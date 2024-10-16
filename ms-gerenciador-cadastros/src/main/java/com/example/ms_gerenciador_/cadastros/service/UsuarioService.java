package com.example.ms_gerenciador_.cadastros.service;

import com.example.ms_gerenciador_.cadastros.dto.UsuarioResponseDTO;
import com.example.ms_gerenciador_.cadastros.exception.UsuarioExistenteException;
import com.example.ms_gerenciador_.cadastros.model.Usuario;
import com.example.ms_gerenciador_.cadastros.repository.UsuarioRepository;
import com.example.ms_gerenciador_.cadastros.utils.HashUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public UsuarioResponseDTO cadastrarUsuario(Usuario usuario) throws NoSuchAlgorithmException {

        Usuario usuarioEmailExistente = usuarioRepository.getByEmail(usuario.getEmail());
        Usuario usuarioCPFExistente = usuarioRepository.getByCpf(usuario.getCpf());

        if (usuarioEmailExistente != null || usuarioCPFExistente != null) {
            throw new UsuarioExistenteException("CPF ou Email já cadastrado");
        }

        String hashSenha = HashUtils.gerarHashSenha(usuario.getSenha(), "SHA-256");
        usuario.setSenha(hashSenha);

        Usuario usuarioResponse = usuarioRepository.save(usuario);
        return UsuarioResponseDTO.builder()
                .id(usuarioResponse.getId())
                .nome(usuarioResponse.getNome())
                .email(usuarioResponse.getEmail())
                .build();
    }

    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }
}
