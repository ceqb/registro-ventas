package com.VentaCliente.repository;

import com.VentaCliente.model.Cliente;
import com.VentaCliente.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
