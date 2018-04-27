	/**
	 * Retrieve the collection that is being loaded as part of processing this
	 * result set.
	 * <p/>
	 * Basically, there are two valid return values from this method:<ul>
	 * <li>an instance of {@link org.hibernate.collection.spi.PersistentCollection} which indicates to
	 * continue loading the result set row data into that returned collection
	 * instance; this may be either an instance already associated and in the
	 * midst of being loaded, or a newly instantiated instance as a matching
	 * associated collection was not found.</li>
	 * <li><i>null</i> indicates to ignore the corresponding result set row
	 * data relating to the requested collection; this indicates that either
	 * the collection was found to already be associated with the persistence
	 * context in a fully loaded state, or it was found in a loading state
	 * associated with another result set processing context.</li>
	 * </ul>
	 *
	 * @param persister The persister for the collection being requested.
	 * @param key The key of the collection being requested.
	 *
	 * @return The loading collection (see discussion above).
	 */
	public PersistentCollection getLoadingCollection(final CollectionPersister persister, final Serializable key) {
		final EntityMode em = persister.getOwnerEntityPersister().getEntityMetamodel().getEntityMode();
		final CollectionKey collectionKey = new CollectionKey( persister, key, em );
		if ( LOG.isTraceEnabled() ) {
			LOG.tracev( "Starting attempt to find loading collection [{0}]",
					MessageHelper.collectionInfoString( persister.getRole(), key ) );
		}
		final LoadingCollectionEntry loadingCollectionEntry = loadContexts.locateLoadingCollectionEntry( collectionKey );
		if ( loadingCollectionEntry == null ) {
			// look for existing collection as part of the persistence context
			PersistentCollection collection = loadContexts.getPersistenceContext().getCollection( collectionKey );
			if ( collection != null ) {
				if ( collection.wasInitialized() ) {
					LOG.trace( "Collection already initialized; ignoring" );
					// ignore this row of results! Note the early exit
					return null;
				}
				LOG.trace( "Collection not yet initialized; initializing" );
			}
			else {
				final Object owner = loadContexts.getPersistenceContext().getCollectionOwner( key, persister );
				final boolean newlySavedEntity = owner != null
						&& loadContexts.getPersistenceContext().getEntry( owner ).getStatus() != Status.LOADING;
				if ( newlySavedEntity ) {
					// important, to account for newly saved entities in query
					// todo : some kind of check for new status...
					LOG.trace( "Owning entity already loaded; ignoring" );
					return null;
				}
				// create one
				LOG.tracev( "Instantiating new collection [key={0}, rs={1}]", key, resultSet );
				collection = persister.getCollectionType().instantiate(
						loadContexts.getPersistenceContext().getSession(), persister, key );
			}
			collection.beforeInitialize( persister, -1 );
			collection.beginRead();
			localLoadingCollectionKeys.add( collectionKey );
			loadContexts.registerLoadingCollectionXRef( collectionKey, new LoadingCollectionEntry( resultSet, persister, key, collection ) );
			return collection;
