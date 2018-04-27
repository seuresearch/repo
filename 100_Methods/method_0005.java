    /**
     * Gets the complete class path for a given project.
     * 
     * @param project the project
     * @return the class path
     */
    private static URL[] getProjectClassPath(IProject project) {

        // List to contain the class path urls
        List<URL> cpURLs = new ArrayList<URL>();

        // add the projects contents to the classpath
        addToClassPath(project, cpURLs, false, new HashSet<IProject>());

        URL[] urls = cpURLs.toArray(new URL[cpURLs.size()]);

        return urls;
    }