    /**
     * Applies the settings of this theme to the specified annotation.
     *
     * @param annotation  the annotation.
     */
    protected void applyToXYAnnotation(XYAnnotation annotation) {
        ParamChecks.nullNotPermitted(annotation, "annotation");
        if (annotation instanceof XYTextAnnotation) {
            XYTextAnnotation xyta = (XYTextAnnotation) annotation;//XY Text Annotation
            xyta.setFont(this.smallFont);
            xyta.setPaint(this.itemLabelPaint);
        }
    }