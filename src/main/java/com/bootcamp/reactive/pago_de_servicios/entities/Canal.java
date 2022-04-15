package com.bootcamp.reactive.pago_de_servicios.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Document(value="canales")
public class Canal {
    @Id
    private String id;
    private String codigo;
    private String nombre;
}
