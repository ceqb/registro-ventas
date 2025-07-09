package com.VentaCliente;


import com.VentaCliente.login.Usuario;
import com.VentaCliente.login.UsuarioRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostConstruct
    public void init() {
        // Usuario: carlos
        String usuarioCarlos = "carlos";
        String claveCarlos = "$2a$10$Zb.ghgQ20vP2pDadmewy9eMfzjegG9SPguBiCTQu/SQFJKclFCwmC"; // carlos123

        if (usuarioRepository.findByUsuario(usuarioCarlos).isEmpty()) {
            Usuario nuevoCarlos = new Usuario();
            nuevoCarlos.setUsuario(usuarioCarlos);
            nuevoCarlos.setNombre("Carlos Quiroz");
            nuevoCarlos.setClave(claveCarlos);
            usuarioRepository.save(nuevoCarlos);
            System.out.println("✅ Usuario 'carlos' insertado con clave encriptada.");
        } else {
            System.out.println("ℹ️ Usuario 'carlos' ya existe.");
        }

        // Usuario: gabriela
        String usuarioGabriela = "gabriela";
        String claveGabriela = "$2a$12$1Riy6PRaTfcepS0efhBjK.rfbVd7Y8.mu8.PYFAplq0DRDTEgK3XS"; // gabriela123

        if (usuarioRepository.findByUsuario(usuarioGabriela).isEmpty()) {
            Usuario nuevaGabriela = new Usuario();
            nuevaGabriela.setUsuario(usuarioGabriela);
            nuevaGabriela.setNombre("Gabriela Chilon");
            nuevaGabriela.setClave(claveGabriela);
            usuarioRepository.save(nuevaGabriela);
            System.out.println("✅ Usuario 'gabriela' insertado con clave encriptada.");
        } else {
            System.out.println("ℹ️ Usuario 'gabriela' ya existe.");
        }
    }
}
