package com.VentaCliente;


import com.VentaCliente.login.Usuario;
import com.VentaCliente.login.UsuarioRepository;
import com.VentaCliente.model.Cliente;
import com.VentaCliente.repository.ClienteRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer {

    private final ClienteRepository clienteRepository;

    @PostConstruct
    public void init() {

            crearClienteSiNoExiste("Jesus Salas");
            crearClienteSiNoExiste("JeanPier Paz");
            crearClienteSiNoExiste("Ramon Rengifo");
            crearClienteSiNoExiste("Gladys Castillo");
            crearClienteSiNoExiste("Julian Perez");
            crearClienteSiNoExiste("Nataly Ayala");
        }

        private void crearClienteSiNoExiste(String nombre) {
            if (clienteRepository.findByNombre(nombre).isEmpty()) {
                Cliente cliente = new Cliente();
                cliente.setNombre(nombre);
                clienteRepository.save(cliente);
                System.out.println("✅ Cliente '" + nombre + "' insertado.");
            } else {
                System.out.println("ℹ️ Cliente '" + nombre + "' ya existe.");
            }
        }
}
