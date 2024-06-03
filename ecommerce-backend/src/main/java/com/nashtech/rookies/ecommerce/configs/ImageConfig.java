package com.nashtech.rookies.ecommerce.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public @Configuration
class ImageConfig implements WebMvcConfigurer {

	private final String imagePath;
	
	public ImageConfig(@Value("${app.image.folder}") String imagePath) {
		this.imagePath = imagePath;
	}

	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
          .addResourceHandler("/images/**")
          .addResourceLocations(String.format("file:%s", imagePath));	
    }
} 
