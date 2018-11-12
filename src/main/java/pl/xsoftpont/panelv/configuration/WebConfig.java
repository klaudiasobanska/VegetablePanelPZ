package pl.xsoftpont.panelv.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(
                "/static/**","/webapp/static/**"
                )
                .addResourceLocations(
                        "classpath:/static/",
                        "classpath:/webapp/static/",
                        "/static/",
                        "/webapp/static/"

                        );
    }
}
