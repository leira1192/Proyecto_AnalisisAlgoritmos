/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto_analisisalgoritmos;

import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author leirA
 */
class TSP_Tablero_Matriz_Adyacencia extends JPanel {
    //Variable global para Matriz de adyacencia

    JTextField[][] Matriz_Main;

    TSP_Tablero_Matriz_Adyacencia() {

    }
    //Actualizamos la matriz de adyacencia para completar el panel visual y hacer los calculos correspondientes para las rutas

    public void setMatriz(float Matriz_Completa[][]) {
        this.removeAll();
        //Dibujamos la matriz en el panel principal
        Matriz_Main = new JTextField[Matriz_Completa.length][Matriz_Completa[0].length];
        this.setLayout(new GridLayout(Matriz_Completa.length, Matriz_Completa[0].length));
        for (int x = 0; x < Matriz_Completa.length; x++) {
            for (int y = 0; y < Matriz_Completa[0].length; y++) {
                //seteamos los valores
                Matriz_Main[x][y] = new JTextField();
                Matriz_Main[x][y].setEditable(false);
                Matriz_Main[x][y].setAutoscrolls(false);
                Matriz_Main[x][y].setFont((new Font("Arial", Font.BOLD, 10)));
                Matriz_Main[x][y].setText("" + Matriz_Completa[x][y]);
                Matriz_Main[x][y].setCaretPosition(0);
                add(Matriz_Main[x][y]);
            }
        }
        //Actualizamos el panel principal para que se muestre la matriz
        this.updateUI();

    }

}
