package com.example.ms_gerenciador_.cadastros.service;

import com.example.ms_gerenciador_.cadastros.dto.EnderecoRequestDTO;
import com.example.ms_gerenciador_.cadastros.exception.EnderecoNaoExistenteException;
import com.example.ms_gerenciador_.cadastros.exception.UsuarioNaoExistenteException;
import com.example.ms_gerenciador_.cadastros.model.Endereco;
import com.example.ms_gerenciador_.cadastros.model.Usuario;
import com.example.ms_gerenciador_.cadastros.repository.EnderecoRepository;
import com.example.ms_gerenciador_.cadastros.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository enderecoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private LatitudeLongitudeService latitudeLongitudeService;

    @Transactional
    public Endereco cadastrarEndereco(EnderecoRequestDTO enderecoRequestDTO) {
        Optional<Usuario> usuario = usuarioRepository.findById(Long.parseLong(enderecoRequestDTO.getUsuarioId()));
        if (usuario.isEmpty()) {
            throw new UsuarioNaoExistenteException("Usuário não encontrado");
        }
        Endereco novoEndereco = new Endereco();
        novoEndereco.setCep(enderecoRequestDTO.getCep());
        novoEndereco.setLogradouro(enderecoRequestDTO.getLogradouro());
        novoEndereco.setNumero(enderecoRequestDTO.getNumero());
        novoEndereco.setComplemento(enderecoRequestDTO.getComplemento());
        novoEndereco.setBairro(enderecoRequestDTO.getBairro());
        novoEndereco.setCidade(enderecoRequestDTO.getCidade());
        novoEndereco.setEstado(enderecoRequestDTO.getEstado());
        novoEndereco.setUsuario(usuario.get());

        try {
            latitudeLongitudeService.sendEndereco(novoEndereco, "endereco-pendente.ex");
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return enderecoRepository.save(novoEndereco);
    }

    public Endereco buscarEnderecoPorId(Long id) {
        Endereco endereco = enderecoRepository.findById(id).orElse(null);
        if (endereco == null) {
            throw new EnderecoNaoExistenteException("Endereço não encontrado");
        }
        return endereco;
    }

    public List<Endereco> buscarEnderecosPorUsuarioId(Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        if (usuario.isEmpty()) {
            throw new UsuarioNaoExistenteException("Usuário não encontrado");
        }
        return enderecoRepository.findByUsuarioId(id);
    }

    public List<Endereco> listarEnderecos() {
        return enderecoRepository.findAll();
    }

    public void deletarEndereco(Long id) {
        Endereco endereco = buscarEnderecoPorId(id);
        if (endereco == null) {
            throw new EnderecoNaoExistenteException("Endereço não encontrado");
        }
        enderecoRepository.deleteById(id);
    }
}
