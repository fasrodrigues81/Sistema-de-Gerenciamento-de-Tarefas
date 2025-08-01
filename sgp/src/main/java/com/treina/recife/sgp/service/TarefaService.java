package com.treina.recife.sgp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.treina.recife.sgp.model.Projeto;
import com.treina.recife.sgp.model.Tarefa;

public interface TarefaService {

    Page<Tarefa> getTarefas(Pageable pageable);

    Optional<Tarefa> getTarefaById(long tarefaId);

    Tarefa creaTeTarefa(Tarefa tarefa);

    void deleteTarefaById(long tarefaId);

    Tarefa updateTarefa(Tarefa tarefa);

    List<Tarefa> findByProjetoProjectId(Projeto projectId);

}
