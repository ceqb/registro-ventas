package com.VentaCliente.service;

import com.VentaCliente.dto.AuditoriaAccesoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class AuditoriaAccesoService {

    @Autowired
    private WebClient.Builder webClientBuilder;
    public void registrarAuditoriaAcceso(String usuario, String ip, String navegador, boolean exitoso, String observacion) {
        AuditoriaAccesoDTO dto = AuditoriaAccesoDTO.builder()
                .usuario(usuario)
                .ip(ip)
                .navegador(navegador)
                .fechaHora(LocalDateTime.now())
                .exitoso(exitoso)
                .observacion(observacion)
                .build();

        webClientBuilder.build()
                .post()
                .uri("http://localhost:9010/api/v1/auditoria-acceso/registrar") // Cambia si es otro puerto
                .bodyValue(dto)
                .retrieve()
                .bodyToMono(Void.class)
                .subscribe(); // asíncrono
    }
}
