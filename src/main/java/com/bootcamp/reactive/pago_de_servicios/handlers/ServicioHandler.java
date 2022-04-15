package com.bootcamp.reactive.pago_de_servicios.handlers;

import com.bootcamp.reactive.pago_de_servicios.entities.Servicio;
import com.bootcamp.reactive.pago_de_servicios.repositories.CanalRepository;
import com.bootcamp.reactive.pago_de_servicios.services.ServicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Component
public class ServicioHandler {

    @Autowired
    private ServicioService servicioService;

    @Autowired
    private CanalRepository canalRepository;

    public Mono<ServerResponse> findAll(ServerRequest request){
        return ServerResponse.ok()
                .contentType(APPLICATION_JSON)
                .body(servicioService.findAll(), Servicio.class);
    }

    public Mono<ServerResponse> findById(ServerRequest request){
        var id = request.pathVariable("id");

        return this.servicioService.findById(id)
                .flatMap(s-> ServerResponse.ok().body(Mono.just(s), Servicio.class))
                .switchIfEmpty(ServerResponse.notFound().build());

    }

    public Mono<ServerResponse> findByCanalId(ServerRequest request){
        var canalId = request.pathVariable("canalId");

        return ServerResponse.ok()
                .contentType(APPLICATION_JSON)
                .body(servicioService.findByCanalId(canalId), Servicio.class);
    }


    public Mono<ServerResponse> obtenerServiciosPorCanal(ServerRequest serverRequest){
        String canalCodigo =serverRequest.pathVariable("canalCodigo");

        return this.canalRepository
                .findByCodigo(canalCodigo)
                .flatMap(canal -> this.servicioService.findByCanalId(canal.getId()).collectList())
                .flatMap(list-> ServerResponse.ok().body(Mono.just(list), Servicio.class));
    }

    public Mono<ServerResponse> save(ServerRequest request){

        var servicioInput= request.bodyToMono(Servicio.class);

        return servicioInput
                .flatMap(servicio-> this.servicioService.save(servicio))
                .flatMap(s-> ServerResponse
                        .ok()
                        .contentType(APPLICATION_JSON)
                        .body(Mono.just(s), Servicio.class));

    }

    public Mono<ServerResponse> delete(ServerRequest serverRequest) {
        String servicioId = serverRequest.pathVariable("id");

        return this.servicioService.delete(servicioId)
                .then(ServerResponse.ok().build());

    }


}
