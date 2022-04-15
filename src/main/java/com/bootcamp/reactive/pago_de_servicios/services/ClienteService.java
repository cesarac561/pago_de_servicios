package com.bootcamp.reactive.pago_de_servicios.services;

import com.bootcamp.reactive.pago_de_servicios.entities.Cliente;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ClienteService {
    Flux<Cliente> findAll();
    Mono<Cliente> findById(String id);
    Mono<Cliente> save(Cliente cliente);
    Mono<Void> delete(String id);
}
