/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto_analisisalgoritmos;

/**
 *
 * @author leirA
 */
public class Knapsack_Elemento {

    private int Peso;
    private int Valor;

    public int getPeso() {
        return Peso;
    }

    public void setPeso(int Peso) {
        this.Peso = Peso;
    }

    public int getValor() {
        return Valor;
    }

    public void setValor(int Valor) {
        this.Valor = Valor;
    }

    public Knapsack_Elemento(int Peso, int Valor) {
        this.Peso = Peso;
        this.Valor = Valor;
    }

}
