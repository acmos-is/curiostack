/*
 * This file is generated by jOOQ.
 */
package org.curioswitch.database.cafemapdb;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.curioswitch.database.cafemapdb.tables.FlywaySchemaHistory;
import org.curioswitch.database.cafemapdb.tables.Place;
import org.jooq.Catalog;
import org.jooq.Table;
import org.jooq.impl.SchemaImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.9"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Cafemapdb extends SchemaImpl {

    private static final long serialVersionUID = 616863750;

    /**
     * The reference instance of <code>cafemapdb</code>
     */
    public static final Cafemapdb CAFEMAPDB = new Cafemapdb();

    /**
     * The table <code>cafemapdb.flyway_schema_history</code>.
     */
    public final FlywaySchemaHistory FLYWAY_SCHEMA_HISTORY = org.curioswitch.database.cafemapdb.tables.FlywaySchemaHistory.FLYWAY_SCHEMA_HISTORY;

    /**
     * The table <code>cafemapdb.place</code>.
     */
    public final Place PLACE = org.curioswitch.database.cafemapdb.tables.Place.PLACE;

    /**
     * No further instances allowed
     */
    private Cafemapdb() {
        super("cafemapdb", null);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Catalog getCatalog() {
        return DefaultCatalog.DEFAULT_CATALOG;
    }

    @Override
    public final List<Table<?>> getTables() {
        List result = new ArrayList();
        result.addAll(getTables0());
        return result;
    }

    private final List<Table<?>> getTables0() {
        return Arrays.<Table<?>>asList(
            FlywaySchemaHistory.FLYWAY_SCHEMA_HISTORY,
            Place.PLACE);
    }
}