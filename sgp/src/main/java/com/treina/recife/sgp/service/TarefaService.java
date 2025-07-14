package com.treina.recife.sgp.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.treina.recife.sgp.model.Projeto;

public interface TarefaService {
    Page<Projeto> getProjetos(Pageable pageable);

    Optional<Projeto> getProjetoById(long tarefaId);

    Projeto creaTeProject(Projeto tarefa);

    Projeto updateProjeto(Projeto tarefa);

    void deleteProjeto(long tarefaId);

}
