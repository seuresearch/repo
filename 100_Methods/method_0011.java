		}
		else {
			return (Serializable) persister.getOwnerEntityPersister().getPropertyValue( owner, collectionType.getLHSPropertyName() );
		}
	}

	private Object initializeLazyPropertiesFromDatastore(
			final String fieldName,
			final Object entity,
			final SharedSessionContractImplementor session,
			final Serializable id,
			final EntityEntry entry) {

		if ( !hasLazyProperties() ) {
			throw new AssertionFailure( "no lazy properties" );
		}

		final InterceptorImplementor interceptor = ( (PersistentAttributeInterceptable) entity ).$$_hibernate_getInterceptor();
		assert interceptor != null : "Expecting bytecode interceptor to be non-null";

		LOG.trace( "Initializing lazy properties from datastore" );

		final String fetchGroup = getEntityMetamodel().getBytecodeEnhancementMetadata()
				.getLazyAttributesMetadata()
				.getFetchGroupName( fieldName );
		final List<LazyAttributeDescriptor> fetchGroupAttributeDescriptors = getEntityMetamodel().getBytecodeEnhancementMetadata()
				.getLazyAttributesMetadata()
				.getFetchGroupAttributeDescriptors( fetchGroup );

		final Set<String> initializedLazyAttributeNames = interceptor.getInitializedLazyAttributeNames();

		final String lazySelect = getSQLLazySelectString( fetchGroup );

		try {
			Object result = null;
			PreparedStatement ps = null;
			try {
				ResultSet rs = null;
				try {
					if ( lazySelect != null ) {
						// null sql means that the only lazy properties
						// are shared PK one-to-one associations which are
						// handled differently in the Type#nullSafeGet code...
						ps = session.getJdbcCoordinator()
								.getStatementPreparer()
								.prepareStatement( lazySelect );
						getIdentifierType().nullSafeSet( ps, id, 1, session );
						rs = session.getJdbcCoordinator().getResultSetReturn().extract( ps );
						rs.next();
					}
					final Object[] snapshot = entry.getLoadedState();
					for ( LazyAttributeDescriptor fetchGroupAttributeDescriptor : fetchGroupAttributeDescriptors ) {
						final boolean previousInitialized = initializedLazyAttributeNames.contains( fetchGroupAttributeDescriptor.getName() );

						if ( previousInitialized ) {
							// todo : one thing we should consider here is potentially un-marking an attribute as dirty based on the selected value
							// 		we know the current value - getPropertyValue( entity, fetchGroupAttributeDescriptor.getAttributeIndex() );
							// 		we know the selected value (see selectedValue below)
							//		we can use the attribute Type to tell us if they are the same
							//
							//		assuming entity is a SelfDirtinessTracker we can also know if the attribute is
							//			currently considered dirty, and if really not dirty we would do the un-marking
							//
							//		of course that would mean a new method on SelfDirtinessTracker to allow un-marking

							// its already been initialized (e.g. by a write) so we don't want to overwrite
							continue;
						}


						final Object selectedValue = fetchGroupAttributeDescriptor.getType().nullSafeGet(
								rs,
								lazyPropertyColumnAliases[fetchGroupAttributeDescriptor.getLazyIndex()],
