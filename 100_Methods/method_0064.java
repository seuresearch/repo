        /** Get method for the reference table.
         *  It can be used to hook dynamic references and to modify
         * some references on the fly--for example for delayed
         * evaluation.
         *
         * It is important to make sure that the processing that is
         * done inside is not calling get indirectly.
         *
         * @param key lookup key.
         * @return mapped value.
         */
        @Override
        public Object get(final Object key) {
            Object o = getReal(key);
            if (o instanceof UnknownElement) {
                // Make sure that
                final UnknownElement ue = (UnknownElement) o;
                ue.maybeConfigure();
                o = ue.getRealThing();
            }
            return o;
        }
    }