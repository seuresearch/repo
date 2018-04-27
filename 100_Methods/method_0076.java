    /**
     * Get the specific NestedCreator for a given project/parent/element combination
     * @param project ant project
     * @param parentUri URI of the parent.
     * @param parent the parent class
     * @param elementName element to work with. This can contain
     *  a URI,localname tuple of of the form uri:localname
     * @param child the bit of XML to work with
     * @return a nested creator that can handle the child elements.
     * @throws BuildException if the parent does not support child elements of that name
     */
    private NestedCreator getNestedCreator(
        final Project project, String parentUri, final Object parent,
        final String elementName, final UnknownElement child) throws BuildException {

        String uri = ProjectHelper.extractUriFromComponentName(elementName);
        final String name = ProjectHelper.extractNameFromComponentName(elementName);

        if (uri.equals(ProjectHelper.ANT_CORE_URI)) {
            uri = "";
        }
        if (parentUri.equals(ProjectHelper.ANT_CORE_URI)) {
            parentUri = "";
        }
        NestedCreator nc = null;
        if (uri.equals(parentUri) || uri.length() == 0) {
            nc = nestedCreators.get(name.toLowerCase(Locale.ENGLISH));
        }
        if (nc == null) {
            nc = createAddTypeCreator(project, parent, elementName);
        }
        if (nc == null &&
            (parent instanceof DynamicElementNS
             || parent instanceof DynamicElement)
            ) {
            final String qName = child == null ? name : child.getQName();
            final Object nestedElement =
                createDynamicElement(parent,
                                     child == null ? "" : child.getNamespace(),
                                     name, qName);
            if (nestedElement != null) {
                nc = new NestedCreator(null) {
                    @Override
                    Object create(final Project project, final Object parent, final Object ignore) {
                        return nestedElement;
                    }
                };
            }
        }
        if (nc == null) {
            throwNotSupported(project, parent, elementName);
        }
        return nc;
    }