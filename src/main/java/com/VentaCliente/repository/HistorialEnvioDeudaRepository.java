package com.VentaCliente.repository;

import com.VentaCliente.model.EstadoVenta;
import com.VentaCliente.model.HistorialEnvioDeuda;
import com.VentaCliente.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HistorialEnvioDeudaRepository extends JpaRepository<HistorialEnvioDeuda, Long> {


}
