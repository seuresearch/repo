	/**
	 * Finish the process of collection-loading for this bound result set.  Mainly this
	 * involves cleaning up resources and notifying the collections that loading is
	 * complete.
	 *
	 * @param persister The persister for which to complete loading.
	 */
	public void endLoadingCollections(CollectionPersister persister) {
		final SharedSessionContractImplementor session = getLoadContext().getPersistenceContext().getSession();
		if ( !loadContexts.hasLoadingCollectionEntries()
				&& localLoadingCollectionKeys.isEmpty() ) {
			return;
		}

		// in an effort to avoid concurrent-modification-exceptions (from
		// potential recursive calls back through here as a result of the
		// eventual call to PersistentCollection#endRead), we scan the
		// internal loadingCollections map for matches and store those matches
		// in a temp collection.  the temp collection is then used to "drive"
		// the #endRead processing.
		List<LoadingCollectionEntry> matches = null;
		final Iterator itr = localLoadingCollectionKeys.iterator();
		while ( itr.hasNext() ) {
			final CollectionKey collectionKey = (CollectionKey) itr.next();
			final LoadingCollectionEntry lce = loadContexts.locateLoadingCollectionEntry( collectionKey );
			if ( lce == null ) {
				LOG.loadingCollectionKeyNotFound( collectionKey );
			}
			else if ( lce.getResultSet() == resultSet && lce.getPersister() == persister ) {
				if ( matches == null ) {
					matches = new ArrayList<>();
				}
				matches.add( lce );
				if ( lce.getCollection().getOwner() == null ) {
					session.getPersistenceContext().addUnownedCollection(
							new CollectionKey(
									persister,
									lce.getKey(),
									persister.getOwnerEntityPersister().getEntityMetamodel().getEntityMode()
							),
							lce.getCollection()
					);
				}
				LOG.tracev( "Removing collection load entry [{0}]", lce );

				// todo : i'd much rather have this done from #endLoadingCollection(CollectionPersister,LoadingCollectionEntry)...
				loadContexts.unregisterLoadingCollectionXRef( collectionKey );
				itr.remove();
			}
		}

		endLoadingCollections( persister, matches );
		if ( localLoadingCollectionKeys.isEmpty() ) {
			// todo : hack!!!
			// NOTE : here we cleanup the load context when we have no more local
			// LCE entries.  This "works" for the time being because really
			// only the collection load contexts are implemented.  Long term,
			// this cleanup should become part of the "close result set"
			// processing from the (sandbox/jdbc) jdbc-container code.
			loadContexts.cleanup( resultSet );
		}
	}
