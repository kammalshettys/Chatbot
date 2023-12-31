package com.napier.chatbot.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;



@EnableCaching
@Configuration
public class EnableCacheManagement {
	
	@Bean
    public CacheManager cacheManager() {
		System.out.println("Ehcache enalbe");
        return new EhCacheCacheManager(ehCacheCacheManager().getObject());
    }

	 @Bean
	    public EhCacheManagerFactoryBean ehCacheCacheManager() {
	    	System.out.println("*********** ehCacheCacheManager() called *****************");
	        EhCacheManagerFactoryBean factory = new EhCacheManagerFactoryBean();
	        factory.setConfigLocation(new ClassPathResource("ehcache.xml"));
	        factory.setShared(true);
	        return factory;
	    }

}
