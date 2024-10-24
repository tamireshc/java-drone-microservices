package com.example.ms_gerenciador_.cadastros.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nome;
    @Column(nullable = false)
    private String sobrenome;
    @Column(nullable = false, unique = true, columnDefinition = "VARCHAR(14) CHECK (cpf ~ '^([0-9]{3}\\.?[0-9]{3}\\.?[0-9]{3}\\-?[0-9]{2}|[0-9]{11})$')")
    private String cpf;
    @Column(nullable = false, unique = true, columnDefinition = "VARCHAR(255) CHECK (email LIKE '%_@_%._%')")
    private String email;
    @Column(nullable = false)
    private String telefone;
    @Column(nullable = false)
    private String senha;

    @OneToMany(mappedBy = "usuario")
    @JsonManagedReference
    private List<Endereco> enderecos;
}
