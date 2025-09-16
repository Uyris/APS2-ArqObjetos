package com.example.Banco.Cartao;

import com.example.Banco.ContaCorrente.ContaCorrenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CartaoService {

    @Autowired
    private ContaCorrenteService contaCorrenteService;

    private HashMap<String, Cartao> cartoes = new HashMap<>();

    public Cartao emitirCartao(Cartao cartao){

        if (cartao.getNumeroContaCorrente() == null) {
            throw new RuntimeException("Esse número é inválido.");
        }

        contaCorrenteService.adicionarCartao(contaCorrenteService.getConta(cartao.getNumeroContaCorrente()), cartao);

        cartoes.put(cartao.getNumeroCartao(), cartao);
        return cartao;
    }

    public Collection<Cartao> listarCartoes() {
        return cartoes.values();
    }

    public Cartao buscarPorNumero(String numero){
        return cartoes.get(numero);
    }

    public boolean cancelar(String numero){
        Cartao cartao = cartoes.get(numero);
        if (cartao != null) {
            cartao.cancelaCartao();
            return true;
        }
        return false;
    }

    public boolean estaAtivo(String numero){
        Cartao cartao = cartoes.get(numero);
        return cartao != null && !"CANCELADO".equals(cartao.getStatus()) && !cartao.isExpired();
    }
}
