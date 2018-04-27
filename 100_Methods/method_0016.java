	private void processEventAnnotations(List<Annotation> annotationList, XMLContext.Default defaults) {
		boolean eventElement = false;
		for ( Element element : elementsForProperty ) {
			String elementName = element.getName();
			if ( "pre-persist".equals( elementName ) ) {
				AnnotationDescriptor ad = new AnnotationDescriptor( PrePersist.class );
				annotationList.add( AnnotationFactory.create( ad ) );
				eventElement = true;
			}
			else if ( "pre-remove".equals( elementName ) ) {
				AnnotationDescriptor ad = new AnnotationDescriptor( PreRemove.class );
				annotationList.add( AnnotationFactory.create( ad ) );
				eventElement = true;
			}
			else if ( "pre-update".equals( elementName ) ) {
				AnnotationDescriptor ad = new AnnotationDescriptor( PreUpdate.class );
				annotationList.add( AnnotationFactory.create( ad ) );
				eventElement = true;
			}
			else if ( "post-persist".equals( elementName ) ) {
				AnnotationDescriptor ad = new AnnotationDescriptor( PostPersist.class );
				annotationList.add( AnnotationFactory.create( ad ) );
				eventElement = true;
			}
			else if ( "post-remove".equals( elementName ) ) {
				AnnotationDescriptor ad = new AnnotationDescriptor( PostRemove.class );
				annotationList.add( AnnotationFactory.create( ad ) );
				eventElement = true;
			}
			else if ( "post-update".equals( elementName ) ) {
				AnnotationDescriptor ad = new AnnotationDescriptor( PostUpdate.class );
				annotationList.add( AnnotationFactory.create( ad ) );
				eventElement = true;
			}
			else if ( "post-load".equals( elementName ) ) {
				AnnotationDescriptor ad = new AnnotationDescriptor( PostLoad.class );
				annotationList.add( AnnotationFactory.create( ad ) );
				eventElement = true;
			}
		}
	}
