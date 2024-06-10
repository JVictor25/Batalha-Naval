package br.ufrn.imd.modelo;

public class Destroyer extends Navio {
    public Destroyer(boolean horizontal, boolean direcao, int startX, int startY) {
        super(5, horizontal, direcao, startX, startY); // Destroyer tem tamanho 5
    }
}