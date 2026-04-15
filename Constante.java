package com.tienda.domain;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "constante")
public class Constante implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_constante")
    private Integer idConstante;

    @Column(nullable = false, unique = true, length = 25)
    private String atributo;

    @Column(nullable = false, length = 150)
    private String valor;

    public Integer getIdConstante() { return idConstante; }
    public void setIdConstante(Integer idConstante) { this.idConstante = idConstante; }

    public String getAtributo() { return atributo; }
    public void setAtributo(String atributo) { this.atributo = atributo; }

    public String getValor() { return valor; }
    public void setValor(String valor) { this.valor = valor; }
}