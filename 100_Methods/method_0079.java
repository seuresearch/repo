    /**
     * Returns the type of a named nested element.
     *
     * @param elementName The name of the element to find the type of.
     *                    Must not be <code>null</code>.
     *
     * @return the type of the nested element with the specified name.
     *         This will never be <code>null</code>.
     *
     * @exception BuildException if the introspected class does not
     *                           support the named nested element.
     */
    public Class<?> getElementType(final String elementName) throws BuildException {
        final Class<?> nt = nestedTypes.get(elementName);
        if (nt == null) {
            throw new UnsupportedElementException("Class "
                    + bean.getName() + " doesn't support the nested \""
                    + elementName + "\" element.", elementName);
        }
        return nt;
    }