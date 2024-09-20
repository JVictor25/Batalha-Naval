package br.ufrn.imd.modelo;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Classe que representa um submarino em um jogo de tabuleiro ou similar,
 * estendendo a classe abstrata Navio. Um submarino tem um tamanho especifico
 * e pode ser posicionado horizontalmente ou verticalmente.
 *
 * @author JoaoVictor
 */
public class Submarino extends Navio {
    
    /**
     * Constroi um submarino com caracteristicas especificadas.
     * 
     * @param horizontal Indica se o submarino será posicionado horizontalmente.
     * @param direcao Indica a direcao do movimento do submarino (true para direita/baixo, false para esquerda/cima).
     * @param startX A posicao inicial X do submarino.
     * @param startY A posicao inicial Y do submarino.
     */
    public Submarino(boolean horizontal, boolean direcao, int startX, int startY) {
        super(3, horizontal, direcao, startX, startY); // Supondo que o tamanho do Submarino é 3
        criarLists();
    }

    /**
     * Sobrescreve o metodo abstrato criarLists() para definir as listas de imagens
     * especificas para o submarino, correspondentes aos diferentes estados visuais.
     */
    @Override
    public void criarLists() {
        	Image image11 = new Image("file:src/resources/imagens/Submarino11.png");
            ImageView imageView11 = new ImageView(image11);
            Image image21 = new Image("file:src/resources/imagens/Submarino21.png");
            ImageView imageView21 = new ImageView(image21);
            Image image31 = new Image("file:src/resources/imagens/Submarino31.png");
            ImageView imageView31 = new ImageView(image31);
            imagens1.add(imageView11);
            imagens1.add(imageView21);
            imagens1.add(imageView31);
            
            Image image12 = new Image("file:src/resources/imagens/Submarino12.png");
            ImageView imageView12 = new ImageView(image12);
            Image image22 = new Image("file:src/resources/imagens/Submarino22.png");
            ImageView imageView22 = new ImageView(image22);
            Image image32 = new Image("file:src/resources/imagens/Submarino32.png");
            ImageView imageView32 = new ImageView(image32);
            imagens2.add(imageView12);
            imagens2.add(imageView22);
            imagens2.add(imageView32);
            
            Image image13 = new Image("file:src/resources/imagens/Submarino13.png");
            ImageView imageView13 = new ImageView(image13);
            Image image23 = new Image("file:src/resources/imagens/Submarino23.png");
            ImageView imageView23 = new ImageView(image23);
            Image image33 = new Image("file:src/resources/imagens/Submarino33.png");
            ImageView imageView33 = new ImageView(image33);
            imagens3.add(imageView13);
            imagens3.add(imageView23);
            imagens3.add(imageView33);
            
            Image image14 = new Image("file:src/resources/imagens/Submarino14.png");
            ImageView imageView14 = new ImageView(image14);
            Image image24 = new Image("file:src/resources/imagens/Submarino24.png");
            ImageView imageView24 = new ImageView(image24);
            Image image34 = new Image("file:src/resources/imagens/Submarino34.png");
            ImageView imageView34 = new ImageView(image34);
            imagens4.add(imageView14);
            imagens4.add(imageView24);
            imagens4.add(imageView34);
        }
}