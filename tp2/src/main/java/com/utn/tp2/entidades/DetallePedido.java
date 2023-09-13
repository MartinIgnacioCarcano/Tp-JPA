package com.utn.tp2.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DetallePedido implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    private int cantidad;
    private double subtotal;

    @ManyToOne
    private Producto producto;
}
