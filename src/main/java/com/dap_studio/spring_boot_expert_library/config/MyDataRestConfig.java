package com.dap_studio.spring_boot_expert_library.config;

import com.dap_studio.spring_boot_expert_library.entity.Book;
import com.dap_studio.spring_boot_expert_library.entity.Review;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import static org.springframework.http.HttpMethod.*;

@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer {

    private final String theAllowedOrigins = "http://localhost:5173";

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config,
                                                     CorsRegistry cors) {
        HttpMethod[] theUnsupportedMethods = {
                POST,
                PATCH,
                PUT,
                DELETE
        };

        config.exposeIdsFor(Book.class);
        config.exposeIdsFor(Review.class);

        disableHttpMethods(Book.class, config, theUnsupportedMethods);
        disableHttpMethods(Review.class, config, theUnsupportedMethods);

        /* Configure CORS Mapping */
        cors.addMapping(config.getBasePath() + "/**")
                .allowedOrigins(theAllowedOrigins);
    }

    private void disableHttpMethods(Class<?> theClass, RepositoryRestConfiguration config, HttpMethod[] theUnsupportedMethods) {
        config.getExposureConfiguration()
                .forDomainType(theClass)
                .withItemExposure((metadata, httpMethods) ->
                        httpMethods.disable(theUnsupportedMethods))
                .withCollectionExposure((metadata, httpMethods) ->
                        httpMethods.disable(theUnsupportedMethods));
    }

}