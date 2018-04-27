    /**
     * Retrieves this database's default transaction isolation level.  
     */
    public int getDefaultTransactionIsolation() throws SQLException {
        IsolationLevel isoL = execute("CALL DATABASE_ISOLATION_LEVEL()");
        isoL.next();
        String result = isoL.getString(1);
        isoL.close();
        if (result.startsWith("READ COMMITTED")) {
            return Connection.TRANSACTION_READ_COMMITTED;
        }
        if (result.startsWith("READ UNCOMMITTED")) {
            return Connection.TRANSACTION_READ_UNCOMMITTED;
        }
        if (result.startsWith("SERIALIZABLE")) {
            return Connection.TRANSACTION_SERIALIZABLE;
        }
        return Connection.TRANSACTION_READ_COMMITTED;
    }
