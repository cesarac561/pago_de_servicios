package com.bootcamp.reactive.pago_de_servicios.services;

import com.bootcamp.reactive.pago_de_servicios.entities.InputPago;
import com.bootcamp.reactive.pago_de_servicios.entities.Pago;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PagoService {
    Flux<Pago> findAll();
    Mono<Pago> findById(String id);
    Mono<Pago> save(Pago pago);
    Mono<Pago> registrarPago(InputPago inputPago);
    Mono<Void> delete(String id);
}
