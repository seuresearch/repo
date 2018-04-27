    public void setAttribute(final Project p, final Object element, final String attributeName,
            final Object value) throws BuildException {
        final AttributeSetter as = attributeSetters.get(
                attributeName.toLowerCase(Locale.ENGLISH));
        if (as == null && value != null) {
            if (element instanceof DynamicAttributeNS) {
                final DynamicAttributeNS dc = (DynamicAttributeNS) element;
                final String uriPlusPrefix = ProjectHelper.extractUriFromComponentName(attributeName);
                final String uri = ProjectHelper.extractUriFromComponentName(uriPlusPrefix);
                final String localName = ProjectHelper.extractNameFromComponentName(attributeName);
                final String qName = "".equals(uri) ? localName : uri + ":" + localName;
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
        } catch (final IllegalAccessException iae) {// Illegal access exception: impossible as getMethods should only return public methods
            throw new BuildException(ie);
        } catch (final InvocationTargetException ite) {
            throw extractBuildException(ite);
        }
    }