package com.VentaCliente.repository;


import com.VentaCliente.model.EstadoVenta;
import com.VentaCliente.model.Venta;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VentaRepository extends JpaRepository<Venta, Long>{

    List<Venta> findByEstado(EstadoVenta estado);
}
