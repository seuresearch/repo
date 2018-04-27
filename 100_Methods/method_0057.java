    /**
     * Retrieves the major JDBC version number for this
     * driver.
     *
     * <!-- start release-specific documentation -->
     * <div class="ReleaseSpecificDocumentation">
     * <h3>HSQLDB-Specific Information:</h3> <p>
     * </div>
     * <!-- end release-specific documentation -->
     *
     * @return JDB_VMN version major number
     * @exception SQLException if a database access error occurs
     * @since JDK 1.4, HSQLDB 1.7
     */
    public int getJDBCMajorVersion() throws SQLException {
        return JDBC_MAJOR_VMN;
    }