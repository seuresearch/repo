        // EnumeratedAttributes have their own helper class
        if (EnumeratedAttribute.class.isAssignableFrom(reflectedArg)) {
            return new AttributeSetter(m, arg) {
                @Override
                public void set(final Project p, final Object parent, final String value)
                        throws InvocationTargetException, IllegalAccessException, BuildException {
                    try {
                        final EnumeratedAttribute ea = (EnumeratedAttribute) reflectedArg.newInstance();
                        ea.setValue(value);
                        m.invoke(parent, new Object[] {ea});
                    } catch (final InstantiationException ie) {
                        throw new BuildException(ie);
                    }
                }
            };
        }
