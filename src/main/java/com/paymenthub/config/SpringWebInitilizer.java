package com.paymenthub.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class SpringWebInitilizer extends AbstractAnnotationConfigDispatcherServletInitializer{

	@Override
	protected Class<?>[] getRootConfigClasses() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		// TODO Auto-generated method stub
		return new Class[] {WebMVCConfig.class};
	}

	@Override
	protected String[] getServletMappings() {
		String[] allowedURLMapping = {"/"};
		return allowedURLMapping;
	}

}
