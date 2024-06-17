package br.ufrn.imd.modelo;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Destroyer extends Navio {
    public Destroyer(boolean horizontal, boolean direcao, int startX, int startY) {
        super(5, horizontal, direcao, startX, startY); // Supondo que o tamanho do Destroyer é 5
        criarLists();
    }

    @Override
    public void criarLists() {
        	Image image11 = new Image("file:src/resources/imagens/Destroyer11.png");
            ImageView imageView11 = new ImageView(image11);
            Image image21 = new Image("file:src/resources/imagens/Destroyer21.png");
            ImageView imageView21 = new ImageView(image21);
            Image image31 = new Image("file:src/resources/imagens/Destroyer31.png");
            ImageView imageView31 = new ImageView(image31);
            Image image41 = new Image("file:src/resources/imagens/Destroyer41.png");
            ImageView imageView41 = new ImageView(image41);
            Image image51 = new Image("file:src/resources/imagens/Destroyer51.png");
            ImageView imageView51 = new ImageView(image51);
            imagens1.add(imageView11);
            imagens1.add(imageView21);
            imagens1.add(imageView31);
            imagens1.add(imageView41);
            imagens1.add(imageView51);
            
            Image image12 = new Image("file:src/resources/imagens/Destroyer12.png");
            ImageView imageView12 = new ImageView(image12);
            Image image22 = new Image("file:src/resources/imagens/Destroyer22.png");
            ImageView imageView22 = new ImageView(image22);
            Image image32 = new Image("file:src/resources/imagens/Destroyer32.png");
            ImageView imageView32 = new ImageView(image32);
            Image image42 = new Image("file:src/resources/imagens/Destroyer42.png");
            ImageView imageView42 = new ImageView(image42);
            Image image52 = new Image("file:src/resources/imagens/Destroyer52.png");
            ImageView imageView52 = new ImageView(image52);
            imagens2.add(imageView12);
            imagens2.add(imageView22);
            imagens2.add(imageView32);
            imagens2.add(imageView42);
            imagens2.add(imageView52);
            
            Image image13 = new Image("file:src/resources/imagens/Destroyer13.png");
            ImageView imageView13 = new ImageView(image13);
            Image image23 = new Image("file:src/resources/imagens/Destroyer23.png");
            ImageView imageView23 = new ImageView(image23);
            Image image33 = new Image("file:src/resources/imagens/Destroyer33.png");
            ImageView imageView33 = new ImageView(image33);
            Image image43 = new Image("file:src/resources/imagens/Destroyer43.png");
            ImageView imageView43 = new ImageView(image43);
            Image image53 = new Image("file:src/resources/imagens/Destroyer53.png");
            ImageView imageView53 = new ImageView(image53);
            imagens3.add(imageView13);
            imagens3.add(imageView23);
            imagens3.add(imageView33);
            imagens3.add(imageView43);
            imagens3.add(imageView53);
            
            Image image14 = new Image("file:src/resources/imagens/Destroyer14.png");
            ImageView imageView14 = new ImageView(image14);
            Image image24 = new Image("file:src/resources/imagens/Destroyer24.png");
            ImageView imageView24 = new ImageView(image24);
            Image image34 = new Image("file:src/resources/imagens/Destroyer34.png");
            ImageView imageView34 = new ImageView(image34);
            Image image44 = new Image("file:src/resources/imagens/Destroyer44.png");
            ImageView imageView44 = new ImageView(image44);
            Image image54 = new Image("file:src/resources/imagens/Destroyer54.png");
            ImageView imageView54 = new ImageView(image54);
            imagens4.add(imageView14);
            imagens4.add(imageView24);
            imagens4.add(imageView34);
            imagens4.add(imageView44);
            imagens4.add(imageView54);
    }
    
}