package pt.ulisboa.tecnico.rnl.dei.dms.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {
  private static final long MAX_AGE_SECS = 3600;

  @Override
  public void addCorsMappings(@SuppressWarnings("null") CorsRegistry registry) {
    registry.addMapping("/**")
        .allowedOriginPatterns("*")
        .allowCredentials(true)
        .allowedMethods("HEAD", "OPTIONS", "GET", "POST", "PUT", "PATCH",
                        "DELETE")
        .maxAge(MAX_AGE_SECS);
  }
}
