    /**
     * Returns the name of the catalog of the default schema.
     */
    String getDatabaseDefaultCatalog() throws SQLException {
        final SystemSchema ss = executeSelect("SYSTEM_SCHEMA",
            "IS_DEFAULT=TRUE");
        String value = ss.next() ? ss.getString(2)
                         : null;
        ss.close();
        return value;
    }
