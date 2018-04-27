 * Provides definitions for some of HSQLDB's additional system vies.
 *
 * The definitions for the rest of system vies are provided by
 * DatabaseInformationMain, which this class extends. <p>
 *
 * @author Campbell Burnet (boucherb@users dot sourceforge.net)
 * @author Fred Toussi (fredt@users dot sourceforge.net)
 * @version 2.3.3
 * @since 1.7.2
 */
final class DatabaseInformationFull
extends org.hsqldb.dbinfo.DatabaseInformationMain {
    static final HashMappedList statementMap;
    static {
        synchronized (DatabaseInformationFull.class) {
            final String path = "/org/hsqldb/resources/information-schema.sql";
            final String[] starters = new String[]{ "/*" };
            InputStream fis = (InputStream) AccessController.doPrivileged(
                new PrivilegedAction() {

                public InputStream run() {
                    return getClass().getResourceAsStream(path);
                }
            });
            InputStreamReader reader = null;
            try {
                reader = new InputStreamReader(fis, "ISO-8859-1");
            } catch (UnsupportedEncodingException e) {
                reader = new InputStreamReader(fis);
            }
            LineNumberReader lineReader = new LineNumberReader(reader);
            LineGroupReader  lg = new LineGroupReader(lineReader, starters);
            statementMap = lg.getAsMap();
            lg.close();
        }
    }