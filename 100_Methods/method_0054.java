    // -------------------------- Package Attributes ----------------------------

    /**
     * The Statement that generated this result. Null if the result is
     * from DatabaseMetaData<p>
     */
    JDBCStatementBase statement;

    /**
     * Session or ClientConnection
     */
    SessionInterface  session;

    /** JDBCConnection for this. */
    JDBCConnection connection;

    /**
     * The scrollability / scroll sensitivity type of this result.
     */
    boolean isScrollable;

    /** The updatability of this result. */
    boolean isUpdatable;

    /** The insertability of this result. */
    boolean isInsertable;
    int     rsProperties;
    int     fetchSize;

    /** Statement is closed when its result set is closed */
    boolean autoClose;

    /** The underlying result set. */
    public Result rs;

    // ---------------------- Public Attributes --------------------------------
    // Support for JDBC 2 from JRE 1.1.x

    /** Copy of java.sql.ResultSet constant, for JDK 1.1 clients. */
    public static final int FETCH_FORWARD = 1000;

    /** Copy of java.sql.ResultSet constant, for JDK 1.1 clients. */
    public static final int FETCH_REVERSE = 1001;

    /** Copy of java.sql.ResultSet constant, for JDK 1.1 clients. */
    public static final int FETCH_UNKNOWN = 1002;

    /** Copy of java.sql.ResultSet constant, for JDK 1.1 clients. */
    public static final int TYPE_FORWARD_ONLY = 1003;

    /**
     * Copy of java.sql.ResultSet constant, for JDK 1.1 clients. <p>
     *
     *  (JDBC4 clarification:) scrollable but generally not sensitive
     *  to changes to the data that underlies the <code>ResultSet</code>.
     */
    public static final int TYPE_SCROLL_INSENSITIVE = 1004;

    /**
     * Copy of java.sql.ResultSet constant, for JDK 1.1 clients. <p>
     *
     *  (JDBC4 clarification:) scrollable and generally sensitive
     *  to changes to the data that underlies the <code>ResultSet</code>.
     */
