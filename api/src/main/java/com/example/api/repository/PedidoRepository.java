package com.example.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.api.model.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}