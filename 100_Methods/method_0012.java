		// check to see if it is in the second-level cache
		if ( session.getCacheMode().isGetEnabled() && hasCache() ) {
			final EntityRegionAccessStrategy cache = getCacheAccessStrategy();
			final Object ck = cache.generateCacheKey( id, this, session.getFactory(), session.getTenantIdentifier() );
			final Object ce = CacheHelper.fromSharedCache( session, ck, getCacheAccessStrategy() );
			if ( ce != null ) {
				return Boolean.FALSE;
			}
		}