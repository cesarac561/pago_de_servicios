package com.bootcamp.reactive.pago_de_servicios.services.impl;

import com.bootcamp.reactive.pago_de_servicios.entities.InputPago;
import com.bootcamp.reactive.pago_de_servicios.entities.Pago;
import com.bootcamp.reactive.pago_de_servicios.exceptions.ServicioBaseException;
import com.bootcamp.reactive.pago_de_servicios.repositories.PagoRepository;
import com.bootcamp.reactive.pago_de_servicios.repositories.ServicioRepository;
import com.bootcamp.reactive.pago_de_servicios.repositories.SuministroRepository;
import com.bootcamp.reactive.pago_de_servicios.services.PagoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.*;
import java.util.Date;

import static org.springframework.web.reactive.function.server.ServerResponse.badRequest;

@Slf4j
@Service
public class PagoServiceImpl implements PagoService {

    @Autowired
    private PagoRepository pagoRepository;

    @Autowired
    private ServicioRepository servicioRepository;

    @Autowired
    private SuministroRepository suministroRepository;
    @Override
    public Flux<Pago> findAll() {
        return this.pagoRepository.findAll();
    }

    @Override
    public Mono<Pago> findById(String id) {
        return this.pagoRepository.findById(id);
    }

    @Override
    public Mono<Pago> save(Pago pago) {
        return this.pagoRepository.save(pago);
    }

    @Override
    public Mono<Pago> registrarPago(InputPago inputPago) {

        log.info("CALL registrarPago");

        if(validarInputPago(inputPago) == 1)
            return Mono.error(new ServicioBaseException(HttpStatus.BAD_REQUEST,"Formato Código Servicio inválido"));

        if(validarInputPago(inputPago) == 2)
            return Mono.error(new ServicioBaseException(HttpStatus.BAD_REQUEST,"Formato Número Suministro inválido"));

        return servicioRepository
                .findByCodigo(inputPago.getCodigoServicio())
                .onErrorResume(e -> Mono.empty())
                .switchIfEmpty(Mono.error(new ServicioBaseException(HttpStatus.NOT_FOUND,"Servicio no encontrado")))
                .flatMap(s -> {
                    return this.suministroRepository.findByNumeroAndServicioId(inputPago.getNumeroSuministro(), s.getId());
                })
                .flatMap(suministro -> {
                    Pago pago = new Pago();
                    Pago pagoR = new Pago();

                    pago.setFechaHora(new Date());
                    pago.setMonto(inputPago.getMontoPago());
                    pago.setSuministroId(suministro.getId());

                    return this.pagoRepository.save(pago);
//                    return this.pagoRepository.save(null);
//                    pagoR = ((int)(Math.random()*10)>5) ? null : pago;
//                    return this.pagoRepository.save(pagoR);
                });

    }

    @Override
    public Mono<Void> delete(String id) {

        return this.pagoRepository.findById(id)
            .switchIfEmpty(Mono.error(new Exception("Pago no encontrado")))
            .flatMap(pago -> {
                return this.pagoRepository.delete(pago);
            });

    }

    public Integer validarInputPago(InputPago inputPago){
        if(inputPago.getCodigoServicio()==null || inputPago.getCodigoServicio().isEmpty() || inputPago.getCodigoServicio().isBlank() || inputPago.getCodigoServicio().length() != 2)
            return 1;
        if(inputPago.getNumeroSuministro()==null || inputPago.getNumeroSuministro().isEmpty() || inputPago.getNumeroSuministro().isBlank() || inputPago.getNumeroSuministro().length() != 7)
            return 2;
        return 0;
    }

}
