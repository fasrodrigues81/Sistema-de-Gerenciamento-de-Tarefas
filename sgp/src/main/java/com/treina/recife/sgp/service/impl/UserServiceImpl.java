package com.treina.recife.sgp.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.treina.recife.sgp.model.Usuario;
import com.treina.recife.sgp.repository.UsuarioRepository;
import com.treina.recife.sgp.service.UsuarioService;

public class UserServiceImpl implements UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    public Page<Usuario> getUsuarios(Pageable pageable) {
        return usuarioRepository.findAll(pageable);
    }

    @Override
    public Optional<Usuario> getusuarioById(long userId) {
        return usuarioRepository.findById(userId);
    }

    @Override
    public Usuario createUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario updateUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Override
    public void deleteUsuario(long userId) {
        usuarioRepository.deleteById(userId);
    }

    @Override
    public boolean isEmailAlreadyTaken(String email) {
        return usuarioRepository.existsByEmail(email);
    }

}
