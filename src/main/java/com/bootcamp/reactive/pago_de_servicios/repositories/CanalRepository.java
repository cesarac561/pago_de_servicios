package com.bootcamp.reactive.pago_de_servicios.repositories;

import com.bootcamp.reactive.pago_de_servicios.entities.Canal;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface CanalRepository extends ReactiveMongoRepository<Canal,String> {

    Mono<Canal> findByCodigo(String canalCodigo);
}
