package com.utn.tp2.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Pedido implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    private String estado;
    private Date fecha;
    private String tipoEnvio;
    private double total;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.EAGER)
    @JoinColumn(nullable = true)
    private Factura factura;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.EAGER)
    @Builder.Default
    private List<DetallePedido> detallePedidos = new ArrayList<>();
    public void agregarDetallePedido(DetallePedido detallepedido){
        detallePedidos.add(detallepedido);
    }

}
