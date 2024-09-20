package br.ufrn.imd.dao;

import java.util.Random;

import br.ufrn.imd.modelo.Coordenadas;
import br.ufrn.imd.modelo.Corveta;
import br.ufrn.imd.modelo.Destroyer;
import br.ufrn.imd.modelo.Fragata;
import br.ufrn.imd.modelo.Navio;
import br.ufrn.imd.modelo.Submarino;

/**
 * Classe responsavel pelo controle do tabuleiro inimigo em um jogo de batalha naval.
 * Esta classe gerencia a posição dos navios inimigos, realiza ataques automáticos
 * e fornece informaçoes atraves de um radar.
 *
 * @author JoaoVictor
 */
public class TabuleiroInimigo {
    private Tabuleiro tabuleiro; 
    private Random random; 

    /**
     * Constroi um novo objeto TabuleiroInimigo, inicializando o tabuleiro e posicionando os navios inimigos.
     */
    public TabuleiroInimigo() {
        tabuleiro = new Tabuleiro();
        random = new Random();
        posicionarNavios();
    }

    /**
     * Posiciona os navios inimigos aleatoriamente no tabuleiro.
     */
    
    
    private void posicionarNavios() {
        posicionarNavioAleatorio(new Corveta(false, random.nextBoolean(), random.nextInt(10), random.nextInt(10)));
        posicionarNavioAleatorio(new Submarino(false, random.nextBoolean(), random.nextInt(10), random.nextInt(10)));
        posicionarNavioAleatorio(new Fragata(false, random.nextBoolean(), random.nextInt(10), random.nextInt(10)));
        posicionarNavioAleatorio(new Destroyer(false, random.nextBoolean(), random.nextInt(10), random.nextInt(10)));
    }

    /**
     * Tenta posicionar um navio inimigo aleatoriamente no tabuleiro ate que seja colocado com sucesso.
     *
     * @param navio Navio a ser posicionado.
     */
    private void posicionarNavioAleatorio(Navio navio) {
        boolean navioColocado;
        do {
            int x = random.nextInt(10);
            int y = random.nextInt(10);
            navio.setStartX(x);
            navio.setStartY(y);
            if (random.nextBoolean()) {
                navio.setHorizontal(true);
            } else {
                navio.setHorizontal(false);
            }
            if (random.nextBoolean()) {
                navio.setDirecao(true);
            } else {
                navio.setDirecao(false);
            }
            navioColocado = tabuleiro.placeShip(navio);
        } while (!navioColocado);
    }

    /**
     * Realiza um ataque automatico no tabuleiro do jogador, tentando atingir um navio inimigo.
     * Retorna as coordenadas do ataque realizado.
     *
     * @param tabuleiroDoJogador Tabuleiro do jogador.
     * @return Coordenadas do ataque realizado.
     */
    
    public Coordenadas realizarAtaque(Tabuleiro tabuleiroDoJogador) {
        int x, y;
        Coordenadas cordenada = new Coordenadas(0,0);
        // Verifica todo o tabuleiro para encontrar células atingidas (valor 2)
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (tabuleiroDoJogador.getBoard()[j][i] == 2) {
                    // Checa todas as direções ao redor da célula atingida
                    if (j < 9 &&!posicaoJaAtacada(tabuleiroDoJogador, j + 1, i)) {
                        tabuleiroDoJogador.shoot(j + 1, i);
                        cordenada.setX(j + 1);
                        cordenada.setY(i);
                        return cordenada;
                    } 
                    if (j > 0 &&!posicaoJaAtacada(tabuleiroDoJogador, j - 1, i)) {
                        tabuleiroDoJogador.shoot(j - 1, i);
                        cordenada.setX(j - 1);
                        cordenada.setY(i);
                        return cordenada;
                    }
                    if (i < 9 &&!posicaoJaAtacada(tabuleiroDoJogador, j, i + 1)) {
                        tabuleiroDoJogador.shoot(j, i + 1);
                        cordenada.setX(j);
                        cordenada.setY(i+1);
                        return cordenada;
                    }
                    if (i > 0 &&!posicaoJaAtacada(tabuleiroDoJogador, j, i - 1)) {
                        tabuleiroDoJogador.shoot(j, i - 1);
                        cordenada.setX(j);
                        cordenada.setY(i-1);
                        return cordenada;
                    }
                }
            }
        }
        
        // Se nenhuma posição adjacente foi encontrada para atacar, escolhe uma posição aleatória
        do {
            x = random.nextInt(10);
            y = random.nextInt(10);
        } while (posicaoJaAtacada(tabuleiroDoJogador, x, y));

        tabuleiroDoJogador.shoot(x, y);
        cordenada.setX(x);
        cordenada.setY(y);
        return cordenada;
    }

    /**
     * Verifica se uma posicao já foi atacada no tabuleiro do jogador.
     *
     * @param tabuleiroDoJogador Tabuleiro do jogador.
     * @param x Coordenada X a ser verificada.
     * @param y Coordenada Y a ser verificada.
     * @return Verdadeiro se a posição já foi atacada, falso caso contrário.
     */
    private boolean posicaoJaAtacada(Tabuleiro tabuleiroDoJogador, int x, int y) {
        int[][] board = tabuleiroDoJogador.getBoard();
        return board[x][y] == 2 || board[x][y] == 3; // 2 represents a hit ship, 3 represents water
    }

    /**
     * Utiliza um radar para detectar a presença de navios inimigos nas coordenadas especificadas.
     * Retorna uma string indicando a localizacao dos navios detectados.
     *
     * @param x Coordenada X do radar.
     * @param y Coordenada Y do radar.
     * @return Informações sobre a detecção de navios inimigos.
     */
    
    public String radar(int x, int y) {
        String auxiliar = "";
        // Verificar a linha
        for (int i = 0; i < 10; i++) {
            if (tabuleiro.getBoard()[x][i] == 1) { // 1 represents a ship part 
                auxiliar = ("Navio encontrado na coluna " + (x + 1) + " ");
                break;
            }
        }
        
        // Verificar a coluna
        for (int j = 0; j < 10; j++) {
            if (tabuleiro.getBoard()[j][y] == 1) { // 1 represents a ship part
                auxiliar = (auxiliar + ("Navio encontrado na linha " + (y + 1)));
                break;
            }
        }
        if(auxiliar.equals("")) {
            return ("Navio não encontrado na coluna " + (x + 1) + " e navio não encontrado na linha " + (y + 1));
        }
        else {
            return auxiliar;
        }
	    }
	
	    public Tabuleiro getTabuleiro() {
	        return tabuleiro;
	    }
	
	    public void setTabuleiro(Tabuleiro tabuleiro) {
	        this.tabuleiro = tabuleiro;
	    }
  	} 
