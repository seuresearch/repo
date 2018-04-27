    /**
     * Draws a title.  The title should be drawn at the top, bottom, left or
     * right of the specified area, and the area should be updated to reflect
     * the amount of space used by the title.
     *
     * @param t  the title (<code>null</code> not permitted).
     * @param g2  the graphics device (<code>null</code> not permitted).
     * @param area  the chart area, excluding any existing titles
     *              (<code>null</code> not permitted).
     * @param entities  a flag that controls whether or not an entity
     *                  collection is returned for the title.
     *
     * @return An entity collection for the title (possibly <code>null</code>).
     */
    protected EntityCollection drawTitle(Title t, Graphics2D g2,
                                         Rectangle2D area, boolean entities) {

        ParamChecks.nullNotPermitted(t, "t");
        ParamChecks.nullNotPermitted(area, "area");
        Rectangle2D titleArea;
        RectangleEdge position = t.getPosition();
        double ww = area.getWidth();
        if (ww <= 0.0) {
            return null;
        }
        double hh = area.getHeight();
        if (hh <= 0.0) {
            return null;
        }
        RectangleConstraint constraint = new RectangleConstraint(ww,
                new Range(0.0, ww), LengthConstraintType.RANGE, hh,
                new Range(0.0, hh), LengthConstraintType.RANGE);
        Object retValue;
        BlockParams p = new BlockParams();
        p.setGenerateEntities(entities);
        if (position == RectangleEdge.TOP) {
            Size2D size = t.arrange(g2, constraint);
            titleArea = createAlignedRectangle2D(size, area,
                    t.getHorizontalAlignment(), VerticalAlignment.TOP);
            retValue = t.draw(g2, titleArea, p);
            area.setRect(area.getX(), Math.min(area.getY() + size.height,
                    area.getMaxY()), area.getWidth(), Math.max(area.getHeight()
                    - size.height, 0));
        }
        else if (position == RectangleEdge.BOTTOM) {
            Size2D size = t.arrange(g2, constraint);
            titleArea = createAlignedRectangle2D(size, area,
                    t.getHorizontalAlignment(), VerticalAlignment.BOTTOM);
            retValue = t.draw(g2, titleArea, p);
            area.setRect(area.getX(), area.getY(), area.getWidth(),
                    area.getHeight() - size.height);
        }
        else if (position == RectangleEdge.RIGHT) {
            Size2D size = t.arrange(g2, constraint);
            titleArea = createAlignedRectangle2D(size, area,
                    HorizontalAlignment.RIGHT, t.getVerticalAlignment());
            retValue = t.draw(g2, titleArea, p);
            area.setRect(area.getX(), area.getY(), area.getWidth()
                    - size.width, area.getHeight());
        }

        else if (position == RectangleEdge.LEFT) {
            Size2D size = t.arrange(g2, constraint);
            titleArea = createAlignedRectangle2D(size, area,
                    HorizontalAlignment.LEFT, t.getVerticalAlignment());
            retValue = t.draw(g2, titleArea, p);
            area.setRect(area.getX() + size.width, area.getY(), area.getWidth()
                    - size.width, area.getHeight());
        }
										 }
