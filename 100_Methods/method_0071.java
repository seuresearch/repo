    /**
     * Returns a helper for the given class, either from the cache
     * or by creating a new instance.
     *
     * @param cH The class helper for which a helper is required.
     *          Must not be <code>null</code>.
     *
     * @return a helper for the specified class
     */
    public static synchronized IntrospectionHelper getClassHelper(final Class<?> cH) {
        return getClassHelper(null, cH);
    }