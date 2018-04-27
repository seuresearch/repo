     /*
     * @see #getHelper(Class)
     */
    private IntrospectionHelper(final Class<?> bean) {
        this.bean = bean;
        final Method[] methods = bean.getMethods();
        Method addTextMethod = null;
        for (int i = 0; i < methods.length; i++) {
            final Method m = methods[i];
            final String name = m.getName();
            final Class<?> returnType = m.getReturnType();
            final Class<?>[] args = m.getParameterTypes();
            // check of add[Configured](Class) pattern
            if (args.length == 1 && java.lang.Void.TYPE.equals(returnType)
                    && ("add".equals(name) || "addConfigured".equals(name))) {
                insertAddTypeMethod(m);
                continue;
            }
            // not really user settable properties on tasks/project components
            if (org.apache.tools.ant.ProjectComponent.class.isAssignableFrom(bean)
                    && args.length == 1 && isHiddenSetMethod(name, args[0])) {
                continue;
            }
            // hide addTask for TaskContainers
            if (isContainer() && args.length == 1 && "addTask".equals(name)
                    && org.apache.tools.ant.Task.class.equals(args[0])) {
                continue;
            }
            if ("addText".equals(name) && java.lang.Void.TYPE.equals(returnType)
                    && args.length == 1 && java.lang.String.class.equals(args[0])) {
                addTextMethod = methods[i];
            } else if (name.startsWith("set") && java.lang.Void.TYPE.equals(returnType)
                    && args.length == 1 && !args[0].isArray()) {
                final String propName = getPropertyName(name, "set");
                AttributeSetter as = attributeSetters.get(propName);
                if (as != null) {
                    if (java.lang.class.equals(args[0])) {
                        /*
                            Ignore method m, as there is an overloaded
                            form of this method that takes in a
                            non-string argument, which gains higher
                            priority.
                        */
                        continue;
                    }
                    if (java.io.File.class.equals(args[0])) {
                        // Ant Resources/FileProviders override java.io.File
                        if (Resource.class.equals(as.type) || FileProvider.class.equals(as.type)) {
                            continue;
                        }
                    }
                    /*
                        In cases other than those just explicitly covered,
                        we just override that with the new one.
                        This mechanism does not guarantee any specific order
                        in which the methods will be selected: so any code
                        that depends on the order in which "set" methods have
                        been defined, is not guaranteed to be selected in any
                        particular order.
                    */
                }
                as = createAttributeSetter(m, args[0], propName);
                if (as != null) {
                    attributeTypes.put(propName, args[0]);
                    attributeSetters.put(propName, as);
                }
            } else if (name.startsWith("create") && !returnType.isArray()
                    && !returnType.isPrimitive() && args.length == 0) {

					final String propName = getPropertyName(name, "create");}
		}
		
