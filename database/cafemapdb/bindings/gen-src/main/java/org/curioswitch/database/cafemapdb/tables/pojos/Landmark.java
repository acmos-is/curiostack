/*
 * MIT License
 *
 * Copyright (c) 2019 Choko (choko@curioswitch.org)
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
/*
 * This file is generated by jOOQ.
 */
package org.curioswitch.database.cafemapdb.tables.pojos;


import java.time.LocalDateTime;

import javax.annotation.Generated;

import org.curioswitch.database.cafemapdb.tables.interfaces.ILandmark;
import org.jooq.types.ULong;


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
public class Landmark implements ILandmark {

    private static final long serialVersionUID = -717149911;

    private final ULong         id;
    private final String        googlePlaceId;
    private final String        s2Cell;
    private final String        type;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public Landmark(ILandmark value) {
        this.id = value.getId();
        this.googlePlaceId = value.getGooglePlaceId();
        this.s2Cell = value.getS2Cell();
        this.type = value.getType();
        this.createdAt = value.getCreatedAt();
        this.updatedAt = value.getUpdatedAt();
    }

    public Landmark(
        ULong         id,
        String        googlePlaceId,
        String        s2Cell,
        String        type,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
    ) {
        this.id = id;
        this.googlePlaceId = googlePlaceId;
        this.s2Cell = s2Cell;
        this.type = type;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    @Override
    public ULong getId() {
        return this.id;
    }

    @Override
    public String getGooglePlaceId() {
        return this.googlePlaceId;
    }

    @Override
    public String getS2Cell() {
        return this.s2Cell;
    }

    @Override
    public String getType() {
        return this.type;
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    @Override
    public LocalDateTime getUpdatedAt() {
        return this.updatedAt;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final Landmark other = (Landmark) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        }
        else if (!id.equals(other.id))
            return false;
        if (googlePlaceId == null) {
            if (other.googlePlaceId != null)
                return false;
        }
        else if (!googlePlaceId.equals(other.googlePlaceId))
            return false;
        if (s2Cell == null) {
            if (other.s2Cell != null)
                return false;
        }
        else if (!s2Cell.equals(other.s2Cell))
            return false;
        if (type == null) {
            if (other.type != null)
                return false;
        }
        else if (!type.equals(other.type))
            return false;
        if (createdAt == null) {
            if (other.createdAt != null)
                return false;
        }
        else if (!createdAt.equals(other.createdAt))
            return false;
        if (updatedAt == null) {
            if (other.updatedAt != null)
                return false;
        }
        else if (!updatedAt.equals(other.updatedAt))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
        result = prime * result + ((this.googlePlaceId == null) ? 0 : this.googlePlaceId.hashCode());
        result = prime * result + ((this.s2Cell == null) ? 0 : this.s2Cell.hashCode());
        result = prime * result + ((this.type == null) ? 0 : this.type.hashCode());
        result = prime * result + ((this.createdAt == null) ? 0 : this.createdAt.hashCode());
        result = prime * result + ((this.updatedAt == null) ? 0 : this.updatedAt.hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Landmark (");

        sb.append(id);
        sb.append(", ").append(googlePlaceId);
        sb.append(", ").append(s2Cell);
        sb.append(", ").append(type);
        sb.append(", ").append(createdAt);
        sb.append(", ").append(updatedAt);

        sb.append(")");
        return sb.toString();
    }
}