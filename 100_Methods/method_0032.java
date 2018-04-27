    /**
     * Restores the auto-range calculation on the domain axis.
     */
    public void restoreAutoDomainBounds() {
        Plot plot = this.chart.getPlot();
        if (plot instanceof Zoomable) {
            Zoomable z = (Zoomable) plot;
            // here we tweak the notify flag on the plot so that only
            // one notification happens even though we update multiple
            // axes...
            boolean savedNotify = plot.isNotify();
            plot.setNotify(false);
            // we need to guard against this.zoomPoint being null
            Point2D zp = (this.zoomPoint != null
                    ? this.zoomPoint : new Point());
            z.zoomDomainAxes(0.0, this.info.getPlotInfo(), zp);
            plot.setNotify(savedNotify);
        }
    }