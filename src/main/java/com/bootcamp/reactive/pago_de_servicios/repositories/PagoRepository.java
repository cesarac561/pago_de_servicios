package com.bootcamp.reactive.pago_de_servicios.repositories;

import com.bootcamp.reactive.pago_de_servicios.entities.Pago;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PagoRepository extends ReactiveMongoRepository<Pago,String> {

}
