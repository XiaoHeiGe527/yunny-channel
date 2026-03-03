package com.yunny.channel.config;

import freemarker.cache.ClassTemplateLoader;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class FreeMarkerConfig {

	public freemarker.template.Configuration getConfiguration(String templPath) throws IOException {
		freemarker.template.Configuration configuration = new freemarker.template.Configuration(
				freemarker.template.Configuration.VERSION_2_3_23);
		configuration.setClassForTemplateLoading(this.getClass(), templPath);
		configuration.setTemplateLoader(new ClassTemplateLoader(this.getClass(), templPath));
		configuration.setDefaultEncoding("utf-8");
		return configuration;
	}
}
