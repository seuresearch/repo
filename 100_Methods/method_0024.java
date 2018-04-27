	private static PersistentClass makePersistentClass(
			InheritanceState inheritanceState,
			PersistentClass superEntity,
			MetadataBuildingContext metadataBuildingContext) {
		//we now know what kind of persistent entity inheritance type is
		if ( !inheritanceState.hasParents() ) {
			return new RootClass( metadataBuildingContext );
		}
		else if ( it.SINGLE_TABLE.equals( inheritanceState.getType() ) ) {
			return new SingleTableSubclass( superEntity, metadataBuildingContext );
		}
		else if ( it.JOINED.equals( inheritanceState.getType() ) ) {
			return new JoinedSubclass( superEntity, metadataBuildingContext );
		}
		else if ( it.TABLE_PER_CLASS.equals( inheritanceState.getType() ) ) {
			return new UnionSubclass( superEntity, metadataBuildingContext );
		}
		else {
			throw new AssertionFailure( "Unknown inheritance type: " + inheritanceState.getType() );
		}
	}