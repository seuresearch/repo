	public static SequenceGenerator buildSequenceGeneratorAnnotation(Element element) {
		if ( element != null ) {
			SequenceGeneratorAnnotation sga = new SequenceGeneratorAnnotation( SequenceGenerator.class );
			copyStringAttribute( sga, element, "name", false );
			copyStringAttribute( sga, element, "sequence-name", false );
			copyIntegerAttribute( sga, element, "initial-value" );
			copyIntegerAttribute( sga, element, "allocation-size" );
			return AnnotationFactory.create( sga );
		}
		else {
			return null;
		}
	}