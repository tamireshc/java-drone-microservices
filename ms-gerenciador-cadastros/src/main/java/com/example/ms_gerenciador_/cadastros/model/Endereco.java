package com.example.ms_gerenciador_.cadastros.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Endereco {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String logradouro;
    @Column(nullable = false)
    private int numero;
    private String complemento;
    private String bairro;
    @Column(nullable = false)
    private String cidade;
    @Column(nullable = false)
    private String estado;
    @Column(nullable = false, columnDefinition = "VARCHAR(9) CHECK (cep ~ ^\\d{5}-\\d{3}$')" )
    private String cep;
    private String latitude;
    private String longitude;

    @ManyToOne
    @JoinColumn(name = "usuarioId")
    @JsonBackReference
    private Usuario usuario;

}
