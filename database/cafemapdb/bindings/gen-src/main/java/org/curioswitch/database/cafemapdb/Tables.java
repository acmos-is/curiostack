/*
 * This file is generated by jOOQ.
 */
package org.curioswitch.database.cafemapdb;


import javax.annotation.Generated;

import org.curioswitch.database.cafemapdb.tables.FlywaySchemaHistory;
import org.curioswitch.database.cafemapdb.tables.Place;


/**
 * Convenience access to all tables in cafemapdb
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.9"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Tables {

    /**
     * The table <code>cafemapdb.flyway_schema_history</code>.
     */
    public static final FlywaySchemaHistory FLYWAY_SCHEMA_HISTORY = org.curioswitch.database.cafemapdb.tables.FlywaySchemaHistory.FLYWAY_SCHEMA_HISTORY;

    /**
     * The table <code>cafemapdb.place</code>.
     */
    public static final Place PLACE = org.curioswitch.database.cafemapdb.tables.Place.PLACE;
}