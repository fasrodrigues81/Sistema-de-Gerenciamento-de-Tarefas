package com.treina.recife.sgp.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.treina.recife.sgp.model.Projeto;
import com.treina.recife.sgp.model.Usuario;

public interface ProjetoService {

    Page<Projeto> getProjetos(
            Pageable pageable);

    Optional<Projeto> getProjetoById(long projectId);

    Projeto createProject(Projeto projeto);

    Projeto updateProjeto(Projeto projeto);

    void deleteProjeto(long projectId);

}
