package com.napier.chatbot.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.context.request.RequestContextListener;



/**
 *
 * @author NAP1051
 */

@EnableCaching
@Configuration
@Profile("dev")
@PropertySource("classpath:application-dev.properties")
public class DevConfiguration  {
	
	private static final Logger logger = LoggerFactory
			.getLogger(DevConfiguration.class);

	 @Autowired
	 private Environment env;
	 
	
	/* @Bean
		public CacheManager cacheManager() {
			return new EhCacheCacheManager(ehCacheCacheManager().getObject());
		}*/

		/*@Bean
		public EhCacheManagerFactoryBean ehCacheCacheManager() {
			EhCacheManagerFactoryBean cmfb = new EhCacheManagerFactoryBean();
			cmfb.setConfigLocation(new ClassPathResource("ehcache.xml"));
			cmfb.setShared(true);
			return cmfb;
		}*/
	
	

	
	@Bean 
	public RequestContextListener requestContextListener(){
		logger.debug("Start initialize RequestContextListener()");
	    return new RequestContextListener();
	}
	 
}



