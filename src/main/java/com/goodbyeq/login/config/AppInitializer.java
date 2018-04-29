package com.goodbyeq.login.config;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;

import org.springframework.util.ObjectUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.goodbyeq.authoriation.api.impl.JWTTokenValidator;
import com.goodbyeq.authorization.framework.OAuthTokenAuthenticationProcessingFilter;
import com.goodbyeq.authorize.api.TokenValidator;

/**
 * Initialize application
 *
 */
public class AppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	private JWTTokenValidator validator;

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] { WebMvcConfig.class };
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] { AppConfig.class };
	}

	@Override
	protected Filter[] getServletFilters() {
		return new Filter[] { getCharacterEncodingFilter(), getOauthTokenFilter() };
	}

	private CharacterEncodingFilter getCharacterEncodingFilter() {
		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
		characterEncodingFilter.setEncoding("UTF-8");
		characterEncodingFilter.setForceEncoding(true);
		return characterEncodingFilter;
	}

	private OAuthTokenAuthenticationProcessingFilter getOauthTokenFilter() {
		List<TokenValidator> validators = new ArrayList<TokenValidator>();
		validators.add(validator);
		return new OAuthTokenAuthenticationProcessingFilter();
	}
}
