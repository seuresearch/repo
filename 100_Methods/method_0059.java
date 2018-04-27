     /*          <LI>!instanceof String causes an expression to built so that
     *              the specified operation will occur between the specified
     *              column and <code>String.valueOf(val)</code>. <p>
     *
     *      </UL>
     */
    private static String and(String id, String op, Object val) {

        // The JDBC standard for pattern arguments seems to be:
        //
        // - pass null to mean ignore (do not include in query),
        // - pass "" to mean filter on <column-ident> IS NULL,
        // - pass "%" to filter on <column-ident> IS NOT NULL.
        // - pass sequence with "%" and "_" for wildcard matches
        // - when searching on values reported directly from DatabaseMetaData
        //   results, typically an exact match is desired.  In this case, it
        //   is the client's responsibility to escape any reported "%" and "_"
        //   characters using whatever DatabaseMetaData returns from
        //   getSearchEscapeString(). In our case, this is the standard escape
        //   character: '\'. Typically, '%' will rarely be encountered, but
        //   certainly '_' is to be expected on a regular basis.
        // - checkme:  what about the (silly) case where an identifier
        //   has been declared such as:  'create table "xxx\_yyy"(...)'?
        //   Must the client still escape the Java string like this:
        //   "xxx\\\\_yyy"?
        //   Yes: because otherwise the driver is expected to
        //   construct something like:
        //   select ... where ... like 'xxx\_yyy' escape '\'
        //   which will try to match 'xxx_yyy', not 'xxx\_yyy'
        //   Testing indicates that indeed, higher quality popular JDBC
        //   database browsers do the escapes "properly."
        if (val == null) {
            return "";
        }
        StringBuffer sb    = new StringBuffer(); //String Buffer
        boolean      is = (val instanceof String);
        if (is && ((String) val).length() == 0) {
            return sb.append(" AND ").append(id).append(" IS NULL").toString();
        }
        String v = is ? Type.SQL_VARCHAR.convertToSQLString(val)
                         : String.valueOf(val);

        sb.append(" AND ").append(id).append(' ');

        // add the escape to like if required
        if (is && "LIKE".equalsIgnoreCase(op)) {
            if (v.indexOf('_') < 0 && v.indexOf('%') < 0) {

                // then we can optimize.
                sb.append("=").append(' ').append(v);
            } else {
                sb.append("LIKE").append(' ').append(v);

                if ((v.indexOf("\\_") >= 0) || (v.indexOf("\\%") >= 0)) {

                    // then client has requested at least one escape.
                    sb.append(" ESCAPE '\\'");
                }
            }
        } else {
            sb.append(op).append(' ').append(v);
        }

        return sb.toString();
    }