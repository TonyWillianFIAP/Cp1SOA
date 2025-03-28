package com.example.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.api.model.Pedido;
import com.example.api.repository.PedidoRepository;

import jakarta.validation.Valid;

@Service
public class PedidoService {
    
    @Autowired
    private PedidoRepository pedidoRepository;

    // Retorna todos os pedidos cadastrados no banco de dados
    public List<Pedido> obterTudo() {
        return pedidoRepository.findAll();
    }

    // Busca um pedido pelo seu ID
    public Optional<Pedido> localizarId(Long id) {
        return pedidoRepository.findById(id);
    }

    // Cadastro novo pedido, desde que o valor total seja igual ou maior que zero
    public Pedido registrar(Pedido pedido) {
    	if (pedido.getValorTotal() < 0) {
            throw new IllegalArgumentException("Total de pedidos deve ser 0 ou maior");
        }
        return pedidoRepository.save(pedido);
    }
    
    // Atualiza um pedido existente
    public Pedido atualizarPedido(Long id, @Valid Pedido pedidoAtualizado) {
        return pedidoRepository.findById(id).map(pedido -> {
            // Verifica se o nome do cliente foi informado corretamente
            if (pedidoAtualizado.getClienteNome() == null || pedidoAtualizado.getClienteNome().isBlank()) {
                throw new IllegalArgumentException("Não esqueça de colocar o name cliente");
            }
            // Verifica se o valor total é válido
            if (pedidoAtualizado.getValorTotal() < 0) {
                throw new IllegalArgumentException("Total de pedidos deve ser 0 ou maior");
            }
            // Atualiza os campos do pedido
            pedido.setClienteNome(pedidoAtualizado.getClienteNome());
            pedido.setValorTotal(pedidoAtualizado.getValorTotal());
            // Salva e retorna o pedido atualizado
            return pedidoRepository.save(pedido);
        }).orElseThrow(() -> new RuntimeException("Não foi possivel encontrar o seu pedido"));
    }
    
    // Deleta um pedido
    public void remover(Long id) {
        pedidoRepository.deleteById(id);
    }
}
