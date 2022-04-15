package com.bootcamp.reactive.pago_de_servicios.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Document(value="pagos")
public class Pago {
    @Id
    private String id;
    private Date fechaHora;
    private BigDecimal monto;
    private String suministroId;
}
