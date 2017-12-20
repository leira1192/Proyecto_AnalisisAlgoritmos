package proyecto_analisisalgoritmos;

/**
 *
 * @author leirA
 */
import java.awt.*;
import java.applet.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.geom.*;
import java.util.*;

class TSP_Grafo_Mapa extends JPanel {

    int Coordenada_X;
    int Coordenada_Y;
    int Temp_Y;
    Image Buffer;
    Graphics2D Grafico2D;
    int Puntos_Flotantes = 0;
    Point[] Dibujo_Punto;
    Vector Posicion;
    Vector Horizontal;
    Vector Vertical;
    boolean Resultado_Actual = false;
    String Camino_Actual;
    boolean Bandera_Limite = false;

    TSP_Grafo_Mapa() {
        this.setBackground(Color.black);
        Dibujo_Punto = new Point[100];
        Posicion = new Vector();
        Vertical = new Vector();
        Horizontal = new Vector();
        this.setBorder(BorderFactory.createBevelBorder(1));

        this.addMouseMotionListener(new MouseMotionAdapter() { //agregamos el metodo para hacer que el mouse se mueva y setee la posicion
            public void mouseMoved(MouseEvent e) {
                Coordenada_X = e.getX() / 20;
                //set coordenada
                Temp_Y = e.getY();
                //temporal
                Coordenada_Y = ((Temp_Y - getHeight()) * -1);
                //Set coordenada
                repaint(); //pintamos segun la posicion en el mapa
            }
        });

        this.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (!Bandera_Limite) {
                    if (!((Coordenada_X == 24) || (Coordenada_Y / 20 == 24))) {
                        Dibujo_Punto[Puntos_Flotantes++] = e.getPoint();
                        Posicion.add("(" + e.getX() / 20 + "," + Coordenada_Y / 20 + ")");
                        //Agregamos posiciones del mouse
                        Horizontal.add(e.getX() / 20);
                        Vertical.add(Coordenada_Y / 20);
                        repaint();//re pintamos
                        //validamos que los puntos no sean mayores a 10
                        if (Puntos_Flotantes >= 10) { //si es mayor a 9 entonces manda un mensaje de error
                            Bandera_Limite = true;
                            JOptionPane.showMessageDialog(null, "Solo se pueden agregar como mucho 9 vertices", "Validacion: Has llegado al numero maximo de vertices", JOptionPane.INFORMATION_MESSAGE);
                        }//fin del if
                    }
                }
            }
        });
    }

    public void paintComponent(Graphics g) {
        Buffer = this.createImage(this.getWidth(), this.getWidth());
        Grafico2D = (Graphics2D) Buffer.getGraphics();
        Pintar_Mapa(Grafico2D);
        g.drawImage(Buffer, 0, 0, this.getWidth(), this.getWidth(), this);
    }

    public void Pintar_Mapa(Graphics2D Grafico) {
        //Con esto mejoraremos la calidad de la pintada de los puntos
        Grafico.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Grafico.setColor(Color.BLACK);
        //Pintamos el fondo negro
        Grafico.fillRect(0, 0, getWidth(), getHeight());
        Grafico.setColor(Color.GREEN);
        //pintamos lineas verdes
        for (int i = 0; i < 100; i += 4) {
            Grafico.drawLine(5 * i, 500, 5 * i, 0);
            Grafico.drawLine(0, 5 * i, 500, 5 * i);
        }

        Grafico.setColor(Color.WHITE);
        Grafico.setFont(new Font("Arial", Font.BOLD, 12));
        Grafico.drawString("(" + Coordenada_X + "," + Coordenada_Y / 20 + ")", Coordenada_X * 20 + 2, Temp_Y);

        if (this.Resultado_Actual) {
            this.Print_Resultado(Grafico);
        }

        for (int i = 0; i < Puntos_Flotantes; i++) {
            Grafico.setColor(Color.WHITE); //Dibujamos el circulo para el grafico
            //asignamos color
            Grafico.fillOval(Dibujo_Punto[i].x - 8, Dibujo_Punto[i].y - 8, 18, 18);
            Grafico.setColor(Color.RED);
            //Numero correspondiente al numero de vertice
            Grafico.drawString("" + (i + 1), (Dibujo_Punto[i].x - 8) + 9 / 2, (Dibujo_Punto[i].y - 9) + 15);
            //pintamos
            Grafico.setColor(Color.YELLOW);
            //Colocamos las coordenadas que tendra el vertice
            Grafico.drawString("" + Posicion.elementAt(i), Dibujo_Punto[i].x - 9, Dibujo_Punto[i].y - 18);
        }

    }

    public void Print_Resultado(Graphics2D Grafico_Temp) {//Dibujamos la arista entre dos vertices y su posicion
        int Temporal1, Temporal2;
        Grafico_Temp.setColor(Color.RED);
        //Traemos por referencia el grafico y coloreamos las lineas para conectar los vertices y ponemos el numero sobre ella
        for (int i = 0; i < Camino_Actual.length() - 1; i++) {
            Temporal1 = Integer.parseInt("" + Camino_Actual.substring(i, i + 1));
            Temporal2 = Integer.parseInt("" + Camino_Actual.substring(i + 1, i + 2));
            Grafico_Temp.drawLine(Dibujo_Punto[Temporal1 - 1].x, Dibujo_Punto[Temporal1 - 1].y, Dibujo_Punto[Temporal2 - 1].x, Dibujo_Punto[Temporal2 - 1].y);
            Grafico_Temp.drawString("" + (i + 1), (int) ((((Dibujo_Punto[Temporal1 - 1].x + Dibujo_Punto[Temporal2 - 1].x) / 2) + Dibujo_Punto[Temporal1 - 1].x) / 2), (int) ((((Dibujo_Punto[Temporal1 - 1].y + Dibujo_Punto[Temporal2 - 1].y) / 2) + Dibujo_Punto[Temporal1 - 1].y) / 2));
        }
    }

    public int getCamino() {
        //Devolvemos el camino de recorrido de los puntos flotantes
        return this.Puntos_Flotantes;
    }

    public int getHorizontal(int Numero) {
        //Recibimos la posicion horizontal del vertice
        return Integer.parseInt("" + Horizontal.elementAt(Numero));
    }

    public int getVertical(int Numero) {
        //Devolvemos la posicion vertical del vertice solicitado
        return Integer.parseInt("" + Vertical.elementAt(Numero));
    }

    public void setResultado(String camino) {
        //Camino que actualmente se evalua, se setea y se recibe
        this.Resultado_Actual = true;
        this.Camino_Actual = camino;
        repaint();

    }

    public void update(Graphics g) {
        paint(g);
    }
}
