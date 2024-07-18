package dynamic_top_k.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI createRestfulApi() {
        OpenAPI openAPI = new OpenAPI();
        openAPI.setInfo(myApiInfo());
        return openAPI;
    }
    private io.swagger.v3.oas.models.info.Info myApiInfo() {
        Info info = new Info();
        info.setTitle("Dynamic Top-K Ranking Service");
        info.setDescription("API Doc");
        info.setDescription("1.0.0");
        return info;
    }
}
