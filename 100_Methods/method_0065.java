    /**
     * Adds a system property that tests can access.
     * This might be useful to transfer Ant properties to the
     * testcases when JVM forking is not enabled.
     *
     * @since Ant 1.3
     * @deprecated since ant 1.6
     * @param sysp environment variable to add
     */
    @Deprecated
    public void addSysproperty(final Environment.Variable sysp) {
        getCommandline().addSysproperty(sysp);
    }