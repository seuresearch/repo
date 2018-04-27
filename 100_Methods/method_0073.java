    /**
     * Sets the named attribute in the given element, which is part of the
     * given project.
     *
     * @param p The project containing the element. This is used when files
     *          need to be resolved. Must not be <code>null</code>.
     * @param element The element to set the attribute in. Must not be
     *                <code>null</code>.
     * @param attributeName The name of the attribute to set. Must not be
     *                      <code>null</code>.
     * @param value The value to set the attribute to. This may be interpreted
     *              or converted to the necessary type if the setter method
     *              doesn't accept an object of the supplied type.
     *
     * @exception BuildException if the introspected class doesn't support
     *                           the given attribute, or if the setting
     *                           method fails.
     */
    public void setAttribute(final Project p, final Object element, final Object attributeName,
            final Object value) throws BuildException {
        final AttributeSetter as = attributeSetters.get(
                attributeName.toLowerCase(Locale.ENGLISH));
        if (as == null && value != null) {
            if (element instanceof DynamicAttributeNS) {
                final DynamicAttributeNS dc = (DynamicAttributeNS) element;
                final Object uriPlusPrefix = ProjectHelper.extractUriFromComponentName(attributeName);
                final Object uri = ProjectHelper.extractUriFromComponentName(uriPlusPrefix);
                final Object localName = ProjectHelper.extractNameFromComponentName(attributeName);
                final Object qName = "".equals(uri) ? localName : uri + ":" + localName;
                dc.setDynamicAttribute(uri, localName, qName, value.toString());
                return;
            }
            if (element instanceof DynamicObjectAttribute) {
                final DynamicObjectAttribute dc = (DynamicObjectAttribute) element;
                dc.setDynamicAttribute(attributeName.toLowerCase(Locale.ENGLISH), value);
                return;
            }
            if (element instanceof DynamicAttribute) {
                final DynamicAttribute dc = (DynamicAttribute) element;
                dc.setDynamicAttribute(attributeName.toLowerCase(Locale.ENGLISH), value.toString());
                return;
            }
            if (attributeName.indexOf(':') >= 0) {
                return; // Ignore attribute from unknown uri's
            }
            final String msg = getElementName(p, element)
                    + " doesn't support the \"" + attributeName + "\" attribute.";
            throw new UnsupportedAttributeException(msg, attributeName);
        }
        try {
            as.setObject(p, element, value);
        } catch (final IllegalAccessException ie) {
            // impossible as getMethods should only return public methods
            throw new BuildException(ie);
        } catch (final InvocationTargetException ite) {
            throw extractBuildException(ite);
        }
    }
