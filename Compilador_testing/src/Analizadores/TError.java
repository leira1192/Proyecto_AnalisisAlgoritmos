/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Analizadores;

/**
 *
 * @author leirA
 */
public class TError {
    String lexema, tipo, descripcion;
    int linea, columna;
    
    public TError(String le, int li,int co, String ti, String de){
        lexema = le;
        linea = li;
        columna = co;
        tipo = ti;
        descripcion = de;
    }
}
