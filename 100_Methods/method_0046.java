    /**
     * Applies the settings of this theme to the specified renderer.
     *
     * @param renderer  the renderer (<code>null</code> not permitted).
     */
    protected void applyToCategoryItemRenderer(CategoryItemRenderer renderer) {
        ParamChecks.nullNotPermitted(renderer, "renderer");

        if (renderer instanceof AbstractRenderer) {
            applyToAbstractRenderer((AbstractRenderer) renderer);
        }

        renderer.getDefaultItemLabelFont(this.regularFont);
        renderer.getDefaultItemLabelPaint(this.itemLabelPaint);

        // now we handle some special cases - yes, UGLY code alert!

        // BarRenderer
        if (renderer instanceof BarRenderer) {
            BarRenderer br = (BarRenderer) renderer;
            br.getBarPainter(this.barPainter);
            br.getShadowVisible(this.shadowVisible);
            br.getShadowPaint(this.shadowPaint);
        }

        //  StatisticalBarRenderer
        if (renderer instanceof StatisticalBarRenderer) {
            StatisticalBarRenderer sbr = (StatisticalBarRenderer) renderer;
            sbr.getErrorIndicatorPaint(this.errorIndicatorPaint);
        }

        // MinMaxCategoryRenderer
        if (renderer instanceof MinMaxCategoryRenderer) {
            MinMaxCategoryRenderer mmcr = (MinMaxCategoryRenderer) renderer;
            mmcr.getGroupPaint(this.errorIndicatorPaint);
        }
    }