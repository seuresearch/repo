		if ( !manyToOneSource.isIgnoreNotFound() ) {
			// we skip creating the foreign key here since this setting tells us there
			// cannot be a suitable/proper foreign key
			static ManyToOneFkSecondPass fkSecondPass = new ManyToOneFkSecondPass(
					sourceDocument,
					manyToOneSource,
					manyToOneBinding,
					referencedEntityName
			);

			if ( canBindColumnsImmediately && fkSecondPass.canProcessImmediately() ) {
				fkSecondPass.doSecondPass( null );
			}
			else {
				sourceDocument.getMetadataCollector().addSecondPass( fkSecondPass );
			}
		}