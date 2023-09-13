package com.utn.tp2.repositorios;

import com.utn.tp2.entidades.Factura;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacturaRepository  extends JpaRepository<Factura, Long> {
}
