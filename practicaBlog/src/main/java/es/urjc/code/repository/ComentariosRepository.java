package es.urjc.code.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import es.urjc.code.model.Comentarios;

public interface ComentariosRepository extends JpaRepository<Comentarios, Long> {

}
