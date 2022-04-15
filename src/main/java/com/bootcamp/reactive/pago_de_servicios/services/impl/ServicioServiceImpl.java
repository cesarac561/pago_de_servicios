package com.bootcamp.reactive.pago_de_servicios.services.impl;

import com.bootcamp.reactive.pago_de_servicios.entities.Canal;
import com.bootcamp.reactive.pago_de_servicios.entities.Servicio;
import com.bootcamp.reactive.pago_de_servicios.repositories.ServicioRepository;
import com.bootcamp.reactive.pago_de_servicios.services.ServicioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Slf4j
@Service
public class ServicioServiceImpl implements ServicioService {

    @Autowired
    private ServicioRepository servicioRepository;

    @Override
    public Flux<Servicio> findAll() {
        return this.servicioRepository.findAll();
    }

    @Override
    public Mono<Servicio> findById(String id) {
        return this.servicioRepository.findById(id);
    }

    @Override
    public Mono<Servicio> findByCodigo(String servicioCodigo) {
        return this.servicioRepository.findByCodigo(servicioCodigo);
    }

    @Override
    public Flux<Servicio> findByCanalId(String canalId)
    {
        log.info("CALL findByCanalId");

        return this.servicioRepository.findByCanalId(canalId);
        //return this.servicioRepository.findByCanalId(canalId).delayElements(Duration.ofSeconds(6));
        //return  this.servicioRepository.findByCanalId(canalId).delayElements(Duration.ofSeconds((int)(Math.random()*10)));
    }

    @Override
    public Mono<Servicio> save(Servicio servicio) {
        return this.servicioRepository.save(servicio);
    }

    @Override
    public Mono<Void> delete(String id) {

        return this.servicioRepository.findById(id)
            .switchIfEmpty(Mono.error(new Exception("Servicio no encontrado")))
            .flatMap(servicio -> {
                return this.servicioRepository.delete(servicio);
            });

    }

}
