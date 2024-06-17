package br.ufrn.imd.modelo;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.ArrayList;
import java.util.List;

public abstract class Navio {
    protected int tamanho;
    protected boolean horizontal;
    protected boolean direcao; // true para direita/baixo, false para esquerda/cima
    protected boolean posicionado;
    protected int startX;
    protected int startY;
    protected List<Coordenadas> coordenadas;
    protected boolean afundado;
    protected List<ImageView> imagens1; // Lista de ImageView
    protected List<ImageView> imagens2; // Lista de ImageView
    protected List<ImageView> imagens3; // Lista de ImageView
    protected List<ImageView> imagens4; // Lista de ImageView

    public Navio(int tamanho, boolean horizontal, boolean direcao, int startX, int startY) {
        this.tamanho = tamanho;
        this.horizontal = horizontal;
        this.direcao = direcao;
        this.posicionado = false;
        this.startX = startX;
        this.startY = startY;
        this.coordenadas = new ArrayList<>(tamanho);
        this.afundado = false; // Inicialmente, o navio não está afundado
        this.imagens1 = new ArrayList<>(tamanho); // Inicializa a lista com o tamanho do navio
        this.imagens2 = new ArrayList<>(tamanho); // Inicializa a lista com o tamanho do navio
        this.imagens3 = new ArrayList<>(tamanho); // Inicializa a lista com o tamanho do navio
        this.imagens4 = new ArrayList<>(tamanho); // Inicializa a lista com o tamanho do navio
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

    public List<ImageView> getImagens1() {
        return imagens1;
    }
    
    public List<ImageView> getImagens2() {
        return imagens2;
    }
    
    public List<ImageView> getImagens3() {
        return imagens3;
    }
    
    public List<ImageView> getImagens4() {
        return imagens4;
    }

    public void setCoordenadas(List<Coordenadas> coordenadas) {
        this.coordenadas = coordenadas;
    }

	public boolean isPosicionado() {
		return posicionado;
	}

	public void setPosicionado(boolean posicionado) {
		this.posicionado = posicionado;
	}
	public abstract void criarLists();
	
}
