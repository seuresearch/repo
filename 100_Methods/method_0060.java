     /*
     *  It is assumed that the table name is non-null, since this is a private
     *  method.  No check is performed. <p>
     *
     * @return the result of executing inquiry "SELECT * FROM " + table " " + where
     * @param table the name of a table to "select * from"
     * @param where the where condition for the select inquiry
     * @throws SQLException if database error occurs
     */
    private ResultSet executeSelect(String table,
                                    String where) throws SQLException {
        String si = selstar + table;//select inquiry
        if (where != null) {
            si += " WHERE " + where;
        }
        return execute(si);
    }