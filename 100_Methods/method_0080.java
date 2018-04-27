    /**
     * Returns the type of a named attribute.
     *
     * @param attributeName The name of the attribute to find the type of.
     *                      Must not be <code>null</code>.
     *
     * @return the type of the attribute with the specified name.
     *         This will never be <code>null</code>.
     *
     * @exception BuildException if the introspected class does not
     *                           support the named attribute.
     */
    public Class<?> getAttributeType(final String attributeName) throws BuildException {
        final Class<?> at = attributeTypes.get(attributeName);
        if (at == null) {
            throw new UnsupportedAttributeException("Class "
                    + bean.getName() + " doesn't support the \""
                    + attributeName + "\" attribute.", attributeName);
        }
        return at;
    }