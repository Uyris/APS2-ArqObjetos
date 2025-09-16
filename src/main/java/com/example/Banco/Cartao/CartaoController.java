package com.example.Banco.Cartao;

import com.example.Banco.Autenticacao.AutenticacaoService;
import com.example.Banco.ContaCorrente.ContaCorrenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/cartoes")
public class CartaoController {

    @Autowired
    private CartaoService cartaoService;

    @Autowired
    private ContaCorrenteService contaCorrenteService;

    @Autowired
    private AutenticacaoService autenticacaoService;

    // listar todos (público)
    @GetMapping
    public Collection<Cartao> listarTodos() {
        return cartaoService.listarCartoes();
    }

    // buscar cartão (público)
    @GetMapping("/{numero}")
    public Cartao buscarPorNumero(@PathVariable String numero) {
        return cartaoService.buscarPorNumero(numero);
    }

    // emitir cartão (precisa token)
    @PostMapping
    public Cartao emitir(@RequestBody Cartao cartao,
                         @RequestHeader("Authorization") String token) {
        autenticacaoService.validarToken(token);

        if (cartao.getContaCorrente() != null) {
            contaCorrenteService.adicionarCartao(contaCorrenteService.getConta(cartao.getContaCorrente().getNumero()), cartao);
            cartao.setContaCorrente(contaCorrenteService.getConta(cartao.getContaCorrente().getNumero()));
        }

        return cartaoService.emitirCartao(cartao);
    }

    // cancelar cartão (precisa token)
    @DeleteMapping("/{numero}")
    public String cancelar(@PathVariable String numero,
                           @RequestHeader("Authorization") String token) {
        autenticacaoService.validarToken(token);
        boolean ok = cartaoService.cancelar(numero);
        return ok ? "Cartão cancelado com sucesso!" : "Cartão não encontrado.";
    }

    // verificar status (público)
    @GetMapping("/{numero}/status")
    public String verificarStatus(@PathVariable String numero) {
        boolean ativo = cartaoService.estaAtivo(numero);
        return ativo ? "Cartão ativo" : "Cartão inativo ou expirado";
    }
}
