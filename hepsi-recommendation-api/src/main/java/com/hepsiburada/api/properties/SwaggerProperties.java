package com.hepsiburada.api.properties;

import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Data
@Getter
@ConfigurationProperties(prefix = "swagger.api-info")
public class SwaggerProperties {

    private String title;
    private String description;
    private String version;
    private String termsOfServiceUrl;

    private Contact contact;
    private String license;
    private String licenseUrl;

    private List<VendorExtension> vendorExtensions;

    @Data
    public static class Contact {
        private String name;
        private String url;
        private String email;
    }

    @Data
    public static class VendorExtension {
        private String name;
        private String value;
    }

}
