package br.com.uds.udspizzaria.infrastructure.persistence.hibernate.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.uds.udspizzaria.domain.model.pedido.Pedido;

public interface PedidoRepositoryInterface extends JpaRepository<Pedido, Long>{

}
