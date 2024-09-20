package br.ufrn.imd.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import br.ufrn.imd.modelo.Coordenadas;
import br.ufrn.imd.modelo.Corveta;
import br.ufrn.imd.modelo.Destroyer;
import br.ufrn.imd.modelo.Fragata;
import br.ufrn.imd.modelo.Navio;
import br.ufrn.imd.modelo.Submarino;

/**
 * Representa o tabuleiro de um jogo de batalha naval.
 * Este tabuleiro contém uma lista de navios e um array bidimensional
 * que representa o estado atual do tabuleiro apos os tiros.
 *
 * @author JoaoVictor
 */

public class Tabuleiro {
    private static final int SIZE = 10;
    private int[][] board;
    private List<Navio> navios;

    
    public Tabuleiro() {
        board = new int[SIZE][SIZE];
        navios = new ArrayList<>();
        for (int i = 0; i < SIZE; i++) {
            Arrays.fill(board[i], 0);
        }
    }

    /**
     * Coloca um navio no tabuleiro de acordo com suas coordenadas.
     * Retorna verdadeiro se o navio foi colocado com sucesso, falso caso contrario.
     *
     * @param navio O navio a ser colocado no tabuleiro.
     * @return Verdadeiro se o navio foi colocado com sucesso, falso caso contrario.
     */
    
    public boolean placeShip(Navio navio) {
        boolean auxiliar = true;
        for (Coordenadas coordenada : navio.getCoordenadas()) {
            int x = coordenada.getX();
            int y = coordenada.getY();
            if (!dentroDoTabuleiro(x, y) || board[x][y] == 1) {
                auxiliar = false;
                break;
            }
        }
        if (auxiliar) {
            for (Coordenadas coordenada : navio.getCoordenadas()) {
                int x = coordenada.getX();
                int y = coordenada.getY();
                if (dentroDoTabuleiro(x, y) && board[x][y] == 0) {
                    board[x][y] = 1;
                }
            }
            navio.setPosicionado(auxiliar);
            navios.add(navio);
        }
        return auxiliar;
    }

    /**
     * Tenta reposicionar um navio no tabuleiro.
     * Retorna verdadeiro se o navio foi reposicionado com sucesso, falso caso contrario.
     *
     * @param navio O navio a ser reposicionado.
     * @return Verdadeiro se o navio foi reposicionado com sucesso, falso caso contrario.
     */
    
    public boolean replaceShip(Navio navio) {
        boolean auxiliar = true;
        for (Coordenadas coordenada : navio.getCoordenadas()) {
            int x = coordenada.getX();
            int y = coordenada.getY();
            if (!dentroDoTabuleiro(x, y) || board[x][y] == 1) {
                auxiliar = false;
                break;
            }
        }
        if (auxiliar) {
            for (Coordenadas coordenada : navio.getCoordenadas()) {
                int x = coordenada.getX();
                int y = coordenada.getY();
                if (dentroDoTabuleiro(x, y) && board[x][y] == 0) {
                    board[x][y] = 1;
                }
            }
        } else {
            setCoordenadas(navio);
        }
        return auxiliar;
    }

    /**
     * Restaura as coordenadas originais de um navio.
     *
     * @param navio O navio cujas coordenadas devem ser restauradas.
     */
    private void setCoordenadas(Navio navio) {
        navio.getCoordenadas().clear();
        for (int i = 0; i < navio.getTamanho(); i++) {
            if (navio.isHorizontal()) {
                if (navio.isDirecao()) {
                    navio.getCoordenadas().add(new Coordenadas(navio.getStartX() + i, navio.getStartY()));
                } else {
                    navio.getCoordenadas().add(new Coordenadas(navio.getStartX() - i, navio.getStartY()));
                }
            } else {
                if (navio.isDirecao()) {
                    navio.getCoordenadas().add(new Coordenadas(navio.getStartX(), navio.getStartY() + i));
                } else {
                    navio.getCoordenadas().add(new Coordenadas(navio.getStartX(), navio.getStartY() - i));
                }
            }
        }
    }

    /**
     * Remove o navio de sua posicao atual no tabuleiro.
     *
     * @param navio O navio cuja posicao deve ser removida.
     */
    public void limparPosicaoNavio(Navio navio) {
        if (navio.isPosicionado()) {
            for (Coordenadas coordenada : navio.getCoordenadas()) {
                int x = coordenada.getX();
                int y = coordenada.getY();
                if (dentroDoTabuleiro(x, y) && board[x][y] == 1) {
                    board[x][y] = 0; // Limpa a posição anterior
                }
            }
        }
    }

    /**
     * Realiza um tiro no tabuleiro nas coordenadas fornecidas.
     *
     * @param x A coordenada X onde o tiro será feito.
     * @param y A coordenada Y onde o tiro será feito.
     */
    
    public void shoot(int x, int y) {
        if (!dentroDoTabuleiro(x, y)) {
            throw new IllegalArgumentException("Coordenadas fora dos limites do tabuleiro");
        }
        if (board[x][y] == 1) {
            board[x][y] = 2; // Acerto
            System.out.println("Tiro em (" + x + ", " + y + "): Acertou");
            verificarAfundamento(x, y);
        } else if (board[x][y] == 0) {
            board[x][y] = 3; // Água
            System.out.println("Tiro em (" + x + ", " + y + "): Água");
        } else {
            System.out.println("Tiro em (" + x + ", " + y + "): Já acertado");
        }
    }
    
    /**
     * Verifica se um navio foi completamente atingido pelo disparo realizado nas coordenadas fornecidas.
     * Itera sobre todos os navios registrados no tabuleiro e verifica se todas as coordenadas desse navio
     * correspondem a celulas atingidas (representadas por 2 no array do tabuleiro).
     * Se um navio inteiro for atingido, marca-o como afundado e imprime uma mensagem informando sobre o afundamento.
     *
     * @param x A coordenada X onde ocorreu o tiro.
     * @param y A coordenada Y onde ocorreu o tiro.
     */
    private void verificarAfundamento(int x, int y) {
        for (Navio navio : navios) {
            boolean afundado = true;
            for (Coordenadas coordenada : navio.getCoordenadas()) {
                if (board[coordenada.getX()][coordenada.getY()] != 2) {
                    afundado = false;
                    break;
                }
            }
            if (afundado) {
                navio.setAfundado(true);
                System.out.println(navio.getClass().getSimpleName() + " afundou!");
            }
        }
    }
    /**
     * Verifica se as coordenadas fornecidas estao dentro dos limites do tabuleiro.
     *
     * @param x A coordenada X a ser verificada.
     * @param y A coordenada Y a ser verificada.
     * @return Verdadeiro se as coordenadas estão dentro dos limites, falso caso contrário.
     */
    public boolean dentroDoTabuleiro(int x, int y) {
        return x >= 0 && x < SIZE && y >= 0 && y < SIZE;
    }

    /**
     * Verifica se todos os navios foram afundados.
     *
     * @return Verdadeiro se todos os navios foram afundados, falso caso contrario.
     */
    public boolean todosNaviosAfundados() {
        for (Navio navio : navios) {
            if (!navio.getAfundado()) {
                return false;
            }
        }
        return true;
    }


    // Getters e setters para board e navios
    public int[][] getBoard() {
        return board;
    }

    public void setBoard(int[][] board) {
        this.board = board;
    }

    public List<Navio> getNavios() {
        return navios;
    }

    public void setNavios(List<Navio> navios) {
        this.navios = navios;
    }
}