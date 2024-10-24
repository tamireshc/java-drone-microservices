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
    @Column(nullable = false, length = 9)
    private String cep;
    private String latitude;
    private String longitude;
    @Transient
    private int tentativas = 0;

    public void setTentativas(int tentativas) {
        this.tentativas = tentativas + 1;
        System.out.println("Tentativas: " + this.tentativas);
    }

    @Setter
    @Getter
    @ManyToOne
    @JoinColumn(name = "usuarioId")
    @JsonBackReference
    private Usuario usuario;
}
