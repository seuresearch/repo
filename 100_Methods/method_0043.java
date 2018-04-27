	/**
     * Applies the attributes of this theme to a {@link FastScatterPlot}.
     * @param plot  the plot.
     */
    protected void applyToFastScatterPlot(FastScatterPlot plot) {
		if (plot instanceof CombinedRangeXYPlot) {
            CombinedRangeXYPlot cp = (CombinedRangeXYPlot) plot;
            for (XYPlot subplot : cp.getSubplots()) {
                if (subplot != null) {
                    applyToPlot(subplot);
                }
            }
        }
    }