package com.bootcamp.reactive.pago_de_servicios.services.impl;

import com.bootcamp.reactive.pago_de_servicios.entities.Canal;
import com.bootcamp.reactive.pago_de_servicios.entities.Servicio;
import com.bootcamp.reactive.pago_de_servicios.repositories.CanalRepository;
import com.bootcamp.reactive.pago_de_servicios.services.CanalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CanalServiceImpl implements CanalService {

    @Autowired
    private CanalRepository canalRepository;

    @Override
    public Flux<Canal> findAll() {
        return this.canalRepository.findAll();
    }

    @Override
    public Mono<Canal> findById(String id) {
        return this.canalRepository.findById(id);
    }

    @Override
    public Mono<Canal> findByCodigo(String canalCodigo) {
        return this.canalRepository.findByCodigo(canalCodigo);
    }

    @Override
    public Mono<Canal> save(Canal canal) {
        return this.canalRepository.save(canal);
    }

    @Override
    public Mono<Void> delete(String id) {

        return this.canalRepository.findById(id)
            .switchIfEmpty(Mono.error(new Exception("Canal no encontrado")))
            .flatMap(canal -> {
                return this.canalRepository.delete(canal);
            });

    }

}
