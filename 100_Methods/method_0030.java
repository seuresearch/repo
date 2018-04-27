        // are we using the chart buffer?
        if (this.useBuffer) {

            // do we need to resize the buffer?
            if ((this.chartBuffer == null)
                    || (this.chartBufferWidth != available.getWidth())
                    || (this.chartBufferHeight != available.getHeight())) {
                this.chartBufferWidth = (int) available.getWidth();
                this.chartBufferHeight = (int) available.getHeight();
                GraphicsConfiguration gc = g2.getDeviceConfiguration();
                this.chartBuffer = gc.createCompatibleImage(
                        this.chartBufferWidth, this.chartBufferHeight,
                        Transparency.TRANSLUCENT);
                this.refreshBuffer = true;
            }
		}