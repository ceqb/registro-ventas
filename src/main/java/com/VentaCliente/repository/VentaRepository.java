package com.VentaCliente.repository;


import com.VentaCliente.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VentaRepository extends JpaRepository<Venta, Long>{
}
