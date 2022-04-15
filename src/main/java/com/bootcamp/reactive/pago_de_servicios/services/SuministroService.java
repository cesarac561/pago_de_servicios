package com.bootcamp.reactive.pago_de_servicios.services;

import com.bootcamp.reactive.pago_de_servicios.entities.Suministro;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SuministroService {
    Flux<Suministro> findAll();
    Mono<Suministro> findById(String id);
    Mono<Suministro> findByNumeroAndServicioId(String numero, String servicioId);
    Mono<Suministro> findByNumeroAndCodigoServicio(String numero, String codServicio);
    Mono<Suministro> save(Suministro suministro);
    Mono<Void> delete(String id);
}
