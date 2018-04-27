    /**
     * Helper method to handle a source path.
     * 
     * @param project the original project
     * @param cpURLs the list that is to contain the projects classpath
     * @param entry the actually processed classpath entry
     * @param javapProject the java project
     * @throws JavaModelException an exception with the java project occured
     */
    private static void handleSourcePath(IProject project, List<URL> cpURLs, IClasspathEntry entry,
            IJavaProject javapProject) throws JavaModelException {

        IPath sourcePath = entry.getPath();

        // check for if the output path is different to the source path
        IPath outputPath = entry.getOutputLocation();

        if (outputPath == null) {
            sourcePath = javapProject.getOutputLocation();
        }
        else if (!outputPath.equals(sourcePath)) {
            // get the resolved classpath of the project
            IClasspathEntry[] cpEntries = javaProject.getResolvedClasspath(true);

            // iterate over classpath to create classpath urls
            int size = cpEntries.length;
            for (int i = 0; i < size; i++) {

                int entryKind = cpEntries[i].getEntryKind();

                // handle a source path
                if (IClasspathEntry.CPE_SOURCE == entryKind) {

                    handleSourcePath(project, cpURLs, cpEntries[i], javaProject);
                }
                // handle a project reference
                else if (IClasspathEntry.CPE_PROJECT == entryKind) {

                    handleRefProject(cpURLs, cpEntries[i], processedProjects);
                }
                // handle a library entry
                else if (IClasspathEntry.CPE_LIBRARY == entryKind) {

                    handleLibrary(project, cpURLs, cpEntries[i]);
                }
                // cannot happen since we use a resolved classpath
                else {

                    // log as exception
                    CheckstylePluginException ex = new CheckstylePluginException(NLS.bind(
                            Messages.errorUnknownClasspathEntry, cpEntries[i].getPath()));
                    CheckstyleLog.log(ex);
                }
            }
		}
        catch (JavaModelException jme) {
            CheckstyleLog.log(jme);
        }
    }