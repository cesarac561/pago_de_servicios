package com.bootcamp.reactive.pago_de_servicios.handlers;

import com.bootcamp.reactive.pago_de_servicios.entities.InputPago;
import com.bootcamp.reactive.pago_de_servicios.entities.Pago;
import com.bootcamp.reactive.pago_de_servicios.services.PagoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.concurrent.ExecutionException;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.ServerResponse.badRequest;

@Slf4j
@Component
public class PagoHandler {

    @Autowired
    private PagoService pagoService;

    public Mono<ServerResponse> findAll(ServerRequest request){
        return ServerResponse.ok()
                .contentType(APPLICATION_JSON)
                .body(pagoService.findAll(), Pago.class);
    }

    public Mono<ServerResponse> findById(ServerRequest request){
        var id = request.pathVariable("id");

        return this.pagoService.findById(id)
                .flatMap(p-> ServerResponse.ok().body(Mono.just(p), Pago.class))
                .switchIfEmpty(ServerResponse.notFound().build());

    }


    public Mono<ServerResponse> save(ServerRequest request){

        var pagoInput= request.bodyToMono(Pago.class);

        return pagoInput
                .flatMap(pago-> this.pagoService.save(pago))
                .flatMap(c-> ServerResponse
                        .ok()
                        .contentType(APPLICATION_JSON)
                        .body(Mono.just(c), Pago.class));

    }

    public Mono<ServerResponse> registrarPago(ServerRequest request) {

        var pagoInput= request.bodyToMono(InputPago.class);

        return pagoInput
                .flatMap(inputPago -> this.pagoService.registrarPago(inputPago))
                .flatMap(p -> ServerResponse
                        .ok()
                        .contentType(APPLICATION_JSON)
                        .body(Mono.just(p), Pago.class));

    }

    public Mono<ServerResponse> delete(ServerRequest serverRequest) {
        String pagoId = serverRequest.pathVariable("id");

        return this.pagoService.delete(pagoId)
                .then(ServerResponse.ok().build());

    }

}
