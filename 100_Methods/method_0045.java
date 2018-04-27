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

        renderer.setDefaultItemLabelFont(this.regularFont);
        renderer.setDefaultItemLabelPaint(this.itemLabelPaint);

        // now we handle some special cases - yes, UGLY code alert!

        // BarRenderer
        if (renderer instanceof BarRenderer) {
            BarRenderer br = (BarRenderer) renderer;
            br.setBarPainter(this.barPainter);
            br.setShadowVisible(this.shadowVisible);
            br.setShadowPaint(this.shadowPaint);
        }

        //  StatisticalBarRenderer
        if (renderer instanceof StatisticalBarRenderer) {
            StatisticalBarRenderer sbr = (StatisticalBarRenderer) renderer;
            sbr.setErrorIndicatorPaint(this.errorIndicatorPaint);
        }

        // MinMaxCategoryRenderer
        if (renderer instanceof MinMaxCategoryRenderer) {
            MinMaxCategoryRenderer mmcr = (MinMaxCategoryRenderer) renderer;
            mmcr.setGroupPaint(this.errorIndicatorPaint);
        }
    }