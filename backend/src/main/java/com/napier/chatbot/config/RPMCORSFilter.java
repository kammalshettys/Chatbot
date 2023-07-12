package com.napier.chatbot.config;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

public class RPMCORSFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(RPMCORSFilter.class);

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

        HttpServletResponse response = (HttpServletResponse) res;

        response.setHeader("Access-Control-Allow-Methods", "PUT, POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Allow-Headers", "Origin, Accept, Content-Type, X-Requested-With, X-Requested-By, X-Auth-Token, X-Timezone,X-Locale,X-Facility-Name,X-Customer-Id,X-Action-By,X-Role-Name,X-Person-Id,X-AUTH-SESSION,X-Facility-Id,X-User-Id,X-GNStatus-Id");
        response.setHeader("Access-Control-Expose-Headers", "X-Auth-Token, X-Timezone,X-Locale,X-Facility-Name,X-Customer-Id,X-Action-By,X-Role-Name,X-Person-Id,X-AUTH-SESSION,X-Facility-Id");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Max-Age", "604800");


        HttpServletRequest request = (HttpServletRequest) req;
        String method = request.getMethod();

        if ("OPTIONS".equals(method)) {
            LOGGER.debug("Options request. Setting status 200");
            response.setStatus(HttpStatus.OK.value());
        } else {
            LOGGER.debug("Will process additional filters");
            chain.doFilter(req, res);
        }
    }

	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

   
}
