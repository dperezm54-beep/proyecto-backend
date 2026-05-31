package com.demo.persistencia.demopersistencia.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.persistencia.demopersistencia.dto.ClienteDto;
import com.demo.persistencia.demopersistencia.entidades.Cliente;
import com.demo.persistencia.demopersistencia.services.ClienteServicio;
import org.springframework.web.bind.annotation.CrossOrigin;


@RestController
@RequestMapping("/api/clientes")

public class ClienteController {
    
    @Autowired
    private ClienteServicio servicioCliente;

    @CrossOrigin(origins = "*") 
    @GetMapping("/listarClientes")
    public List<Cliente> consultarClientes() {
        return servicioCliente.consultarClientes();
    }


    @PostMapping("/registrarCliente")
    public Cliente registrarCliente(@RequestBody ClienteDto clienteJson) {

        Cliente cliente = new Cliente();
        
        cliente.setNombre(clienteJson.getNombre());
        cliente.setDireccion(clienteJson.getDireccion());
        cliente.setNit(clienteJson.getNit());
       

        System.out.println("valor a persistir " + cliente.toString());

       
        return servicioCliente.registClientes(cliente);

    }
}
