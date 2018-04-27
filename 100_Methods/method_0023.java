		if ( InheritanceType.JOINED.equals( inheritanceState.getType() ) ) {
			if ( inheritanceState.hasParents() ) {
				onDeleteAppropriate = true;
				final JoinedSubclass jsc = ( JoinedSubclass ) persistentClass;
				SimpleValue key = new DependantValue( context.getMetadataCollector(), jsc.getTable(), jsc.getIdentifier() );
				jsc.setKey( key );
				ForeignKey fkc = clazzToProcess.getAnnotation( ForeignKey.class );
				if ( fkc != null && !BinderHelper.isEmptyAnnotationValue( fkc.name() ) ) {
					key.setForeignKeyName( fkc.name() );
				}
				else {
					final PrimaryKeyJoinColumn pc = clazzToProcess.getAnnotation( PrimaryKeyJoinColumn.class );
					if ( pc != null && pc.foreignKey() != null
							&& !StringHelper.isEmpty( pc.foreignKey().name() ) ) {
						key.setForeignKeyName( pc.foreignKey().name() );
					}
				}
				if ( onDeleteAnn != null ) {
					key.setCascadeDeleteEnabled( OnDeleteAction.CASCADE.equals( onDeleteAnn.action() ) );
				}
				else {
					key.setCascadeDeleteEnabled( false );
				}
				//we are never in a second pass at that stage, so queue it
				context.getMetadataCollector().addSecondPass( new JoinedSubclassFkSecondPass( jsc, inheritanceJoinedColumns, key, context ) );
				context.getMetadataCollector().addSecondPass( new CreateKeySecondPass( jsc ) );
			}

			if ( isInheritanceRoot ) {
				// the class we are processing is the root of the hierarchy, see if we had a discriminator column
				// (it is perfectly valid for joined subclasses to not have discriminators).
				if ( discriminatorColumn != null ) {
					// we have a discriminator column
					if ( hasSubclasses || !discriminatorColumn.isImplicit() ) {
						bindDiscriminatorColumnToRootPersistentClass(
								(RootClass) persistentClass,
								discriminatorColumn,
								entityBinder.getSecondaryTables(),
								propertyHolder,
								context
						);
						//bind it again since the type might have changed
						entityBinder.bindDiscriminatorValue();
					}
				}
			}
		}
