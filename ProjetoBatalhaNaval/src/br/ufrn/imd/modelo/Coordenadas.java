package br.ufrn.imd.modelo;

/**
 * Representa as coordenadas em um plano bidimensional.
 * Esta classe encapsula dois valores inteiros, x e y, que representam as coordenadas em um sistema de coordenadas cartesianas.
 *
 * @author JoaoVictor
 */
public class Coordenadas {
    private int x; // A coordenada x na linha
    private int y; // A coordenada y na coluna

    /**
     * Constrói uma nova instância de Coordenadas com os valores fornecidos para x e y.
     *
     * @param x A coordenada x na linha
     * @param y A coordenada y na coluna
     */
    public Coordenadas(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Retorna o valor da coordenada x.
     *
     * @return O valor da coordenada x
     */
    public int getX() {
        return x;
    }

    /**
     * Define o valor da coordenada x.
     *
     * @param x O novo valor para a coordenada x
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Retorna o valor da coordenada y.
     *
     * @return O valor da coordenada y
     */
    public int getY() {
        return y;
    }

    /**
     * Define o valor da coordenada y.
     *
     * @param y O novo valor para a coordenada y
     */
    public void setY(int y) {
        this.y = y;
    }
}
