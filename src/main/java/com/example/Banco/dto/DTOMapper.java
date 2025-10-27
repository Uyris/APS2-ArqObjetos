package com.example.Banco.dto;

import java.util.List;
import java.util.stream.Collectors;

public class DTOMapper {

    public static List<CartaoDTO> cartoesToDTO(List<com.example.Banco.Cartao.Cartao> list) {
        if (list == null) return null;
        return list.stream().map(CartaoDTO::fromEntity).collect(Collectors.toList());
    }

    public static List<MovimentacaoDTO> movimentacoesToDTO(List<com.example.Banco.Movimentacao.Movimentacao> list) {
        if (list == null) return null;
        return list.stream().map(MovimentacaoDTO::fromEntity).collect(Collectors.toList());
    }

    public static List<ClienteDTO> clientesToDTO(List<com.example.Banco.Cliente.Cliente> list) {
        if (list == null) return null;
        return list.stream().map(ClienteDTO::fromEntity).collect(Collectors.toList());
    }

    public static List<ContaCorrenteDTO> contasToDTO(List<com.example.Banco.ContaCorrente.ContaCorrente> list) {
        if (list == null) return null;
        return list.stream().map(ContaCorrenteDTO::fromEntity).collect(Collectors.toList());
    }
}
