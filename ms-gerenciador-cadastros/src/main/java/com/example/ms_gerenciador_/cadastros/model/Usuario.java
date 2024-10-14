package com.example.ms_gerenciador_.cadastros.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    @NonNull
    private String nome;
    @NonNull
    private String sobrenome;
    @Column(nullable = false, unique = true, length = 11)
    private String cpf;
    @Column(nullable = false, unique = true)
    private String email;
    @NonNull
    private String telefone;
    @NonNull
    private String senha;

    @OneToMany(mappedBy = "usuario")
    @JsonBackReference
    private List<Endereco> enderecos;

}
