	/**
	 * Sets the language-sensitive orientation that is to be used to order the
	 * elements or text within this component. Language-sensitive LayoutManager
	 * and Component subclasses will use this property to determine how to lay
	 * out and draw components.
	 * <p>
	 * At construction time, a component's orientation is set to
	 * ComponentOrientation.UNKNOWN, indicating that it has not been specified
	 * explicitly. The UNKNOWN orientation behaves the same as
	 * ComponentOrientation.LEFT_TO_RIGHT.
	 *
	 * @param coel
	 *            The component orientation elements list.
	 */
	public void setComponentOrientation(ComponentOrientationElementsList coel) {
		super.setComponentOrientation(coel);
		_iltr = coel.isLeftToRight();
		calculateStartPosition();
	}