/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto_analisisalgoritmos;

import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author leirA
 */
public class Knapsack_Mochila {

    int Capacidad_Total_Peso;
    int Capacidad_Total_Valor;
    int Cantidad_Elementos_Total;
    boolean Es_Peso;
    ArrayList<Knapsack_Elemento> Articulos_Robados = new ArrayList<Knapsack_Elemento>();

    public int getPesoTotal() { //Solo vemos cual es la capacidad maxima de la mochila
        int total = 0;
        for (int i = 0; i < this.Articulos_Robados.size(); i++) {
            total += this.Articulos_Robados.get(i).getPeso();
        }
        return total;
    }

    public int getValorTotal() { //Vemos la capacidad maxima de la mochila
        int total = 0;
        for (int i = 0; i < this.Articulos_Robados.size(); i++) {
            total += this.Articulos_Robados.get(i).getValor();
        }
        return total;
    }

    public Knapsack_Mochila(int peso, int valor, boolean Espeso) { //Constructor
        this.Cantidad_Elementos_Total = this.Articulos_Robados.size();
        this.Capacidad_Total_Peso = peso;
        this.Capacidad_Total_Valor = valor;
        this.Es_Peso = Espeso;
    }

    public boolean NuevoArticulo(Knapsack_Elemento Elemento) { //Intentamos agregar un nuevo elemento
        if (this.Es_Peso) {
            //Hacemos validacion por si el elemento excede la capacidad de la mochila
            if (Elemento.getPeso() + getPesoTotal() > this.Capacidad_Total_Peso) {
                return false;
            } else {
                this.Articulos_Robados.add(Elemento);
                this.Cantidad_Elementos_Total++;
                JOptionPane.showMessageDialog(null, "Elemento agregado");
                return true;
            }
        } else {
            //si es por valor vemos si es el maximo valor que es permitido, si deja lo ingresa
            if (Elemento.getValor() + getValorTotal() > this.Capacidad_Total_Valor) {
                return false;
            } else {
                this.Articulos_Robados.add(Elemento);
                this.Cantidad_Elementos_Total++;
                JOptionPane.showMessageDialog(null, "Elemento agregado");
                return true;
            }
        }
    }

    public String ElementosDentro() { //ToString para imprimir los elementos dentro del la mochila
        String toString = "";
        for (int i = 0; i < this.Articulos_Robados.size(); i++) {
            toString += "Elemento " + i + " \n Peso: " + this.Articulos_Robados.get(i).getPeso() + " \n Valor: " + this.Articulos_Robados.get(i).getValor() + " \n";
        }
        return toString;
    }
}
