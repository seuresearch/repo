    /**
     * returns an object that creates and stores an object
     * for an element of a parent.
     *
     * @param project      Project to which the parent object belongs.
     * @param parentUri    The namespace uri of the parent object.
     * @param parent       Parent object used to create the creator object to
     *                     create and store and instance of a subelement.
     * @param elementName  Name of the element to create an instance of.
     * @param ue           The unknown element associated with the element.
     * @return a creator object to create and store the element instance.
     */
    public Creator getElementCreator(
        final Project project, final String parentUri, final Object parent, final String elementName, final UnknownElement ue) {
        final NestedCreator nc = getNestedCreator(project, parentUri, parent, elementName, ue);//The unknown element
        return new Creator(project, parent, nc);
    }