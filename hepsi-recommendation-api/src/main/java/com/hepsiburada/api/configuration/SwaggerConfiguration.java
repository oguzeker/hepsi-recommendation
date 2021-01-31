package com.hepsiburada.api.configuration;

import com.hepsiburada.api.properties.SwaggerProperties;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.StringVendorExtension;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
@AllArgsConstructor
public class SwaggerConfiguration {

    public static final String TAG_BROWSING_HISTORY = "Browsing History Management Screen";
    public static final String TAG_RECOMMENDATION = "Recommendation Query Screen";

    public static final String DESC_BROWSING_HISTORY = "Search and manage browsing history";
    public static final String DESC_RECOMMENDATION = "Search for product recommendations";

    private static final String BASE_PACKAGE_NAME = "com.hepsiburada";

    private final SwaggerProperties properties;

    @Bean
    public Docket api(ApiInfo apiInfo) {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .apis(RequestHandlerSelectors.basePackage(BASE_PACKAGE_NAME))
                .paths(PathSelectors.any())
                .build().apiInfo(apiInfo)
                .tags(new Tag(TAG_BROWSING_HISTORY, DESC_BROWSING_HISTORY))
                .tags(new Tag(TAG_RECOMMENDATION, DESC_RECOMMENDATION));
    }

    @Bean
    public ApiInfo apiInfo() {
        SwaggerProperties.Contact contact = properties.getContact();
        List<SwaggerProperties.VendorExtension> vendorExtensions = properties.getVendorExtensions();

        return new ApiInfo(
                properties.getTitle(),
                properties.getDescription(),
                properties.getVersion(),
                properties.getTermsOfServiceUrl(),
                new Contact(contact.getName(), contact.getUrl(), contact.getEmail()),
                properties.getLicense(),
                properties.getLicenseUrl(),
                vendorExtensions.stream()
                        .map(extension -> new StringVendorExtension(extension.getName(), extension.getValue()))
                        .collect(Collectors.toList()));
    }

}
