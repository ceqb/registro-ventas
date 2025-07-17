package com.VentaCliente.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@ConfigurationProperties(prefix = "correo")
@Data
@Getter
@Setter
public class CorreoProperties {
    private String admin;
    private String from;
}
