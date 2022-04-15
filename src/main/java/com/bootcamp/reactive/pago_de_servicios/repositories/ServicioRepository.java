package com.bootcamp.reactive.pago_de_servicios.repositories;

import com.bootcamp.reactive.pago_de_servicios.entities.Servicio;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ServicioRepository extends ReactiveMongoRepository<Servicio,String> {

    Flux<Servicio> findByCanalId(String canalId);

    Mono<Servicio> findByCodigo(String servicioCodigo);
}
