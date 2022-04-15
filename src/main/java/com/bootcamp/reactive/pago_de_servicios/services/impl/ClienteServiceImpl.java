package com.bootcamp.reactive.pago_de_servicios.services.impl;

import com.bootcamp.reactive.pago_de_servicios.entities.Cliente;
import com.bootcamp.reactive.pago_de_servicios.repositories.ClienteRepository;
import com.bootcamp.reactive.pago_de_servicios.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public Flux<Cliente> findAll() {
        return this.clienteRepository.findAll();
    }

    @Override
    public Mono<Cliente> findById(String id) {
        return this.clienteRepository.findById(id);
    }

    @Override
    public Mono<Cliente> save(Cliente cliente) {
        return this.clienteRepository.save(cliente);
    }

    @Override
    public Mono<Void> delete(String id) {

        return this.clienteRepository.findById(id)
            .switchIfEmpty(Mono.error(new Exception("Cliente no encontrado")))
            .flatMap(cliente -> {
                return this.clienteRepository.delete(cliente);
            });

    }

}
