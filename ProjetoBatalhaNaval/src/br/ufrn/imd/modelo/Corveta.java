package br.ufrn.imd.modelo;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Corveta extends Navio {
    
    public Corveta(boolean horizontal, boolean direcao, int startX, int startY) {
        super(2, horizontal, direcao, startX, startY); // Supondo que o tamanho da Corveta é 2
        criarLists();
    }

    @Override
    public void criarLists() {

        	Image image11 = new Image("file:src/resources/imagens/Corveta11.png");
            ImageView imageView11 = new ImageView(image11);
            Image image21 = new Image("file:src/resources/imagens/Corveta21.png");
            ImageView imageView21 = new ImageView(image21);
            imagens1.add(imageView11);
            imagens1.add(imageView21);
            
            Image image12 = new Image("file:src/resources/imagens/Corveta12.png");
            ImageView imageView12 = new ImageView(image12);
            Image image22 = new Image("file:src/resources/imagens/Corveta22.png");
            ImageView imageView22 = new ImageView(image22);
            imagens2.add(imageView12);
            imagens2.add(imageView22);
            
            Image image13 = new Image("file:src/resources/imagens/Corveta13.png");
            ImageView imageView13 = new ImageView(image13);
            Image image23 = new Image("file:src/resources/imagens/Corveta23.png");
            ImageView imageView23 = new ImageView(image23);
            imagens3.add(imageView13);
            imagens3.add(imageView23);
            
            Image image14 = new Image("file:src/resources/imagens/Corveta14.png");
            ImageView imageView14 = new ImageView(image14);
            Image image24 = new Image("file:src/resources/imagens/Corveta24.png");
            ImageView imageView24 = new ImageView(image24);
            imagens4.add(imageView14);
            imagens4.add(imageView24);
        }
}
