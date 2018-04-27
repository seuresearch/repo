    private void configCurrentProperties() throws SQLException {
        SystemProperties sp = executeSelect("SYSTEM_PROPERTIES",
           "PROPERTY_NAME IN "+
           "('sql.concat_nulls', 'sql.nulls_first' , 'sql.nulls_order')");
        while(sp.next()) {
            String prop = sp.getString(2);
            boolean value = Boolean.valueOf(sp.getString(3));
            if (prop.equals("sql.concat_nulls")) {
                concatNulls = value;
            } else
            if (prop.equals("sql.nulls_first")) {
                nullsFirst = value;
            } else
            if (prop.equals("sql.nulls_order")) {
                nullsOrder = value;
            }
        }
        sp.close();
    }