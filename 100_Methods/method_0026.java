	private static PropertyBinder bindComponent(
			PropertyData inferredData,
			PropertyHolder propertyHolder,
			AccessType propertyAccessor,
			EntityBinder entityBinder,
			boolean isIdentifierMapper,
			MetadataBuildingContext buildingContext,
			boolean isComponentEmbedded,
			boolean isId, //is a identifier
			Map<XClass, InheritanceState> inheritanceStatePerClass,
			String referencedEntityName, //is a component who is overridden by a @MapsId
			Ejb3JoinColumn[] columns) {
		Component comp;
		if ( referencedEntityName != null ) {
			comp = createComponent( propertyHolder, inferredData, isComponentEmbedded, isIdentifierMapper, buildingContext );
			SecondPass sp = new CopyIdentifierComponentSecondPass(
					comp,
					referencedEntityName,
					columns,
					buildingContext
			);
			buildingContext.getMetadataCollector().addSecondPass( sp );
		}
		else {
			comp = fillComponent(
					propertyHolder, inferredData, propertyAccessor, !isId, entityBinder,
					isComponentEmbedded, isIdentifierMapper,
					false, buildingContext, inheritanceStatePerClass
			);
		}
			}
			
		
	
