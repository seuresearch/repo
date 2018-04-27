	/*
     * @return the underlying database's major version
     */
    public int getDatabaseMajorVersion() throws SQLException {
        Version dbv = execute("call database_version()");
        dbv.next();
        String v = dbv.getString(1);
        dbv.close();
        return Integer.parseInt(v.substring(0, v.indexOf(".")));
    }