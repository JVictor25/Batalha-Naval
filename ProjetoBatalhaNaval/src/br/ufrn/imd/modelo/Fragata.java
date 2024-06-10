package br.ufrn.imd.modelo;

public class Fragata extends Navio {
    public Fragata(boolean horizontal, boolean direcao, int startX, int startY) {
        super(4, horizontal, direcao, startX, startY); // Fragata tem tamanho 4
    }
}