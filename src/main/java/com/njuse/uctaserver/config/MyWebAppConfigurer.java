package com.njuse.uctaserver.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Created by Vinson on 2019-03-21.
 */
@Configuration
public class MyWebAppConfigurer implements WebMvcConfigurer {
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/image/**").addResourceLocations("/home/img/");
	}
}
