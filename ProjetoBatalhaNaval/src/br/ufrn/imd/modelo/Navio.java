package br.ufrn.imd.modelo;

import javafx.scene.image.ImageView;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe abstrata que representa um navio em um jogo de tabuleiro ou similar.
 * Os navios podem ser posicionados horizontalmente ou verticalmente, e têm um tamanho específico.
 * Esta classe define comportamentos e atributos comuns a todos os tipos de navios, permitindo a extensão para classes específicas de navios.
 *
 * @author JoaoVictor
 */
public abstract class Navio {
    protected int tamanho;
    protected boolean horizontal;
    protected boolean direcao; 
    protected boolean posicionado;
    protected int startX;
    protected int startY; 
    protected List<Coordenadas> coordenadas; 
    protected boolean afundado; 
    protected List<ImageView> imagens1; 
    protected List<ImageView> imagens2; 
    protected List<ImageView> imagens3;
    protected List<ImageView> imagens4; 

    /**
     * Constrói um objeto Navio com caracteristicas especificadas.
     *
     * @param tamanho O tamanho do navio.
     * @param horizontal Indica se o navio sera posicionado horizontalmente.
     * @param direcao Indica a direcao do movimento do navio (true para direita/baixo, false para esquerda/cima).
     * @param startX A posicao inicial X do navio.
     * @param startY A posicao inicial Y do navio.
     */
    public Navio(int tamanho, boolean horizontal, boolean direcao, int startX, int startY) {
        this.tamanho = tamanho;
        this.horizontal = horizontal;
        this.direcao = direcao;
        this.posicionado = false;
        this.startX = startX;
        this.startY = startY;
        this.coordenadas = new ArrayList<>(tamanho);
        this.afundado = false;
        this.imagens1 = new ArrayList<>(tamanho);
        this.imagens2 = new ArrayList<>(tamanho);
        this.imagens3 = new ArrayList<>(tamanho);
        this.imagens4 = new ArrayList<>(tamanho);
        setCoordenadas();
    }
    /**
     * Metodo abstrato para criar listas de imagens especificas para diferentes estados do navio.
     */
    public abstract void criarLists();

    /**
     * Configura as coordenadas do navio baseado em seu tamanho, orientacao e direcao.
     */
    private void setCoordenadas() {
        coordenadas.clear();
        for (int i = 0; i < tamanho; i++) {
            if (horizontal) {
                if (direcao) {
                    coordenadas.add(new Coordenadas(startX + i, startY));
                } else {
                    coordenadas.add(new Coordenadas(startX - i, startY));
                }
            } else {
                if (direcao) {
                    coordenadas.add(new Coordenadas(startX, startY + i));
                } else {
                    coordenadas.add(new Coordenadas(startX, startY - i));
                }
            }
        }
    }




    /**
     * Retorna o tamanho do navio.
     *
     * @return O tamanho do navio.
     */
    public int getTamanho() {
        return tamanho;
    }

    /**
     * Define o tamanho do navio.
     *
     * @param tamanho O novo tamanho do navio.
     */
    public void setTamanho(int tamanho) {
        this.tamanho = tamanho;
    }

    /**
     * Verifica se o navio esta posicionado horizontalmente.
     *
     * @return Verdadeiro se o navio esta posicionado horizontalmente, falso caso contrario.
     */
    public boolean isHorizontal() {
        return horizontal;
    }

    /**
     * Define se o navio esta posicionado horizontalmente.
     *
     * @param horizontal Verdadeiro para horizontal, falso para vertical.
     */
    public void setHorizontal(boolean horizontal) {
        this.horizontal = horizontal;
        setCoordenadas();
    }

    /**
     * Verifica a direcao do movimento do navio.
     *
     * @return Verdadeiro para direita/baixo, falso para esquerda/cima.
     */
    public boolean isDirecao() {
        return direcao;
    }

    /**
     * Define a direcao do movimento do navio.
     *
     * @param direcao Verdadeiro para direita/baixo, falso para esquerda/cima.
     */
    public void setDirecao(boolean direcao) {
        this.direcao = direcao;
        setCoordenadas();
    }

    /**
     * Retorna a posicao inicial X do navio.
     *
     * @return A posicao inicial X do navio.
     */
    public int getStartX() {
        return startX;
    }

    /**
     * Define a posicao inicial X do navio.
     *
     * @param startX A nova posicao inicial X do navio.
     */
    public void setStartX(int startX) {
        this.startX = startX;
        setCoordenadas();
    }

    /**
     * Retorna a posicao inicial Y do navio.
     *
     * @return A posicao inicial Y do navio.
     */
    public int getStartY() {
        return startY;
    }

    /**
     * Define a posicao inicial Y do navio.
     *
     * @param startY A nova posicao inicial Y do navio.
     */
    public void setStartY(int startY) {
        this.startY = startY;
        setCoordenadas();
    }

    /**
     * Retorna as coordenadas ocupadas pelo navio.
     *
     * @return Uma lista de coordenadas ocupadas pelo navio.
     */
    public List<Coordenadas> getCoordenadas() {
        return coordenadas;
    }

    /**
     * Indica se o navio foi afundado.
     *
     * @return Verdadeiro se o navio foi afundado, falso caso contrario.
     */
    public boolean getAfundado() {
        return afundado;
    }

    /**
     * Define se o navio foi afundado.
     *
     * @param afundado Verdadeiro para afundado, falso para nao afundado.
     */
    public void setAfundado(boolean afundado) {
        this.afundado = afundado;
    }

    /**
     * Retorna a lista de imagens para representação visual do estado 1 do navio.
     *
     * @return A lista de imagens para representação visual do estado 1 do navio.
     */
    public List<ImageView> getImagens1() {
        return imagens1;
    }

    /**
     * Retorna a lista de imagens para representação visual do estado 2 do navio.
     *
     * @return A lista de imagens para representação visual do estado 2 do navio.
     */
    public List<ImageView> getImagens2() {
        return imagens2;
    }

    /**
     * Retorna a lista de imagens para representação visual do estado 3 do navio.
     *
     * @return A lista de imagens para representação visual do estado 3 do navio.
     */
    public List<ImageView> getImagens3() {
        return imagens3;
    }

    /**
     * Retorna a lista de imagens para representação visual do estado 4 do navio.
     *
     * @return A lista de imagens para representação visual do estado 4 do navio.
     */
    public List<ImageView> getImagens4() {
        return imagens4;
    }

    /**
     * Define as coordenadas ocupadas pelo navio.
     *
     * @param coordenadas As novas coordenadas ocupadas pelo navio.
     */
    public void setCoordenadas(List<Coordenadas> coordenadas) {
        this.coordenadas = coordenadas;
    }

    /**
     * Indica se o navio ja foi posicionado.
     *
     * @return Verdadeiro se o navio ja foi posicionado, falso caso contrario.
     */
    public boolean isPosicionado() {
        return posicionado;
    }

    /**
     * Define se o navio ja foi posicionado.
     *
     * @param posicionado Verdadeiro para posicionado, falso para nao posicionado.
     */
    public void setPosicionado(boolean posicionado) {
        this.posicionado = posicionado;
    }
}

