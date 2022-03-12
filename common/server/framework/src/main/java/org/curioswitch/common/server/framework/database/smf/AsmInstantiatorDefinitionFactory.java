/*
 * MIT License
 *
 * Copyright (c) 2022 Choko (choko@curioswitch.org)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.curioswitch.common.server.framework.database.smf;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Member;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.simpleflatmapper.reflect.InstantiatorDefinition;
import org.simpleflatmapper.reflect.Parameter;
import org.simpleflatmapper.reflect.instantiator.ExecutableInstantiatorDefinition;
import org.simpleflatmapper.util.ErrorHelper;
import org.simpleflatmapper.util.TypeHelper;

/**
 * AsmInstantiatorDefinitionFactory used in Curiostack
 *
 * <p>Most of the content is the same as
 * org.simpleflatmapper.reflect.asm.AsmInstantiatorDefinitionFactory, with the change to use
 * org.ow2.asm instead of org.simpleflatmapper.ow2ams.
 */
public final class AsmInstantiatorDefinitionFactory {

  private AsmInstantiatorDefinitionFactory() {}

  public static List<InstantiatorDefinition> extractDefinitions(final Type target)
      throws IOException {
    final List<InstantiatorDefinition> constructors = new ArrayList<>();

    final Class<?> targetClass = TypeHelper.toClass(target);

    ClassLoader cl = targetClass.getClassLoader();
    if (cl == null) {
      cl = ClassLoader.getSystemClassLoader();
    }

    final String fileName = targetClass.getName().replace('.', '/') + ".class";
    try (InputStream is = cl.getResourceAsStream(fileName)) {
      if (is == null) {
        throw new IOException("Cannot find file " + fileName + " in " + cl);
      }
      ClassReader classReader = new ClassReader(is);
      classReader.accept(
          new ClassVisitor(AsmUtils.API) {
            List<String> genericTypeNames;

            @Override
            public void visit(
                int version,
                int access,
                String name,
                String signature,
                String superName,
                String[] interfaces) {
              if (signature != null) {
                genericTypeNames = AsmUtils.extractGenericTypeNames(signature);
              } else {
                genericTypeNames = Collections.emptyList();
              }
              super.visit(version, access, name, signature, superName, interfaces);
            }

            @Override
            public MethodVisitor visitMethod(
                int access,
                final String methodName,
                String desc,
                String signature,
                String[] exceptions) {
              final boolean isConstructor = "<init>".equals(methodName);
              if ((Opcodes.ACC_PUBLIC & access) == Opcodes.ACC_PUBLIC
                  && (isConstructor
                      || ((Opcodes.ACC_STATIC & access) == Opcodes.ACC_STATIC
                          && !desc.endsWith("V")))) {

                final List<String> descTypes = AsmUtils.extractTypeNamesFromSignature(desc);
                final List<String> genericTypes;
                final List<String> names = new ArrayList<>();
                if (signature != null) {
                  genericTypes = AsmUtils.extractTypeNamesFromSignature(signature);
                  if (targetClass.isMemberClass()
                      && isConstructor
                      && !Modifier.isStatic(targetClass.getModifiers())) { // add outer class param
                    genericTypes.add(0, descTypes.get(0));
                  }
                } else {
                  genericTypes = descTypes;
                }

                if (!isConstructor) {
                  if (descTypes.size() > 0) {
                    try {
                      final Type genericType =
                          AsmUtils.toGenericType(
                              descTypes.get(descTypes.size() - 1), genericTypeNames, target);
                      if (!targetClass.isAssignableFrom(TypeHelper.toClass(genericType))) {
                        return null;
                      }
                    } catch (ClassNotFoundException e) {
                      return null;
                    }
                  } else return null;
                }

                return new MethodVisitor(AsmUtils.API) {

                  Label firstLabel;
                  Label lastLabel;

                  @Override
                  public void visitLabel(Label label) {
                    if (firstLabel == null) {
                      firstLabel = label;
                    }
                    lastLabel = label;
                  }

                  @Override
                  public void visitLocalVariable(
                      String name,
                      String desc,
                      String signature,
                      Label start,
                      Label end,
                      int index) {
                    if (start.equals(firstLabel) && end.equals(lastLabel) && !"this".equals(name)) {
                      names.add(name);
                    }
                  }

                  @Override
                  public void visitEnd() {
                    try {
                      final List<Parameter> parameters = new ArrayList<>();

                      int l = descTypes.size() - (isConstructor ? 0 : 1);
                      for (int i = 0; i < l; i++) {
                        String name = null;
                        if (i < names.size()) {
                          name = names.get(i);
                        }
                        parameters.add(
                            createParameter(i, name, descTypes.get(i), genericTypes.get(i)));
                      }

                      final Member executable;

                      if (isConstructor) {
                        executable = targetClass.getDeclaredConstructor(toTypeArray(parameters));
                      } else {
                        executable =
                            targetClass.getDeclaredMethod(methodName, toTypeArray(parameters));
                      }
                      constructors.add(
                          new ExecutableInstantiatorDefinition(
                              executable, parameters.toArray(new Parameter[0])));
                    } catch (Exception e) {
                      ErrorHelper.rethrow(e);
                    }
                  }

                  private Class<?>[] toTypeArray(List<Parameter> parameters) {
                    Class<?>[] types = new Class<?>[parameters.size()];
                    for (int i = 0; i < types.length; i++) {
                      types[i] = parameters.get(i).getType();
                    }
                    return types;
                  }

                  private Parameter createParameter(
                      int index, String name, String desc, String signature) {
                    try {

                      Type basicType = AsmUtils.toGenericType(desc, genericTypeNames, target);
                      Type genericType = basicType;
                      if (signature != null) {
                        Type type = AsmUtils.toGenericType(signature, genericTypeNames, target);
                        if (type != null) {
                          genericType = type;
                        }
                      }
                      return new Parameter(index, name, TypeHelper.toClass(basicType), genericType);
                    } catch (ClassNotFoundException e) {
                      throw new Error("Unexpected error " + e, e);
                    }
                  }
                };
              } else {
                return null;
              }
            }
          },
          0);
    }

    return constructors;
  }
}
