package com.example.ms_gerenciador_.cadastros.service;

import com.example.ms_gerenciador_.cadastros.dto.EnderecoRequestDTO;
import com.example.ms_gerenciador_.cadastros.dto.UsuarioResponseEnderecoDTO;
import com.example.ms_gerenciador_.cadastros.exception.UsuarioNaoExistenteException;
import com.example.ms_gerenciador_.cadastros.model.Endereco;
import com.example.ms_gerenciador_.cadastros.model.Usuario;
import com.example.ms_gerenciador_.cadastros.repository.EnderecoRepository;
import com.example.ms_gerenciador_.cadastros.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return enderecoRepository.findById(id).orElse(null);
    }

}
