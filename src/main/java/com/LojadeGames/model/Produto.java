package com.LojadeGames.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "tb_produto")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O Atributo Nome é obrigatório")
    private String nome;

    @NotNull(message = "O Atributo valor é obrigatório")
    private String  fabricante;

    @NotNull(message = "O Atrbuto quantidade é obrigatório")
    private int quantidade;
    @ManyToOne
    @JsonIgnoreProperties("produto")
    private Categoria categoria;


    @ManyToOne
    @JsonIgnoreProperties("produtos")
    private Clientes clientes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Clientes getClientes() {
        return clientes;
    }

    public void setClientes(Clientes clientes) {
        this.clientes = clientes;
    }

    public int getQuantidade() {return quantidade; }

    public void setQuantidade(int quantidade) {this.quantidade = quantidade;}
}
