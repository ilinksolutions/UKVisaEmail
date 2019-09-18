package com.ilinksolutions.UKVisaEmail;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import com.ilinksolutions.UKVisaEmail.UKVisaEmailApplication;

public class ServletInitializer extends SpringBootServletInitializer
{
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application)
	{
		return application.sources(UKVisaEmailApplication.class);
	}
}
