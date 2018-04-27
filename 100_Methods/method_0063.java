    /**
     * Define a class given its bytes
     *
     * @param container the container from which the class data has been read
     *                  may be a directory or a jar/zip file.
     *
     * @param classData the bytecode data for the class
     * @param classname the name of the class
     *
     * @return the Class instance created from the given data
     *
     * @throws IOException if the class data cannot be read.
     */
    protected Class<?> defineClassFromData(final File container, final byte[] classData, final String classname)
        throws IOException {
        definePackage(container, classname);
        final ProtectionDomain currentPd = Project.class.getProtectionDomain();
        final String classResource = getClassFilename(classname);
        final CodeSource src = new CodeSource(FILE_UTILS.getFileURL(container),
                                              getCertificates(container,
                                                              classResource));
        final ProtectionDomain classesPd =
            new ProtectionDomain(src, currentPd.getPermissions(),
                                 this,
                                 currentPd.getPrincipals());
        return defineClass(classname, classData, 0, classData.length,
                           classesPd);
    }