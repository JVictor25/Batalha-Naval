package br.ufrn.imd.modelo;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;


public class Tabuleiro {
    private static final int SIZE = 10;
    private int[][] board;
    private List<Navio> navios;

    public Tabuleiro() {
        board = new int[SIZE][SIZE];
        navios = new ArrayList<>();
        // Inicializa o tabuleiro com valores 0 (água)
        for (int i = 0; i < SIZE; i++) {
            Arrays.fill(board[i], 0);
        }
    }

    public boolean placeShip(Navio navio) {
    	boolean auxiliar=true;
    	   for (Coordenadas coordenada : navio.getCoordenadas()) {
               int x = coordenada.getX();
               int y = coordenada.getY();
               if (!dentroDoTabuleiro(x, y) || board[x][y] == 1) {
            	   auxiliar=false;
            	   System.out.println("Posição do " + navio.getClass().getSimpleName() + "inválida ou já ocupada nas coordenadas: (" + x + ", " + y + ")");
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
    	        navios.add(navio);
    	    }
      return auxiliar;
    }

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
    public boolean dentroDoTabuleiro(int x, int y) {
        return x >= 0 && x < SIZE && y >= 0 && y < SIZE;
    }

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
    
    public boolean todosNaviosAfundados() {
        for (Navio navio : navios) {
            if (!navio.getAfundado()) {
                return false;
            }
        }
        return true;
    }
    
    public void printTabuleiro() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                System.out.print(board[j][i] + " ");
            }
            System.out.println();
        }
    }

	public int[][] getBoard() {
		return board;
	}

	public void setBoard(int[][] board) {
		this.board = board;
	}
}