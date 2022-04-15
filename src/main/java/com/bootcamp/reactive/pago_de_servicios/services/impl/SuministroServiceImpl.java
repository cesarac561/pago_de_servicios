package com.bootcamp.reactive.pago_de_servicios.services.impl;

import com.bootcamp.reactive.pago_de_servicios.entities.Suministro;
import com.bootcamp.reactive.pago_de_servicios.exceptions.ServicioBaseException;
import com.bootcamp.reactive.pago_de_servicios.exceptions.SuministroBaseException;
import com.bootcamp.reactive.pago_de_servicios.repositories.ServicioRepository;
import com.bootcamp.reactive.pago_de_servicios.repositories.SuministroRepository;
import com.bootcamp.reactive.pago_de_servicios.services.SuministroService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Slf4j
@Service
public class SuministroServiceImpl implements SuministroService {

    @Autowired
    private SuministroRepository suministroRepository;

    @Autowired
    private ServicioRepository servicioRepository;

    @Override
    public Flux<Suministro> findAll() {
        return this.suministroRepository.findAll();
    }

    @Override
    public Mono<Suministro> findById(String id) {
        return this.suministroRepository.findById(id);
    }

    @Override
    public Mono<Suministro> findByNumeroAndServicioId(String numero, String servicioId) {
        return this.suministroRepository.findByNumeroAndServicioId(numero, servicioId);
    }

    @Override
    public Mono<Suministro> findByNumeroAndCodigoServicio(String numero, String codServicio) {

        log.info("CALL findByNumeroAndCodigoServicio");

        return servicioRepository
                .findByCodigo(codServicio)
                .onErrorResume(e -> Mono.empty())
                .switchIfEmpty(Mono.error(new ServicioBaseException("Servicio no encontrado")))
                .flatMap(s -> {
                    return this.suministroRepository.findByNumeroAndServicioId(numero, s.getId());
//                    return this.suministroRepository.findByNumeroAndServicioId(numero, s.getId()).delayElement(Duration.ofSeconds(6));
//                    return this.suministroRepository.findByNumeroAndServicioId(numero, s.getId()).delayElement(Duration.ofSeconds((int)(Math.random()*10)));
                });
    }

    @Override
    public Mono<Suministro> save(Suministro suministro) {
        return this.suministroRepository.save(suministro);
    }

    @Override
    public Mono<Void> delete(String id) {

        return this.suministroRepository.findById(id)
            .switchIfEmpty(Mono.error(new SuministroBaseException("Suministro no encontrado")))
            .flatMap(suministro -> {
                return this.suministroRepository.delete(suministro);
            });

    }

}
