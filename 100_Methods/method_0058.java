     /*      <UL>
     *      <LI>functionResultUnknown - Cannot determine if a return value
     *       or table will be returned
     *      <LI> functionNoTable- Does not return a table
     *      <LI> functionReturnsTable - Returns a table
     *      </UL>
     *  <LI><B>SPECIFIC_NAME</B> String  => the name which uniquely identifies
     *  this function within its schema.  This is a user specified, or DBMS
     * generated, name that may be different then the <code>FUNCTION_NAME</code>
     * for example with overload functions
     *  </OL>
     * <p>
     * A user may not have permission to execute any of the functions that are
     * returned by <code>getFunctions</code>
     *
     * @param catalog a catalog name; must match the catalog name as it
     *        is stored in the database; "" retrieves those without a catalog;
     *        <code>null</code> means that the catalog name should not be used to narrow
     *        the search
     * @param schemaPattern a schema name pattern; must match the schema name
     *        as it is stored in the database; "" retrieves those without a schema;
     *        <code>null</code> means that the schema name should not be used to narrow
     *        the search
     * @param functionNamePattern a function name pattern; must match the
     *        function name as it is stored in the database
     * @return <code>ResultSet</code> - each row is a function description
     * @exception SQLException if a database access error occurs
     * @see #getSearchStringEscape
     * @since JDK 1.6, HSQLDB 1.9
     */
//#ifdef JAVA6
    public ResultSet getFunctions(
            String catalog, String schemaPattern,
            String functionNamePattern) throws SQLException {
        StringBuffer sb = new StringBuffer(256);//String Buffer
        sb.append("select ").append(
            "sp.procedure_cat as FUNCTION_CAT,").append(
            "sp.procedure_schem as FUNCTION_SCHEM,").append(
            "sp.procedure_name as FUNCTION_NAME,").append(
            "sp.remarks as REMARKS,").append("1 as FUNCTION_TYPE,").append(
            "sp.specific_name as SPECIFIC_NAME ").append(
            "from information_schema.system_procedures sp ").append(
            "where sp.procedure_type = 2 ");

        if (wantsIsNull(functionNamePattern)) {
            return execute(sb.append("and 1=0").toString());
        }
        schemaPattern = translateSchema(schemaPattern);

        sb.append(and("sp.procedure_cat", "=",
                      catalog)).append(and("sp.procedure_schem", "LIKE",
                          schemaPattern)).append(and("sp.procedure_name",
                              "LIKE", functionNamePattern));

        // By default, query already returns the result ordered by
        // FUNCTION_SCHEM, FUNCTION_NAME...
        return execute(sb.toString());
    }