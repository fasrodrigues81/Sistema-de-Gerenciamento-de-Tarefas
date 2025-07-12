package com.treina.recife.sgp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.treina.recife.sgp.model.Projeto;
import com.treina.recife.sgp.model.Usuario;

@Repository
public interface ProjetoRepository extends JpaRepository<Projeto, Long> {

    List<Projeto> findByResponsavelUserId(long userId);

    Optional<Usuario> findResponsavelByProjectId(long projectId);
}
