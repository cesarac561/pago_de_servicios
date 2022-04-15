package com.bootcamp.reactive.pago_de_servicios.handlers;

import com.bootcamp.reactive.pago_de_servicios.entities.Cliente;
import com.bootcamp.reactive.pago_de_servicios.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Component
public class ClienteHandler {

    @Autowired
    private ClienteService clienteService;

    public Mono<ServerResponse> findAll(ServerRequest request){
        return ServerResponse.ok()
                .contentType(APPLICATION_JSON)
                .body(clienteService.findAll(), Cliente.class);
    }

    public Mono<ServerResponse> findById(ServerRequest request){
        var id = request.pathVariable("id");

        return this.clienteService.findById(id)
                .flatMap(c-> ServerResponse.ok().body(Mono.just(c), Cliente.class))
                .switchIfEmpty(ServerResponse.notFound().build());

    }


    public Mono<ServerResponse> save(ServerRequest request){

        var clienteInput= request.bodyToMono(Cliente.class);

        return clienteInput
                .flatMap(cliente -> this.clienteService.save(cliente))
                .flatMap(c-> ServerResponse
                        .ok()
                        .contentType(APPLICATION_JSON)
                        .body(Mono.just(c), Cliente.class));

    }

    public Mono<ServerResponse> delete(ServerRequest serverRequest) {
        String clienteId = serverRequest.pathVariable("id");

        return this.clienteService.delete(clienteId)
                .then(ServerResponse.ok().build());

    }


}
