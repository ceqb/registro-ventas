package com.VentaCliente.repository;

import com.VentaCliente.login.Usuario;
import com.VentaCliente.model.Cliente;
import com.VentaCliente.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findByNombre(String cliente);
}
