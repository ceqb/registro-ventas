package com.VentaCliente.repository;

import com.VentaCliente.model.HistorialEnvioPersonalizado;
import com.VentaCliente.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HistorialEnvioPersonalizadoRepository extends JpaRepository<HistorialEnvioPersonalizado, Long> {

    Optional<HistorialEnvioPersonalizado> findTopByVentaOrderByFechaEnvioDesc(Venta venta);

}
