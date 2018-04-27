     *
     * If <code>Connection.getTypeMap</code> does throw a
     * <code>SQLFeatureNotSupportedException</code>,
     * then structured values are not supported, and distinct values
     * are mapped to the default Java class as determined by the
     * underlying SQL type of the DISTINCT type.
     * <!-- end generic documentation -->
     *
     * @param columnIndex the first column is 1, the second is 2, ...
     * @return a <code>java.lang.Object</code> holding the column value
     * @exception SQLException if a database access error occurs or this method is
     *            called on a closed result set
     */
    public Object getObject(int columnIndex) throws SQLException {
        checkColumn(columnIndex);
        Type sourceType = resultMetaData.columnTypes[columnIndex - 1];
        switch (sourceType.typeCode) {
            case Types.SQL_ARRAY :
                return getArray(columnIndex);
            case Types.SQL_DATE :
                return getDate(columnIndex);
            case Types.SQL_TIME :
            case Types.SQL_TIME_WITH_TIME_ZONE :
                return getTime(columnIndex);
            case Types.SQL_TIMESTAMP :
            case Types.SQL_TIMESTAMP_WITH_TIME_ZONE :
                return getTimestamp(columnIndex);
            case Types.SQL_BINARY :
            case Types.SQL_VARBINARY :
                return getBytes(columnIndex);
            case Types.SQL_GUID :
                BinaryData bd = (BinaryData) getColumnValue(columnIndex);
                return BinaryUUIDType.getJavaUUID(bd);
            case Types.SQL_BIT : {
                boolean b = getBoolean(columnIndex);

                return wasNull() ? null
                                 : b ? Boolean.TRUE
                                     : Boolean.FALSE;
            }
            case Types.SQL_CLOB :
                return getClob(columnIndex);
            case Types.SQL_BLOB :
                return getBlob(columnIndex);
            case Types.OTHER :
            case Types.JAVA_OBJECT : {
                Object o = getColumnInType(columnIndex, sourceType);

                if (o == null) {
                    return null;
                }

                try {
                    return ((JavaObjectData) o).getObject();
                } catch (HsqlException e) {
                    throw JDBCUtil.sqlException(e);
                }
            }
            default :
                return getColumnInType(columnIndex, sourceType);
        }
    }
