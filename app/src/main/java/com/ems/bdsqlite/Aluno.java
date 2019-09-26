package com.ems.bdsqlite;

import java.io.Serializable;

// POJO - Plain Old Java Objects
public class Aluno implements Serializable {
    private String ra;
    private String nome;
    private String curso;
    private String campus;

    /**
     * Método construtor da classe
     *
     * @param ra
     * @param nome
     * @param curso
     * @param campus
     */
    public Aluno(String ra, String nome, String curso, String campus) {
        this.ra = ra;
        this.nome = nome;
        this.curso = curso;
        this.campus = campus;
    }

    // Getters and Setters
    public String getRa() {
        return ra;
    }

    public void setRa(String ra) {
        this.ra = ra;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getCampus() {
        return campus;
    }

    public void setCampus(String campus) {
        this.campus = campus;
    }

    /**
     * Método sobrescrito para retornar o nome do aluno na ListView
     *
     * @return
     */
    @Override
    public String toString() {
        return nome;
    }

    /**
     * Método que retorna todos os dados de uma só vez
     *
     * @return
     */
    public String getDados() {
        return "RA: " + ra + "\n" +
                "Nome: " + nome + "\n" +
                "Curso: " + curso + "\n" +
                "Campus: " + campus;
    }
}
