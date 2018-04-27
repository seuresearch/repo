    public Object createElement(final Project project, final Object parent, final String elementName)
            throws BuildException {
        final NestedCreator nc = getNestedCreator(project, "", parent, elementName, null);
        try {
            final Object ne = nc.create(project, parent, null);//nested element object
            if (project != null) {
                project.setProjectReference(ne);
            }
            return ne;
        } catch (final IllegalAccessException ie) {
            // impossible as getMethods should only return public methods
            throw new BuildException(ie);
        } catch (final InstantiationException ine) {
            // impossible as getMethods should only return public methods
            throw new BuildException(ine);
        } catch (final InvocationTargetException ite) {
            throw extractBuildException(ite);
        }
    }