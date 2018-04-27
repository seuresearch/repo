     * @param project the project
     * @param cpURLs the resulting list
     * @param isReferenced true if a referenced project is processed
     */
    private static void addToClassPath(IProject project, List<URL> cpURLs, boolean isReferenced,
            Collection<IProject> processedProjects) {

        try {
            // this project has already been added
            if (processedProjects.contains(project)) {
                return;
            }
            else {
                processedProjects.add(project);
            }
            // get the java project
            IJavaProject javaProject = JavaCore.create(project);

            // get the resolved classpath entry of the project
            IClasspathEntry[] cpe = javaProject.getResolvedClasspath(true);
            // iterate over classpath to create classpath urls
            int size = cpe.length;
            for (int i = 0; i < size; i++) {
                int entryKind = cpe[i].getEntryKind();
                // handle a source path
                if (IClasspathEntry.CPE_SOURCE == entryKind) {
                    handleSourcePath(project, cpURLs, cpe[i], javaProject);
                }
                // handle a project reference
                else if (IClasspathEntry.CPE_PROJECT == entryKind) {

                    handleRefProject(cpURLs, cpe[i], processedProjects);
                }
                // handle a library entry
                else if (IClasspathEntry.CPE_LIBRARY == entryKind) {

                    handleLibrary(project, cpURLs, cpe[i]);
                }
                // cannot happen since we use a resolved class path
                else {
                    // log as exception
                    CheckstylePluginException ex = new CheckstylePluginException(NLS.bind(
                            Messages.errorUnknownClasspathEntry, cpe[i].getPath()));
                    CheckstyleLog.log(ex);
                }
            }
        }
        catch (JavaModelException jme) {
            CheckstyleLog.log(jme);
        }
    }