package br.edu.atitus.gatewayservice.configs;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayConfig {

    @Bean
    RouteLocator getGatewayRouter(RouteLocatorBuilder builder) {
        return  builder.routes()

                //Rota para o serviço httpbin
                .route(p -> p
                        .path("/get")
                        .filters(f -> f
                                .addRequestHeader("X-USER-NAME","username")
                                .addRequestParameter("name","fulano"))
                        .uri("http://httpbin.org:80"))

                //Rota para o serviço de produtos
                .route(p -> p
                .path("/products/**")
                        .uri("lb://product-service"))

                //Rota para o serviço de moedas
                .route(p -> p
                        .path("/currency/**")
                        .uri("lb://currency-service"))

                //Rota para o servico de Login
                .route(p -> p
                        .path("/auth/**")
                        .uri("lb://auth-service"))

                //Rota para o servico de WS
                .route(p -> p
                        .path("/ws/products/**")
                        .uri("lb://product-service"))

                //Rota para servico de Pedidos
                .route(p -> p
                        .path("/ws/orders/**")
                        .uri("lb://order-service"))

                //Rota para servico de Greeting
                .route(p -> p
                        .path("/greeting/**")
                        .uri("lb://greeting-service"))

                .build();
    }
}
