package com.bootcamp.reactive.pago_de_servicios.handlers;

import com.bootcamp.reactive.pago_de_servicios.entities.Suministro;
import com.bootcamp.reactive.pago_de_servicios.services.SuministroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.ServerResponse.badRequest;

@Component
public class SuministroHandler {

    @Autowired
    private SuministroService suministroService;

    public Mono<ServerResponse> findAll(ServerRequest request){
        return ServerResponse.ok()
                .contentType(APPLICATION_JSON)
                .body(suministroService.findAll(), Suministro.class);
    }

    public Mono<ServerResponse> findById(ServerRequest request){
        var id = request.pathVariable("id");

        return this.suministroService.findById(id)
                .flatMap(s-> ServerResponse.ok().body(Mono.just(s), Suministro.class))
                .switchIfEmpty(ServerResponse.notFound().build());

    }

    public Mono<ServerResponse> findByNumeroAndCodigoServicio(ServerRequest request){
        var numeroOpt = request.queryParam("numero");
        var codServicioOpt = request.queryParam("codServicio");
        String numero, codServicio;

        if (!numeroOpt.isPresent() || !codServicioOpt.isPresent()) return badRequest().build();

        numero = numeroOpt.get();
        codServicio = codServicioOpt.get();

        return ServerResponse.ok()
                .contentType(APPLICATION_JSON)
                .body(suministroService.findByNumeroAndCodigoServicio(numero, codServicio), Suministro.class);

    }


    public Mono<ServerResponse> save(ServerRequest request){

        var suministroInput= request.bodyToMono(Suministro.class);

        return suministroInput
                .flatMap(suministro -> this.suministroService.save(suministro))
                .flatMap(c-> ServerResponse
                        .ok()
                        .contentType(APPLICATION_JSON)
                        .body(Mono.just(c), Suministro.class));

    }

    public Mono<ServerResponse> delete(ServerRequest serverRequest) {
        String suministroId = serverRequest.pathVariable("id");

        return this.suministroService.delete(suministroId)
                .then(ServerResponse.ok().build());

    }


}
