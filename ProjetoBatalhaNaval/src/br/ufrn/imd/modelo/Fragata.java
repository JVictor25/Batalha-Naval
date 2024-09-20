package br.ufrn.imd.modelo;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Classe que representa uma fragata em um jogo de tabuleiro ou similar,
 * estendendo a classe abstrata Navio. Uma fragata tem um tamanho especifico
 * e pode ser posicionada horizontalmente ou verticalmente.
 *
 * @author JoaoVictor
 */
public class Fragata extends Navio {
    
    /**
     * Constroi uma fragata com caracteristicas especificadas.
     * 
     * @param horizontal Indica se a fragata sera posicionada horizontalmente.
     * @param direcao Indica a direcao do movimento da fragata (true para direita/baixo, false para esquerda/cima).
     * @param startX A posicao inicial X da fragata.
     * @param startY A posicao inicial Y da fragata.
     */
    public Fragata(boolean horizontal, boolean direcao, int startX, int startY) {
        super(4, horizontal, direcao, startX, startY); // Supondo que o tamanho da Fragata é 4
        criarLists();
    }

    /**
     * Sobrescreve o metodo abstrato criarLists() para definir as listas de imagens
     * especificas para a fragata, correspondentes aos diferentes estados visuais.
     */
    @Override
    public void criarLists() {


        	Image image11 = new Image("file:src/resources/imagens/Fragata11.png");
            ImageView imageView11 = new ImageView(image11);
            Image image21 = new Image("file:src/resources/imagens/Fragata21.png");
            ImageView imageView21 = new ImageView(image21);
            Image image31 = new Image("file:src/resources/imagens/Fragata31.png");
            ImageView imageView31 = new ImageView(image31);
            Image image41 = new Image("file:src/resources/imagens/Fragata41.png");
            ImageView imageView41 = new ImageView(image41);
            imagens1.add(imageView11);
            imagens1.add(imageView21);
            imagens1.add(imageView31);
            imagens1.add(imageView41);
            
            Image image12 = new Image("file:src/resources/imagens/Fragata12.png");
            ImageView imageView12 = new ImageView(image12);
            Image image22 = new Image("file:src/resources/imagens/Fragata22.png");
            ImageView imageView22 = new ImageView(image22);
            Image image32 = new Image("file:src/resources/imagens/Fragata32.png");
            ImageView imageView32 = new ImageView(image32);
            Image image42 = new Image("file:src/resources/imagens/Fragata42.png");
            ImageView imageView42 = new ImageView(image42);
            imagens2.add(imageView12);
            imagens2.add(imageView22);
            imagens2.add(imageView32);
            imagens2.add(imageView42);
            
            Image image13 = new Image("file:src/resources/imagens/Fragata13.png");
            ImageView imageView13 = new ImageView(image13);
            Image image23 = new Image("file:src/resources/imagens/Fragata23.png");
            ImageView imageView23 = new ImageView(image23);
            Image image33 = new Image("file:src/resources/imagens/Fragata33.png");
            ImageView imageView33 = new ImageView(image33);
            Image image43 = new Image("file:src/resources/imagens/Fragata43.png");
            ImageView imageView43 = new ImageView(image43);
            imagens3.add(imageView13);
            imagens3.add(imageView23);
            imagens3.add(imageView33);
            imagens3.add(imageView43);
            
            Image image14 = new Image("file:src/resources/imagens/Fragata14.png");
            ImageView imageView14 = new ImageView(image14);
            Image image24 = new Image("file:src/resources/imagens/Fragata24.png");
            ImageView imageView24 = new ImageView(image24);
            Image image34 = new Image("file:src/resources/imagens/Fragata34.png");
            ImageView imageView34 = new ImageView(image34);
            Image image44 = new Image("file:src/resources/imagens/Fragata44.png");
            ImageView imageView44 = new ImageView(image44);
            imagens4.add(imageView14);
            imagens4.add(imageView24);
            imagens4.add(imageView34);
            imagens4.add(imageView44);
            
        }
    }

