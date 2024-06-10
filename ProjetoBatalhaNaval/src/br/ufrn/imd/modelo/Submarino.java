package br.ufrn.imd.modelo;

public class Submarino extends Navio {
    public Submarino(boolean horizontal, boolean direcao, int startX, int startY) {
        super(3, horizontal, direcao, startX, startY); // Submarino tem tamanho 3
    }
}