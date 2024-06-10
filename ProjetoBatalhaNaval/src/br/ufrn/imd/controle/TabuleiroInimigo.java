package br.ufrn.imd.controle;

import br.ufrn.imd.modelo.*;

import java.util.Random;

public class TabuleiroInimigo {
    private Tabuleiro tabuleiro;
    private Random random;

    public TabuleiroInimigo() {
        tabuleiro = new Tabuleiro();
        random = new Random();
        posicionarNavios();
    }

    private void posicionarNavios() {
        posicionarNavioAleatorio(new Corveta(false, random.nextBoolean(), random.nextInt(10), random.nextInt(10)));
        posicionarNavioAleatorio(new Submarino(false, random.nextBoolean(), random.nextInt(10), random.nextInt(10)));
        posicionarNavioAleatorio(new Fragata(false, random.nextBoolean(), random.nextInt(10), random.nextInt(10)));
        posicionarNavioAleatorio(new Destroyer(false, random.nextBoolean(), random.nextInt(10), random.nextInt(10)));
    }

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
            navioColocado = tabuleiro.placeShip(navio);
        } while (!navioColocado);
    }

    public void realizarAtaque(Tabuleiro tabuleiroDoJogador) {
        int x = random.nextInt(10);
        int y = random.nextInt(10);
        tabuleiroDoJogador.shoot(x, y);
    }
    public void imprimirTabuleiro() {
        tabuleiro.printTabuleiro();
    }

	public Tabuleiro getTabuleiro() {
		return tabuleiro;
	}

	public void setTabuleiro(Tabuleiro tabuleiro) {
		this.tabuleiro = tabuleiro;
	}
}