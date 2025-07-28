package com.treina.recife.sgp.controller;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.query.SortDirection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.treina.recife.sgp.constants.StatusProjeto;
import com.treina.recife.sgp.dto.ProjetoDto;
import com.treina.recife.sgp.dto.UsuarioDto;
import com.treina.recife.sgp.model.Projeto;
import com.treina.recife.sgp.model.Usuario;
import com.treina.recife.sgp.service.ProjetoService;
import com.treina.recife.sgp.service.UsuarioService;

@RestController
@RequestMapping("/projetos")
public class ProjetoController {

    @Autowired
    ProjetoService projetoService;

    @Autowired
    UsuarioService usuarioService;

    Logger logger = LogManager.getLogger(ProjetoController.class);

    public ProjetoController(ProjetoService projetoService, UsuarioService usuarioService) {

        this.projetoService = projetoService;
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseEntity<Page<Projeto>> getProjetos(
            @PageableDefault(sort = "projectId ", direction = Sort.Direction.ASC) Pageable pageable) {

        Page<Projeto> projetos = projetoService.getProjetos(pageable);

        if (projetos.isEmpty()) {
            logger.info("Ainda não há projeto cadastrado");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Page.empty());

        } else {
            return ResponseEntity.status(HttpStatus.OK).body(projetos);
        }
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<Object> getProjetoById(@PathVariable(value = "projectId") long projectId) {

        Optional<Projeto> projeto = projetoService.getProjetoById(projectId);

        if (projeto.isEmpty()) {
            logger.error("Projeto não encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Projeto não encontrado");

        } else {
            logger.info(projeto.get().toString());
            return ResponseEntity.status(HttpStatus.OK).body(projeto.get());
        }

    }

    @PostMapping
    public ResponseEntity<Object> createProjeto(@RequestBody ProjetoDto projetoDto) {

        Projeto novoProjeto = new Projeto();
        novoProjeto.setNome(projetoDto.getNome());
        novoProjeto.setDescricao(projetoDto.getDescricao());
        novoProjeto.setDataInicio(projetoDto.getDataInicio());
        novoProjeto.setStatus(StatusProjeto.ATIVO);
        novoProjeto.setDataConclusao(projetoDto.getDataConclusao());

        Projeto projeto = projetoService.createProject(novoProjeto);
        logger.info("projeto {} criado com sucesso", novoProjeto.getNome());
        return ResponseEntity.status(HttpStatus.CREATED).body(projeto);

    }

    @PutMapping
    public ResponseEntity<Object> updateProjeto(@PathVariable(value = "projectId") long projectId,
            @RequestBody ProjetoDto projetoDto) {

        Optional<Projeto> projeto = projetoService.getProjetoById(projectId);

        if (projeto.isEmpty()) {
            logger.error("Projeto Não encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Projeto Não encontrado");
        } else {
            Projeto projetoAtualizado = projeto.get();
            projetoAtualizado.setNome(projetoDto.getNome());
            projetoAtualizado.setDescricao(projetoDto.getDescricao());
            projetoAtualizado.setDataInicio(projetoDto.getDataInicio());
            projetoAtualizado.setDataConclusao(projetoDto.getDataConclusao());
            projetoAtualizado.setStatus(projetoDto.getStatus());

            projetoService.updateProjeto(projetoAtualizado);
            logger.info("projeto de ID {} atualizado com sucesso", projetoAtualizado.getProjectId());
            return ResponseEntity.status(HttpStatus.OK).body(projetoAtualizado);
        }
    }

    @DeleteMapping("/projectId")
    public ResponseEntity<Object> deleteProjeto(@PathVariable(value = "projectId") long projectId) {

        Optional<Projeto> projeto = projetoService.getProjetoById(projectId);

        if (projeto.isEmpty()) {
            logger.warn("Projeto não encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Projeto não encontrado");
        } else {
            projetoService.deleteProjeto(projectId);
            logger.info("Projeto {} deletado com sucesso", projectId);
            return ResponseEntity.status(HttpStatus.OK).body("Projeto deletado com sucesso");
        }

    }

    @PatchMapping("/{projectId}/responsavel")
    public ResponseEntity<Object> atribuirResponsavel(@PathVariable(value = "projectId") long projectId,
            @RequestBody() UsuarioDto usuarioDto) {

        Optional<Projeto> projetoOptional = projetoService.getProjetoById(projectId);

        if (projetoOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Projeto não encontrado");

        }

        Optional<Usuario> usuarOptional = usuarioService.getusuarioById(usuarioDto.getUserId());

        if (usuarOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario não encontrado");

        }

        Projeto projeto = projetoOptional.get();
        Usuario responsavel = usuarOptional.get();

        projeto.setResponsavel(responsavel);

        Projeto projetoAtualizado = projetoService.updateProjeto(projeto);
        logger.info("Responsavel {} designado ao projeto {} com sucesso", responsavel, projeto.getNome());
        return ResponseEntity.status(HttpStatus.OK).body(projetoAtualizado);

    }
}