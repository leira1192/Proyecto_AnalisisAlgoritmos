package Analizadores;
import java_cup.runtime.*;
import java.util.LinkedList;
%%

%{
    //Codigo java
    public static LinkedList<TError> TablaEL = new LinkedList<TError>();
%}


%public
%class Analizador_Lexico
%cupsym Simbolos
%cup
%char
%column
%line
%full
%ignorecase
%unicode

numero = [0-9]+

%%
//Simbolos
<YYINITIAL> "+" {System.out.println("Reconocio " + yytext() + " mas");return new Symbol(Simbolos.mas, yycolumn, yyline, yytext());}
<YYINITIAL> "-" {System.out.println("Reconocio " + yytext() + " menos");return new Symbol(Simbolos.menos, yycolumn, yyline, yytext());}
<YYINITIAL> "/" {System.out.println("Reconocio " + yytext() + " div");return new Symbol(Simbolos.div, yycolumn, yyline, yytext());}
<YYINITIAL> "*" {System.out.println("Reconocio " + yytext() + " por");return new Symbol(Simbolos.por, yycolumn, yyline, yytext());}
<YYINITIAL> "(" {System.out.println("Reconocio " + yytext() + " para");return new Symbol(Simbolos.para, yycolumn, yyline, yytext());}
<YYINITIAL> ")" {System.out.println("Reconocio " + yytext() + " parac");return new Symbol(Simbolos.parc, yycolumn, yyline, yytext());}

// Expresiones regulares

<YYINITIAL> {numero} { return new Symbol(Simbolos.num, yycolumn, yyline, yytext()); }
[ \t\r\n\f] {/* Espacios en blanco, se ignoran */}

.            { System.out.println("Error Lexico" + yytext() + " Linea " + yyline + " Columna " + yycolumn);
            TError datos = new TError(yytext(), yyline, yycolumn, "Error Lexico", "Simbolo no existe en el lenguaje");
            TablaEL.add(datos);}