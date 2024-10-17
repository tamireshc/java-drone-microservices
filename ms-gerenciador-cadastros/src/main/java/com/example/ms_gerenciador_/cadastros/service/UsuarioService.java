package com.example.ms_gerenciador_.cadastros.service;

import com.example.ms_gerenciador_.cadastros.dto.UsuarioResponseDTO;
import com.example.ms_gerenciador_.cadastros.dto.UsuarioResponseEnderecoDTO;
import com.example.ms_gerenciador_.cadastros.exception.EdicaoNaoPermitidaException;
import com.example.ms_gerenciador_.cadastros.exception.UsuarioExistenteException;
import com.example.ms_gerenciador_.cadastros.exception.UsuarioNaoExistenteException;
import com.example.ms_gerenciador_.cadastros.model.Usuario;
import com.example.ms_gerenciador_.cadastros.repository.UsuarioRepository;
import com.example.ms_gerenciador_.cadastros.utils.HashUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
                .sobrenome(usuarioResponse.getSobrenome())
                .telefone(usuarioResponse.getTelefone())
                .email(usuarioResponse.getEmail())
                .build();
    }

    public List<UsuarioResponseEnderecoDTO> listarUsuarios() {
        List<Usuario> usuarios = usuarioRepository.findAll();

        if (usuarios.isEmpty()) {
            throw new UsuarioNaoExistenteException("Nenhum usuário cadastrado");
        }
        List<UsuarioResponseEnderecoDTO> usuariosResponseEnderecoDTO = new ArrayList<>();

        for (Usuario usuario : usuarios) {
            usuariosResponseEnderecoDTO.add(
                    new UsuarioResponseEnderecoDTO().converterUsuarioparaUsuarioResponseDTO(Optional.ofNullable(usuario)));
        }
        return usuariosResponseEnderecoDTO;
    }

    public UsuarioResponseEnderecoDTO buscarUsuarioPorCPF(String cpf) {
        Usuario usuario = usuarioRepository.getByCpf(cpf);
        if (usuario == null) {
            throw new UsuarioNaoExistenteException("Usuário não encontrado");
        }
        return new UsuarioResponseEnderecoDTO().converterUsuarioparaUsuarioResponseDTO(Optional.of(usuario));
    }

    public UsuarioResponseEnderecoDTO buscarUsuarioPorId(String id) {

        Optional<Usuario> usuario = usuarioRepository.findById(Long.parseLong(id));
        if (usuario.isEmpty()) {
            throw new UsuarioNaoExistenteException("Usuário não encontrado");
        }
        return new UsuarioResponseEnderecoDTO().converterUsuarioparaUsuarioResponseDTO(usuario);
    }

    @Transactional
    public UsuarioResponseDTO editarUsuario(String id, Usuario usuario) throws NoSuchAlgorithmException {
        Optional<Usuario> usuarioExistente = usuarioRepository.findById(Long.parseLong(id));

        if (usuarioExistente.isEmpty()) {
            throw new UsuarioNaoExistenteException("Usuário não encontrado");
        } else {
            if (!usuarioExistente.get().getCpf().equals(usuario.getCpf())) {
                throw new EdicaoNaoPermitidaException("Edição não permitida");
            }
            if (usuario.getNome() != null) usuarioExistente.get().setNome(usuario.getNome());
            if (usuario.getSobrenome() != null) usuarioExistente.get().setSobrenome(usuario.getSobrenome());
            if (usuario.getEmail() != null) usuarioExistente.get().setEmail(usuario.getEmail());
            if (usuario.getTelefone() != null) usuarioExistente.get().setTelefone(usuario.getTelefone());
            if (usuario.getSenha() != null)
                usuarioExistente.get().setSenha(HashUtils.gerarHashSenha(usuario.getSenha(), "SHA-256"));

            Usuario usuarioResponse = usuarioRepository.save((Usuario) usuarioExistente.get());

            return new UsuarioResponseDTO().converterUsuarioparaUsuarioResponseDTO(usuarioResponse);
        }
    }
}
