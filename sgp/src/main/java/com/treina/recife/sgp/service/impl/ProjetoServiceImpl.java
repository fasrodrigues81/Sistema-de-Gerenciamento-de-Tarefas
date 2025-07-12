package com.treina.recife.sgp.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.treina.recife.sgp.model.Projeto;
import com.treina.recife.sgp.model.Usuario;
import com.treina.recife.sgp.repository.ProjetoRepository;
import com.treina.recife.sgp.repository.UsuarioRepository;
import com.treina.recife.sgp.service.ProjetoService;

@Service
public class ProjetoServiceImpl implements ProjetoService {

    @Autowired
    ProjetoRepository projetoRepository;

    @Override
    public Page<Projeto> getProjetos(Pageable pageable) {
        return projetoRepository.findAll(pageable);
    }

    @Override
    public Optional<Projeto> getProjetoById(long projectId) {
        return projetoRepository.findById(projectId);
    }

    @Override
    public Projeto creaTeProject(Projeto projeto) {
        return projetoRepository.save(projeto);
    }

    @Override
    public Projeto updateProjeto(Projeto projeto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateProjeto'");
    }

    @Override
    public void deleteProjeto(long projectId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteProjeto'");
    }

}
