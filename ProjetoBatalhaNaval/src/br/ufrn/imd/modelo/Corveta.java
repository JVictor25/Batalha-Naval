package br.ufrn.imd.modelo;

public class Corveta extends Navio {
    public Corveta(boolean horizontal, boolean direcao, int startX, int startY) {
        super(2, horizontal, direcao, startX, startY); // Corveta tem tamanho 2
    }
}