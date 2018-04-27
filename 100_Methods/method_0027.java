		if ( joinColumn != null
				&& joinColumn.foreignKey().value() == ConstraintMode.NO_CONSTRAINT ) {
			// not ideal...
			value.setForeignKeyName( "none" );
		}
		else {
			final ForeignKey fk = property.getAnnotation( ForeignKey.class );
			if ( fk != null && StringHelper.isNotEmpty( fk.name() ) ) {
				value.setForeignKeyName( fk.name() );
			}
			else if ( joinColumn != null ) {
				value.setForeignKeyName( StringHelper.nullIfEmpty( joinColumn.foreignKey().name() ) );
				value.setForeignKeyDefinition( StringHelper.nullIfEmpty( joinColumn.foreignKey().foreignKeyDefinition() ) );
			}
		}