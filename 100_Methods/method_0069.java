    /**
     * Take care that some output is produced in report files if the
     * forked machine terminated before the JUnitTest suite finished
     * with case message (CM) 
     * @since Ant 1.7
     */
    private void logVmExit(final FormatterArray[] feA, final JUnitTest jt,
                           final String cm, final String testCase) {
        if (delegate == null) {
            setupJUnitDelegate();
        }

        try {
            log("Using System properties " + System.getProperties(),
                Project.MSG_VERBOSE);
            if (splitJUnit) {
                classLoader = (AntClassLoader) delegate.getClass().getClassLoader();
            } else {
                createClassLoader();
            }
            if (classLoader != null) {
                classLoader.setThreadContextLoader();
            }

            jt.setCounts(1, 0, 1, 0);
            jt.setProperties(getProject().getProperties());
            for (int i = 0; i < feA.length; i++) {
                final FormatterArray fe = feA[i];
                if (fe.shouldUse(this)) {
                    final JUnitTaskMirror.JUnitResultFormatterMirror formatter =
                        fe.createFormatter(classLoader);
                    if (formatter != null) {
                        OutputStream out = null;
                        final File outFile = getOutput(fe, jt);
                        if (outFile != null) {
                            try {
                                out = new FileOutputStream(outFile);
                            } catch (final IOException e) {
                                // ignore
                            }
                        }
                        if (out == null) {
                            out = getDefaultOutput();
                        }
                        delegate.addVmExit(jt, formatter, out, cm,
                                           testCase);
                    }
                }
            }
            if (summary) {
                final JUnitTaskMirror.SummaryJUnitResultFormatterMirror f =
                    delegate.newSummaryJUnitResultFormatter();
                f.setWithOutAndErr(equalsWithOutAndErr(summaryValue));
                delegate.addVmExit(jt, f, getDefaultOutput(), cm, testCase);
            }
        } finally {
            if (classLoader != null) {
                classLoader.resetThreadContextLoader();
            }
        }
    }