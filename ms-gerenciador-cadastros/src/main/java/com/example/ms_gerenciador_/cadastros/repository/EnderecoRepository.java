package com.example.ms_gerenciador_.cadastros.repository;

import com.example.ms_gerenciador_.cadastros.model.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
    public List<Endereco> findByUsuarioId(Long id);
}
