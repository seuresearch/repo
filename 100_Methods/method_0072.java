    /**
     * Returns a helper for the given class, either from the cache
     * or by creating a new instance.
     *
     * The method will make sure the helper will be cleaned up at the end of
     * the project, and only one instance will be created for each class.
     *
     * @param p the project instance. Can be null, in which case the helper is not cached.
     * @param c The class for which a helper is required.
     *          Must not be <code>null</code>.
     *
     * @return a helper for the specified class
     */
    public static synchronized IntrospectionHelper getHelper(final Project p, final Class<?> c) {
        IntrospectionHelper ih = HELPERS.get(c.getName());
        // If a helper cannot be found, or if the helper is for another
        // classloader, create a new IH
        if (ih == null || ih.bean != c) {
            ih = new IntrospectionHelper(c);
            if (p != null) {
                // #30162: do *not* cache this if there is no project, as we
                // cannot guarantee that the cache will be cleared.
                HELPERS.put(c.getName(), ih);
            }
        }
        return ih;
    }