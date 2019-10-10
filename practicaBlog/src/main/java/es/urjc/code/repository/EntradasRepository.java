package es.urjc.code.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import es.urjc.code.model.Entradas;

public interface EntradasRepository extends JpaRepository<Entradas, Long> {
	List<Entradas> findByNick(String nick);
}
