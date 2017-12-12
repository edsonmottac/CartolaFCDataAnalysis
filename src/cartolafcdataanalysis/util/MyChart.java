/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cartolafcdataanalysis.util;

/**
 *
 * @author edson
 */
public class MyChart {

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public float getTotal_pontos() {
        return total_pontos;
    }

    public void setTotal_pontos(float total_pontos) {
        this.total_pontos = total_pontos;
    }

    public float getMedia_pontos() {
        return media_pontos;
    }

    public void setMedia_pontos(float media_pontos) {
        this.media_pontos = media_pontos;
    }

    public float getValor_saldo() {
        return valor_saldo;
    }

    public void setValor_saldo(float valor_saldo) {
        this.valor_saldo = valor_saldo;
    }

    private String nome;
    private float total_pontos;
    private float media_pontos;
    private float valor_saldo;
    
    
}
