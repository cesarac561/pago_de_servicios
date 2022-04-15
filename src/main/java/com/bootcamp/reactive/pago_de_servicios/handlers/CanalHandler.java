package com.bootcamp.reactive.pago_de_servicios.handlers;

import com.bootcamp.reactive.pago_de_servicios.entities.Canal;
import com.bootcamp.reactive.pago_de_servicios.services.CanalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Component
public class CanalHandler {

    @Autowired
    private CanalService canalService;

    public Mono<ServerResponse> findAll(ServerRequest request){
        return ServerResponse.ok()
                .contentType(APPLICATION_JSON)
                .body(canalService.findAll(), Canal.class);
    }

    public Mono<ServerResponse> findById(ServerRequest request){
        var id = request.pathVariable("id");

        return this.canalService.findById(id)
                .flatMap(c-> ServerResponse.ok().body(Mono.just(c), Canal.class))
                .switchIfEmpty(ServerResponse.notFound().build());

    }

    public Mono<ServerResponse> findByCodigo(ServerRequest request){
        var canalCodigo = request.pathVariable("canalCodigo");

        return this.canalService.findByCodigo(canalCodigo)
                .flatMap(c-> ServerResponse.ok().body(Mono.just(c), Canal.class))
                .switchIfEmpty(ServerResponse.notFound().build());

    }

    public Mono<ServerResponse> save(ServerRequest request){

        var canalInput= request.bodyToMono(Canal.class);

        return canalInput
                .flatMap(canal-> this.canalService.save(canal))
                .flatMap(c-> ServerResponse
                        .ok()
                        .contentType(APPLICATION_JSON)
                        .body(Mono.just(c), Canal.class));

    }

    public Mono<ServerResponse> delete(ServerRequest serverRequest) {
        String canalId = serverRequest.pathVariable("id");

        return this.canalService.delete(canalId)
                .then(ServerResponse.ok().build());

    }


}
