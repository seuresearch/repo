    /**
     * Creates and returns a buffered image into which the chart has been drawn.
     *
     * @param imageWidth  the image width.
     * @param imageHeight  the image height.
     * @param drawWidth  the width for drawing the chart (will be scaled to
     *                   fit image).
     * @param drawHeight  the height for drawing the chart (will be scaled to
     *                    fit image).
     * @param info  optional object for collection chart dimension and entity
     *              information.
     *
     * @return A buffered image.
     */
    public BufferedImage createBufferedImage(int imageWidth,
                                             int imageHeight,
                                             double drawWidth,
                                             double drawHeight,
                                             ChartRenderingInfo info) {

        BufferedImage image = new BufferedImage(imageWidth, imageHeight,
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = image.createGraphics();
        double scaleX = imageWidth / drawWidth;
        double scaleY = imageHeight / drawHeight;
        AffineTransform st = AffineTransform.getScaleInstance(scaleX, scaleY);
        g2.transform(st);
        draw(g2, new Rectangle2D.Double(0, 0, drawWidth, drawHeight), null,
                info);
        g2.dispose();
        return image;

    }