	public static void bindPackage(String packageName, MetadataBuildingContext context) {
		XPackage pckg;
		try {
			pckg = context.getBuildingOptions().getReflectionManager().packageForName( packageName );
		}
		catch (ClassLoadingException e) {
			LOG.packageNotFound( packageName );
			return;
		}
		catch ( ClassNameNotFoundException cnfe ) {//class name not found exception
			LOG.packageNotFound( packageName );
			return;
		}

		if ( pckg.isAnnotationPresent( SequenceGenerator.class ) ) {
			SequenceGenerator ann = pckg.getAnnotation( SequenceGenerator.class );
			IdentifierGeneratorDefinition idGen = buildIdGenerator( ann, context );
			context.getMetadataCollector().addIdentifierGenerator( idGen );
			if ( LOG.isTraceEnabled() ) {
				LOG.tracev( "Add sequence generator with name: {0}", idGen.getName() );
			}
		}
	}