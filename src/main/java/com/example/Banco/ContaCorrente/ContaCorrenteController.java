package com.example.Banco.ContaCorrente;

import com.example.Banco.Autenticacao.AutenticacaoService;
import com.example.Banco.Movimentacao.Movimentacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/contas")
public class ContaCorrenteController {

    @Autowired
    private ContaCorrenteService contaService;

    @Autowired
    private AutenticacaoService autenticacaoService;

    // listar contas (público)
    @GetMapping
    public Collection<ContaCorrente> listarContas() {
        return contaService.getContas();
    }

    // buscar conta (público)
    @GetMapping("/{numero}")
    public ContaCorrente buscarConta(@PathVariable String numero) {
        return contaService.getConta(numero);
    }

    // criar conta (precisa token)
    @PostMapping
    public ContaCorrente criarConta(@RequestBody ContaCorrente conta,
                                    @RequestHeader("Authorization") String token) {
        autenticacaoService.validarToken(token);
        return contaService.salvarConta(conta);
    }

    // sacar (precisa token)
    @PostMapping("/{numero}/saque")
    public String sacar(@PathVariable String numero,
                        @RequestParam Float valor,
                        @RequestHeader("Authorization") String token) {
        autenticacaoService.validarToken(token);
        boolean ok = contaService.sacar(numero, valor);
        return ok ? "Saque realizado com sucesso!" : "Saldo insuficiente ou conta inexistente.";
    }

    // depositar (precisa token)
    @PostMapping("/{numero}/deposito")
    public String depositar(@PathVariable String numero,
                            @RequestParam Float valor,
                            @RequestHeader("Authorization") String token) {
        autenticacaoService.validarToken(token);
        boolean ok = contaService.depositar(numero, valor);
        return ok ? "Depósito realizado com sucesso!" : "Conta inexistente.";
    }

    // listar movimentações (público)
    @GetMapping("/{numero}/movimentacoes")
    public List<Movimentacao> listarMovimentacoes(@PathVariable String numero) {
        return contaService.listarMovimentacoes(numero);
    }
}
