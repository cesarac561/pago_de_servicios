package com.bootcamp.reactive.pago_de_servicios.services;

import com.bootcamp.reactive.pago_de_servicios.entities.Canal;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CanalService {
    Flux<Canal> findAll();
    Mono<Canal> findById(String id);
    Mono<Canal> findByCodigo(String canalCodigo);
    Mono<Canal> save(Canal canal);
    Mono<Void> delete(String id);
}
