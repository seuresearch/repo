    /**
     * Add a new single testcase.
     * @param   jt    a new single JUnit Test case
     * @see JUnitTest
     *
     * @since Ant 1.2
     */
    public void addTest(final JUnitTest jt) {
        jt.addElement(test);
        preConfigure(test);
    }