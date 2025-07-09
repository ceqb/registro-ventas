package com.VentaCliente.login;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class TestPasswordHash {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode("carlos123");
        System.out.println("estas es la calve encriptada: " + encodedPassword);
    }
}
