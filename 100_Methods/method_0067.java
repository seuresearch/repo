        StringBuffer formatterArg = new StringBuffer(STRING_BUFFER_SIZE);
        final FormatterElement[] feArray = mergeFormatters(test);
        for (int i = 0; i < feArray.length; i++) {
            final FormatterElement fe = feArray[i];
            if (fe.shouldUse(this)) {
                formatterArg.append(Constants.FORMATTER);
                formatterArg.append(fe.getClassname());
                final File outFile = getOutput(fe, test);
                if (outFile != null) {
                    formatterArg.append(",");
                    formatterArg.append(outFile);
                }
                cmd.createArgument().setValue(formatterArg.toString());
                formatterArg = new StringBuffer();
            }
        }