package com.example.ms_gerenciador_.cadastros.repository;

import com.example.ms_gerenciador_.cadastros.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    public Usuario getByEmail(String email);
    public Usuario getByCpf(String cpf);
}
