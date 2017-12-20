/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto_analisisalgoritmos;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

/**
 *
 * @author leirA
 */
public class TSP_Grafo_Agente extends JFrame implements Runnable {

    Container Contenido; //Panel de contenido
    TSP_Grafo_Mapa Mapa; //Mapa del los grafos
    JList Caminos; //Lista de caminos posibles
    Vector Camino; //Camino actual evaluado
    JButton Botton_Comenzar; //Botones
    JButton Botton_Camino_Optimo; //Botones
    JLabel Label1, Label2, Label3, Label4; //Viñetas para rotular
    JList Lista; //Lista de vertices
    JScrollPane Panel; //Panel para el contenido general
    JComboBox Combo_Vertices; //Lista de vertices para inicializar el recorrido
    int Vertice_Inicial;
    int Camino_Seleccionado = -1;
    float[][] Matriz_Adyacencia;
    TSP_Tablero_Matriz_Adyacencia Tablero;
    Thread Hilo_Agente;
    TSP_Grafo_Agente Grafo_Agente_Actual;

    TSP_Grafo_Agente() {
        Contenido = this.getContentPane();
        Contenido.setLayout(null);
        Grafo_Agente_Actual = this;
        Caminos = new JList();
        Camino = new Vector();
        Tablero = new TSP_Tablero_Matriz_Adyacencia();
        Mapa = new TSP_Grafo_Mapa();
        Mapa.setBounds(10, 10, 200, 200);
        Contenido.add(Mapa);
        Label1 = new JLabel("Vertice Inicial");
        Label2 = new JLabel("Matriz de Adyacencia");
        Botton_Comenzar = new JButton("Generar Matriz de Adyacencia");
        Botton_Camino_Optimo = new JButton("Generar Camino Optimizado");
        this.setTitle("Problema TSP_ Grafos");
        Botton_Comenzar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String Cadena = "";
                Camino.clear();
                int Tamanio = getCamino();
                String Vector[] = new String[Tamanio];
                for (int i = 0; i < Tamanio; i++) {
                    Vector[i] = "" + (i + 1);
                    Combo_Vertices.addItem(Vector[i]);
                }
                Lista.updateUI();
                Lista.setListData(Camino);
                Panel.updateUI();
                Botton_Comenzar.setVisible(false);
                Botton_Camino_Optimo.setVisible(true);
                Matriz(Tamanio);
                JOptionPane.showMessageDialog(null, "Se ha creado una matriz de adyacencia con distancias estandar y se medira distancia");
                Tablero.setMatriz(Matriz_Adyacencia);
                JOptionPane.showMessageDialog(null, "Selecciones nodo inicial");
            }
        });
        Lista = new JList();
        Panel = new JScrollPane(Lista);
        //Modificacion de tamaños y posiciones de los elementos
        Combo_Vertices = new JComboBox();
        Label1.setBounds(211, 10, 100, 20);
        Combo_Vertices.setBounds(211, 30, 100, 20);
        Botton_Comenzar.setBounds(211, 210, 200, 20);
        Botton_Camino_Optimo.setBounds(211, 210, 200, 20);
        Botton_Camino_Optimo.setVisible(false);
        Label2.setBounds(11, 210, 200, 20);
        Panel.setBounds(211, 55, 100, 150);
        Tablero.setBounds(11, 240, 800 - 511, 160);
        //Agregamos los elementos al panel principal del contenido
        Contenido.add(Tablero);
        Contenido.add(Combo_Vertices);
        Contenido.add(Label1);
        Contenido.add(Label2);
        Contenido.add(Botton_Comenzar);
        Contenido.add(Botton_Camino_Optimo);
        Contenido.add(Panel);
        //Hacemos el clickAction activo para cualquier momento para evaluar la matriz de adyacencia
        Botton_Camino_Optimo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                double temp_milis = System.currentTimeMillis();
                int tamanio_temporal = getCamino();
                Vertice_Inicial = Combo_Vertices.getSelectedIndex() + 1;
                Camino.clear();
                String Cad_Temp = "";
                //Comenzamos a analizar cada uno de los posibles caminos entre los vertices y calculamos el menor
                Siguiente_Matriz_Caminos(Combo_Vertices.getSelectedIndex(), tamanio_temporal, 0, Cad_Temp);
                double Tiempo_Milis_Restante = System.currentTimeMillis() - temp_milis;
                Lista.updateUI();
                JOptionPane.showMessageDialog(null, "Tiempo de respuesta en milisegundos " + Tiempo_Milis_Restante + ", pero se va a mostrar como se calcularon los caminos");
                Hilo_Agente = new Thread(Grafo_Agente_Actual);
                Hilo_Agente.start();
            }
        });
    }

    public void run() {
        int i = 0;
        float Menor_Distancia = getValidacionCadena("" + Camino.elementAt(0)), menor_temp = 0;
        while (i < Camino.size()) {
            try {
                String Cadena_Temp = "";
                Cadena_Temp = "" + Camino.elementAt(i);
                menor_temp = getValidacionCadena(Cadena_Temp);
                Mostrar_Camino_Actual(Cadena_Temp);
                Thread.sleep(1); //Se hace una espera de 1 milisegundo para mostrar el proceso del camino evaluado
                if (menor_temp <= Menor_Distancia) {
                    //Se compara los caminos actuales y el temporar y se evalua cual es menor, y el menor es seleccionado para convertirse en el nuevo temporal
                    Camino_Seleccionado = i;
                    Menor_Distancia = menor_temp;
                }
                i++;
            } catch (Exception ex) {
            }
        }
        Mostrar_Camino_Actual("" + Camino.elementAt(Camino_Seleccionado));
        JOptionPane.showMessageDialog(Tablero, "Distancia menor: " + Menor_Distancia + "\n" + "Ruta Optima a seguir por vertice: " + Camino.elementAt(Camino_Seleccionado), "Ruta Optimizada TSP", 1);

    }

    public void Matriz(int Numero) {
        Matriz_Adyacencia = new float[Numero][Numero];
        for (int k = 0; k < Numero; k++) {
            for (int z = 0; z < Numero; z++) {
                Matriz_Adyacencia[k][z] = getDistancia("" + k, "" + z);
            }
        }
    }

    public float getDistancia(String i, String j) {
        //Metodo para evaluar la distancia entre dos vertices seleccionados, recibimos
        //Como parametro el numero de vertice que comparamos
        int CoordenadaA1, CoordenadaB1, CoordenadaA2, CoordenadaB2;
        float Resultado_DistanciaHorizontal, Resultado_DistanciaVertical;
        //Consultamos en el mapa las coordenadas
        CoordenadaA1 = Mapa.getHorizontal(Integer.parseInt(i));
        CoordenadaB1 = Mapa.getHorizontal(Integer.parseInt(j));
        CoordenadaA2 = Mapa.getVertical(Integer.parseInt(i));
        CoordenadaB2 = Mapa.getVertical(Integer.parseInt(j));
        //Casteamos el resultado para hacerlo flotante y pormediamos un numero
        Resultado_DistanciaHorizontal = (float) Math.pow(CoordenadaB1 - CoordenadaA1, 2);
        //tanto en horizontal como vertical
        Resultado_DistanciaVertical = (float) Math.pow(CoordenadaB2 - CoordenadaA2, 2);
        float retorno = (float) Math.sqrt((Resultado_DistanciaHorizontal + Resultado_DistanciaVertical));
        return retorno;
    }

    public float getTotal() {
        String Cadena_Temporal;
        float Distancia_Menor_Actual = getValidacionCadena("" + Camino.elementAt(0));
        float Cadena_Distancia_Temporal = 0;
        for (int i = 0; i < Camino.size(); i++) {
            Cadena_Temporal = "" + Camino.elementAt(i);
            Cadena_Distancia_Temporal = getValidacionCadena(Cadena_Temporal);
            if (Cadena_Temporal.startsWith("" + Vertice_Inicial)) {
                if (Cadena_Distancia_Temporal <= Distancia_Menor_Actual) {
                    Camino_Seleccionado = i;
                    Distancia_Menor_Actual = Cadena_Distancia_Temporal;
                }
            } else {
                return Distancia_Menor_Actual;
            }
        }
        return Distancia_Menor_Actual;
    }

    public float getValidacionCadena(String cadena_temp) {
        //Validamos la cadena, retorna en numero de la cadena validado
        float numero_retorno = 0;
        int numeroA = 0, numeroB = 0;
        for (int i = 0; i < cadena_temp.length() - 1; i++) {
            numeroA = Integer.parseInt(cadena_temp.substring(i, i + 1));
            numeroB = Integer.parseInt(cadena_temp.substring(i + 1, i + 2));
            numero_retorno = numero_retorno + Matriz_Adyacencia[numeroA - 1][numeroB - 1];
        }
        //return
        return numero_retorno;
    }

    public void Siguiente_Matriz_Caminos(int i, int n, int p, String Acumulado) {
        //metodo recursivo para evaluar los caminos en distancia
        //Se hacer recursivo para asegurarse de pasar por todos los vertices y agregar todos los caminos a la lista de caminos posibles
        if (p < n && i < n)// si se acabo un para o un contador
        {
            boolean no = false;
            for (int k = 0; k < Acumulado.length() && !no; k++)//si ya esta el numero del vertice
            {
                if (Acumulado.substring(k, k + 1).equals("" + (i + 1))) {
                    no = true;
                }
            }
            //Evalua los caminos posibles y analiza si son iguales, menores o mayores
            if (!no) {
                Siguiente_Matriz_Caminos(0, n, p + 1, Acumulado + "" + (i + 1));
            }
            Siguiente_Matriz_Caminos(i + 1, n, p, Acumulado);
            no = false;
            for (int k = 0; k < Acumulado.length() && !no; k++) {
                if (Acumulado.substring(k, k + 1).equals("" + (i + 1))) {
                    no = true;
                }
            }
            if (!no && Acumulado.length() == n - 1) {
                Acumulado = Acumulado + "" + (i + 1) + Vertice_Inicial;
                if (Acumulado.startsWith("" + Vertice_Inicial)) {
                    Camino.add(Acumulado);
                } else {
                    return;
                }
            }
        }
    }

    public int getCamino() {
        // Obtenemos el camino del Mapa
        return Mapa.getCamino();
    }

    public void Mostrar_Camino_Actual(String camino) {
        //Actualizamos en el mapa el camino que actualmente evaluamos
        Mapa.setResultado(camino);
    }
}
