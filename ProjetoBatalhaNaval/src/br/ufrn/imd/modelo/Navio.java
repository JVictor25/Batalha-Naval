package br.ufrn.imd.modelo;

import java.util.ArrayList;
import java.util.List;

public abstract class Navio {
    protected int tamanho;
    protected boolean horizontal;
    protected boolean direcao; // true para direita/baixo, false para esquerda/cima
    protected int startX;
    protected int startY;
    protected List<Coordenadas> coordenadas;
    protected boolean afundado;

    public Navio(int tamanho, boolean horizontal, boolean direcao, int startX, int startY) {
        this.tamanho = tamanho;
        this.horizontal = horizontal;
        this.direcao = direcao;
        this.startX = startX;
        this.startY = startY;
        this.coordenadas = new ArrayList<>(tamanho);
        this.afundado = false; // Inicialmente, o navio não está afundado
        setCoordenadas();
    }

    private void setCoordenadas() {
        coordenadas.clear();
        for (int i = 0; i < tamanho; i++) {
            if (horizontal) {
                if (direcao) {
                    coordenadas.add(new Coordenadas(startX + i, startY)); // Para a direita
                } else {
                    coordenadas.add(new Coordenadas(startX - i, startY)); // Para a esquerda
                }
            } else {
                if (direcao) {
                    coordenadas.add(new Coordenadas(startX, startY + i)); // Para baixo
                } else {
                    coordenadas.add(new Coordenadas(startX, startY - i)); // Para cima
                }
            }
        }
    }

    public int getTamanho() {
        return tamanho;
    }

    public void setTamanho(int tamanho) {
        this.tamanho = tamanho;
    }

    public boolean isHorizontal() {
        return horizontal;
    }

    public void setHorizontal(boolean horizontal) {
        this.horizontal = horizontal;
        setCoordenadas();
    }

    public boolean isDirecao() {
        return direcao;
    }

    public void setDirecao(boolean direcao) {
        this.direcao = direcao;
        setCoordenadas();
    }

    public int getStartX() {
        return startX;
    }

    public void setStartX(int startX) {
        this.startX = startX;
        setCoordenadas();
    }

    public int getStartY() {
        return startY;
    }

    public void setStartY(int startY) {
        this.startY = startY;
        setCoordenadas();
    }

    public List<Coordenadas> getCoordenadas() {
        return coordenadas;
    }

    public boolean getAfundado() {
        return afundado;
    }

    public void setAfundado(boolean afundado) {
        this.afundado = afundado;
    }
}