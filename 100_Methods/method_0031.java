            // do we need to redraw the buffer?
            if (this.refreshBuffer) {

                this.refreshBuffer = false; // clear the flag

                Rectangle2D bufferArea = new Rectangle2D.Double(
                        0, 0, this.chartBufferWidth, this.chartBufferHeight);

                // make the background of the buffer clear and transparent
                Graphics2D bufferG2 = (Graphics2D)
                        this.chartBuffer.getGraphics();
                Composite savedComposite = bufferG2.getComposite();
                bufferG2.setComposite(AlphaComposite.getInstance(
                        AlphaComposite.CLEAR, 0.0f));
                Rectangle r = new Rectangle(0, 0, this.chartBufferWidth,
                        this.chartBufferHeight);
                bufferG2.fill(r);
                bufferG2.setComposite(savedComposite);

                if (scale) {
                    AffineTransform saved = bufferG2.getTransform();
                    AffineTransform st = AffineTransform.getScaleInstance(
                            this.scaleX, this.scaleY);
                    bufferG2.transform(st);
                    this.chart.draw(bufferG2, chartArea, this.anchor,
                            this.info);
                    bufferG2.setTransform(saved);
                } else {
                    this.chart.draw(bufferG2, bufferArea, this.anchor,
                            this.info);
                }

            }