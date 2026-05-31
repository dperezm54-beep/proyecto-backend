package com.demo.persistencia.demopersistencia.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.persistencia.demopersistencia.entidades.Cliente;
import com.demo.persistencia.demopersistencia.repositorio.ClienteRepositorio;



@Service

public class ClienteServicio {

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    /**
     * consultar el cliente.
     * 
     * @return
     */
    public List<Cliente> consultarClientes() {
        return (List<Cliente>) clienteRepositorio.findAll();
    }

    /**
     * @param cliente
     * @return el registro del cliente.
     */

    public Cliente registClientes(Cliente cliente) {

        System.out.println("Servicio trae" + cliente.toString());
        return clienteRepositorio.save(cliente);

    }

}
