package com.bootcamp.reactive.pago_de_servicios.repositories;

import com.bootcamp.reactive.pago_de_servicios.entities.Suministro;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface SuministroRepository extends ReactiveMongoRepository<Suministro,String> {

    Mono<Suministro> findByNumeroAndServicioId(String numero, String servicioId);
}
