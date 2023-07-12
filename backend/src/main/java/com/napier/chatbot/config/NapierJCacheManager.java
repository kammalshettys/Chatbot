/*package com.napier.chatbot.config;

import java.util.Collection;
import java.util.LinkedHashSet;

import javax.cache.CacheManager;

import org.springframework.cache.Cache;
import org.springframework.cache.jcache.JCacheCache;
import org.springframework.cache.jcache.JCacheCacheManager;

public class NapierJCacheManager extends JCacheCacheManager{
	
	
	@Override
	protected Collection<Cache> loadCaches() {
		CacheManager cacheManager = getCacheManager();
		Collection<Cache> caches = new LinkedHashSet<Cache>();
		for (String cacheName : cacheManager.getCacheNames()) {
			javax.cache.Cache<String, Object> jcache = cacheManager.getCache(cacheName, String.class, Object.class);
			caches.add((Cache) new MyCustomAdaptingCache(jcache, isAllowNullValues()));
		}
		return caches;
	}
	
	
	@Override
	protected Cache getMissingCache(String name) {
		CacheManager cacheManager = getCacheManager();		
		// Check the JCache cache again (in case the cache was added at runtime)
		javax.cache.Cache<Object, Object> jcache = cacheManager.getCache(name);
		if (jcache != null) {
			return new JCacheCache(jcache, isAllowNullValues());
		}
		return null;
	}




}

*/