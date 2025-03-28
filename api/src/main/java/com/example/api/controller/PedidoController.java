package com.example.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.model.Pedido;
import com.example.api.service.PedidoService;

@RestController 
@RequestMapping("/v1/pedidos") //URL paraos endpoints
public class PedidoController {

    @Autowired
    private PedidoService pedidoService; 

    // Endpoint GET para buscar todos os pedidos cadastrados
    @GetMapping
    public List<Pedido> obterTudo() {
        return pedidoService.obterTudo();
    }

    // Endpoint GET para buscar um pedido específico pelo ID
    @GetMapping("/{id}")
    public Optional<Pedido> localizarId(@PathVariable Long id) {
        return pedidoService.localizarId(id);
    }

    // Endpoint POST para adicionar um novo pedido
    @PostMapping
    public Pedido adicionar(@RequestBody Pedido pedido) {
        return pedidoService.registrar(pedido);
    }

    @DeleteMapping("/{id}") // Endpoint DELETE para remover um pedido com base no ID
    public void remover(@PathVariable Long id) {
        pedidoService.remover(id);
    }

    // Endpoint PUT para atualizar um pedido existente
    @PutMapping("/{id}")
    public ResponseEntity<Pedido> modificar(@PathVariable Long id, @RequestBody Pedido pedidoAtualizado) {
        Optional<Pedido> pedidoExistente = pedidoService.localizarId(id);

        // Verifica se o pedido existe
        if (pedidoExistente.isPresent()) {
            Pedido pedido = pedidoExistente.get();

            // Atualiza os dados do pedido com os valores recebidos
            pedido.setClienteNome(pedidoAtualizado.getClienteNome());
            pedido.setDataPedido(pedidoAtualizado.getDataPedido());
            pedido.setValorTotal(pedidoAtualizado.getValorTotal());

            Pedido pedidoSalvo = pedidoService.registrar(pedido);

            // Retorna status 200 OK com o pedido atualizado
            return ResponseEntity.ok(pedidoSalvo);
        } else {
            // Retorna 404 caso o pedido não seja encontrado
            return ResponseEntity.notFound().build();
        }
    }
}
