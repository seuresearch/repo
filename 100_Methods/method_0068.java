    private void logVmCrash(final FormatterArray[] feA, final JUnitTest test, final String testCase) {
        logVmExit(
            feA, test,
            "Forked Java VM exited abnormally. Please note the time in the report"
            + " does not reflect the time until the VM exit.",
            testCase);
    }