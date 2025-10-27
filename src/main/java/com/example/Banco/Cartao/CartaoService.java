package com.example.Banco.Cartao;

import com.example.Banco.ContaCorrente.ContaCorrente;
import com.example.Banco.ContaCorrente.ContaCorrenteService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CartaoService {

    private final CartaoRepository repo;
    private final ContaCorrenteService contaService;

    public CartaoService(CartaoRepository repo, ContaCorrenteService contaService) {
        this.repo = repo;
        this.contaService = contaService;
    }

    @Transactional
    public Cartao emitirCartao(Cartao cartao) {
        if (cartao.getNumeroDaConta() == null || cartao.getNumeroDaConta().isBlank())
            throw new RuntimeException("Número da conta inválido.");

        ContaCorrente conta = contaService.getConta(cartao.getNumeroDaConta());
        if (conta == null) throw new RuntimeException("Conta não encontrada.");

        if (repo.existsByNumeroCartao(cartao.getNumeroCartao()))
            throw new RuntimeException("Já existe cartão com esse número.");

        cartao.setContaCorrente(conta);
        cartao.setNumeroDaConta(conta.getNumero());
        Cartao salvo = repo.save(cartao);
        contaService.adicionarCartao(conta, salvo);
        return salvo;
    }

    @Transactional(readOnly = true)
    public List<Cartao> listarCartoes() {
        return repo.findAll();
    }

    @Transactional(readOnly = true)
    public Cartao buscarPorNumero(String numero) {
        return repo.findByNumeroCartao(numero).orElse(null);
    }

    @Transactional
    public boolean cancelar(String numero) {
        var opt = repo.findByNumeroCartao(numero);
        if (opt.isEmpty()) return false;
        Cartao c = opt.get();
        c.cancelaCartao();
        repo.save(c);
        return true;
    }

    @Transactional(readOnly = true)
    public boolean estaAtivo(String numero) {
        var opt = repo.findByNumeroCartao(numero);
        if (opt.isEmpty()) return false;
        Cartao c = opt.get();
        return !"CANCELADO".equals(c.getStatus()) && !c.isExpired();
    }

    @Transactional
    public void removerPorNumero(String numero) {
        repo.deleteByNumeroCartao(numero);
    }
}
