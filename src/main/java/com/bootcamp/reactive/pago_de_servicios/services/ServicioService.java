package com.bootcamp.reactive.pago_de_servicios.services;

import com.bootcamp.reactive.pago_de_servicios.entities.Servicio;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ServicioService {
    Flux<Servicio> findAll();
    Mono<Servicio> findById(String id);
    Mono<Servicio> findByCodigo(String servicioCodigo);
    Flux<Servicio> findByCanalId(String canalId);
    Mono<Servicio> save(Servicio servicio);
    Mono<Void> delete(String id);
}
