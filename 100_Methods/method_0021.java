		if ( InheritanceType.JOINED.equals( inheritanceState.getType() ) ) {
			if ( inheritanceState.hasParents() ) {
				onDeleteAppropriate = true;
				final JoinedSubclass jc = ( JoinedSubclass ) persistentClass;
				SimpleValue key = new DependantValue( context.getMetadataCollector(), jc.getTable(), jc.getIdentifier() );
				jc.setKey( key );
				ForeignKey fk = clazzToProcess.getAnnotation( ForeignKey.class );
				if ( fk != null && !BinderHelper.isEmptyAnnotationValue( fk.name() ) ) {
					key.setForeignKeyName( fk.name() );
				}
				else {
					final PrimaryKeyJoinColumn pkJoinColumn = clazzToProcess.getAnnotation( PrimaryKeyJoinColumn.class );
					if ( pkJoinColumn != null && pkJoinColumn.foreignKey() != null
							&& !StringHelper.isEmpty( pkJoinColumn.foreignKey().name() ) ) {
						key.setForeignKeyName( pkJoinColumn.foreignKey().name() );
					}
				}
				if ( onDeleteAnn != null ) {
					key.setCascadeDeleteEnabled( OnDeleteAction.CASCADE.equals( onDeleteAnn.action() ) );
				}
				else {
					key.setCascadeDeleteEnabled( false );
				}
				//we are never in a second pass at that stage, so queue it
				context.getMetadataCollector().addSecondPass( new JoinedSubclassFkSecondPass( jc, inheritanceJoinedColumns, key, context ) );
				context.getMetadataCollector().addSecondPass( new CreateKeySecondPass( jc ) );
			}
		}