	private static void buildUniqueConstraints(AnnotationDescriptor annotation, Element element) {
		List uniqueConstraintElementList = element.elements( "unique-constraint" );
		UniqueConstraint[] uniqueConstraints = new UniqueConstraint[uniqueConstraintElementList.size()];
		int ucIndex = 0;
		Iterator ucIt = uniqueConstraintElementList.listIterator();
		while ( ucIt.hasNext() ) {
			Element subelement = (Element) ucIt.next();
			List<Element> columnNamesElements = subelement.elements( "column-name" );
			String[] columnName = new String[columnNamesElements.size()];
			int columnNameIndex = 0;
			Iterator it = columnNamesElements.listIterator();
			while ( it.hasNext() ) {
				Element columnNameElt = (Element) it.next();
				columnName[columnNameIndex++] = columnNameElt.getTextTrim();
			}
			AnnotationDescriptor AnnD = new AnnotationDescriptor( UniqueConstraint.class );
			copyStringAttribute( AnnD, subelement, "name", false );
			AnnD.setValue( "columnName", columnName );
			uniqueConstraints[ucIndex++] = AnnotationFactory.create( AnnD );
		}
		annotation.setValue( "uniqueConstraints", uniqueConstraints );
	}