package com.bootcamp.reactive.pago_de_servicios.routes;

import com.bootcamp.reactive.pago_de_servicios.handlers.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class RouterConfiguration {

    @Bean
    public RouterFunction<ServerResponse> canalRoutes(CanalHandler canalHandler) {
        return RouterFunctions.nest(RequestPredicates.path("/canales"),
                RouterFunctions
                        .route(GET(""), canalHandler::findAll)
                        .andRoute(GET("/{id}"), canalHandler::findById)
                        .andRoute(GET("/codigo/{canalCodigo}"), canalHandler::findByCodigo)
                        .andRoute(POST("").and(contentType(APPLICATION_JSON)), canalHandler::save)
                        .andRoute(DELETE("/{id}"), canalHandler::delete)
            );
    }

    @Bean
    public RouterFunction<ServerResponse> clienteRoutes(ClienteHandler clienteHandler) {
        return RouterFunctions.nest(RequestPredicates.path("/clientes"),
                RouterFunctions
                        .route(GET(""), clienteHandler::findAll)
                        .andRoute(GET("/{id}"), clienteHandler::findById)
                        .andRoute(POST("").and(contentType(APPLICATION_JSON)), clienteHandler::save)
                        .andRoute(DELETE("/{id}"), clienteHandler::delete)
        );
    }

    @Bean
    public RouterFunction<ServerResponse> pagoRoutes(PagoHandler pagoHandler) {
        return RouterFunctions.nest(RequestPredicates.path("/pagos"),
                RouterFunctions
                        .route(GET(""), pagoHandler::findAll)
                        .andRoute(GET("/{id}"), pagoHandler::findById)
                        .andRoute(POST("").and(contentType(APPLICATION_JSON)), pagoHandler::save)
                        .andRoute(POST("/registrar_pago").and(contentType(APPLICATION_JSON)), pagoHandler::registrarPago)
                        .andRoute(DELETE("/{id}"), pagoHandler::delete)
        );
    }

    @Bean
    public RouterFunction<ServerResponse> servicioRoutes(ServicioHandler servicioHandler) {
        return RouterFunctions.nest(RequestPredicates.path("/servicios"),
                RouterFunctions
                        .route(GET(""), servicioHandler::findAll)
                        .andRoute(GET("/{id}"), servicioHandler::findById)
                        .andRoute(GET("/id_canal/{canalId}"), servicioHandler::findByCanalId)
                        .andRoute(GET("/codigo_canal/{canalCodigo}"), servicioHandler::obtenerServiciosPorCanal)
                        .andRoute(POST("").and(contentType(APPLICATION_JSON)), servicioHandler::save)
                        .andRoute(DELETE("/{id}"), servicioHandler::delete)
        );
    }

    @Bean
    public RouterFunction<ServerResponse> suministroRoutes(SuministroHandler suministroHandler) {
        return RouterFunctions.nest(RequestPredicates.path("/suministros"),
                RouterFunctions
                        .route(GET(""), suministroHandler::findAll)
                        .andRoute(GET("/query"), suministroHandler::findByNumeroAndCodigoServicio)
                        .andRoute(GET("/{id}"), suministroHandler::findById)
                        .andRoute(POST("").and(contentType(APPLICATION_JSON)), suministroHandler::save)
                        .andRoute(DELETE("/{id}"), suministroHandler::delete)
        );
    }

}
