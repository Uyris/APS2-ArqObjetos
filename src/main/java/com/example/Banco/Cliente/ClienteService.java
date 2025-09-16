package com.example.Banco.Cliente;

import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;

@Service
public class ClienteService {

    private HashMap<String, Cliente> listaDeClientes = new HashMap<>();

    public Cliente getCliente(String cpf){
        return listaDeClientes.get(cpf);
    }

    public Collection<Cliente> getClientes() {
        return listaDeClientes.values();
    }

    public Cliente salvarCliente(Cliente cliente){
        listaDeClientes.put(cliente.getCpf(), cliente);
        return cliente;
    }

    public Cliente atualizarCliente(String cpf, Cliente cliente){
        listaDeClientes.put(cpf, cliente);
        return cliente;
    }

    public void excluirCliente(String cpf){
        listaDeClientes.remove(cpf);
    }
}
