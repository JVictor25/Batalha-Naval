package br.ufrn.imd.controle;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.Node;
import br.ufrn.imd.modelo.Coordenadas;
import br.ufrn.imd.modelo.Corveta;
import br.ufrn.imd.modelo.Destroyer;
import br.ufrn.imd.modelo.Fragata;
import br.ufrn.imd.modelo.Navio;
import br.ufrn.imd.modelo.Submarino;
import br.ufrn.imd.modelo.Tabuleiro;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class BatalhaNavalController implements Initializable {

    @FXML
    private GridPane gridPaneInimigo;

    @FXML
    private GridPane gridPaneJogador;
    @FXML
    private ImageView imageViewRadar;
    @FXML
    private GridPane gridPaneNavios;
    @FXML
    private GridPane gridPaneRadar;
    
    @FXML
    private Button iniciarJogoButton;
    
    @FXML
    private TextArea terminalTextArea;
    
    @FXML
    private Label labelUsosRadar;
    
    @FXML
    private Button reiniciarJogoButton;

    private final int SIZE = 10; // Tamanho do tabuleiro
    private TabuleiroInimigo tabuleiroInimigo;
    private Tabuleiro tabuleiroJogador;
    private int navioIndex;
    private boolean todosNaviosPosicionados;
    private boolean jogoIniciado;
    private boolean jogoAcabado;
    private boolean usandoRadar;
    private int usosRadar;
    private Corveta corveta;
    private Submarino submarino;
    private Fragata fragata;
    private Destroyer destroyer;
    private Navio navioClicado;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	tabuleiroInimigo = new TabuleiroInimigo(); // Inicializa o TabuleiroInimigo
        tabuleiroJogador = new Tabuleiro(); // Inicializa o TabuleiroJogador
        navioIndex = 0;
        usosRadar = 3;
        updateRadarLabel();
        todosNaviosPosicionados = false;
        jogoIniciado = false;
        jogoAcabado = false;
        usandoRadar = false;
        
        criarNavio();
        criarRadar();
        iniciarJogoButton.setOnAction(event -> iniciarJogo());
        reiniciarJogoButton.setOnAction(event -> reiniciarJogo());
       
        terminalTextArea.setText(""); // Inicializa o terminal vazio
        
        Image marImage = new Image("file:src/resources/imagens/Mar.png");
        // Criar o tabuleiro do inimigo com ImageViews
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                ImageView imageView = new ImageView(marImage);
                imageView.setFitWidth(30);
                imageView.setFitHeight(30);
                imageView.setPreserveRatio(false);
                imageView.setSmooth(true);
                imageView.setCache(true);

                int x = j;
                int y = i;
                imageView.setOnMouseClicked(event -> atirar(x, y, imageView)); // Adiciona evento de clique
                gridPaneInimigo.add(imageView, j, i);
            }
        }

        // Criar o tabuleiro do jogador com ImageViews
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                ImageView imageView = new ImageView(marImage);
                imageView.setFitWidth(30);
                imageView.setFitHeight(30);
                imageView.setPreserveRatio(false);
                imageView.setSmooth(true);
                imageView.setCache(true);

                int x = j;
                int y = i;
                imageView.setOnMouseClicked(event -> posicionarNavio(x, y, navioClicado)); // Adiciona evento de clique
                gridPaneJogador.add(imageView, j, i);
            }
        }
        appendToTerminal("Bem-vindo ao Batalha Naval!");
        appendToTerminal("Posicione seus navios para iniciar o jogo.");
    }
    
    private void atirar(int x, int y, ImageView imageView) {
        if (!jogoIniciado) {
        	appendToTerminal("Posicione todos os navios e clique em Jogar para iniciar o jogo!");
            return; // Não permite tiros até que todos os navios estejam posicionados e o jogo tenha iniciado
        }
        if(usandoRadar&&usosRadar>0) {
        	appendToTerminal(tabuleiroInimigo.radar(x, y));
        	usandoRadar=false;
        	usosRadar--;
        	updateRadarLabel();
        	return;
        }
        else if(jogoAcabado){
            return; // Não permite tiros depois que o jogo acabou
        }
        tabuleiroInimigo.getTabuleiro().shoot(x, y); // Atira no tabuleiro do inimigo

        // Carrega as imagens para os diferentes resultados do tiro
        Image hitImage = new Image("file:src/resources/imagens/Explosao2.png");
        Image missImage = new Image("file:src/resources/imagens/MarAcertado.png");

        // Atualiza a imagem do ImageView com base no resultado do tiro
        if (tabuleiroInimigo.getTabuleiro().getBoard()[x][y] == 2) {
            imageView.setImage(hitImage); // Navio atingido, muda para a imagem de navio atingido
            appendToTerminal("Você atirou em (" + x + ", " + y+ "): Acertou um Navio inimigo");
        } else if (tabuleiroInimigo.getTabuleiro().getBoard()[x][y] == 3) {
            imageView.setImage(missImage); // Água atingida, muda para a imagem de água atingida
            appendToTerminal("Você atirou em (" + x + ", " + y+ "): Acertou o Mar");
        }

        imageView.setDisable(true); // Desativa o ImageView para evitar múltiplos tiros no mesmo lugar

        // Chama a função de ataque do tabuleiro inimigo
        realizarAtaqueInimigo();
        jogoAcabou();
    }
    
    private void updateRadarLabel() {
        labelUsosRadar.setText("Usos restantes: " + usosRadar);
    }
    private void criarRadar() {
    	Image radarImage1 = new Image("file:src/resources/imagens/Radar1.png");
        ImageView imageViewRadar1 = new ImageView(radarImage1);
        imageViewRadar1.setOnMouseClicked(event -> usaRadar());
        gridPaneRadar.add(imageViewRadar1, 0, 0);
        
        Image radarImage2 = new Image("file:src/resources/imagens/Radar2.png");
        ImageView imageViewRadar2 = new ImageView(radarImage2);
        imageViewRadar2.setOnMouseClicked(event -> usaRadar());
        gridPaneRadar.add(imageViewRadar2, 0, 1);
        
        Image radarImage3 = new Image("file:src/resources/imagens/Radar3.png");
        ImageView imageViewRadar3 = new ImageView(radarImage3);
        imageViewRadar3.setOnMouseClicked(event -> usaRadar());
        gridPaneRadar.add(imageViewRadar3, 0, 2);
    }
    
    private void usaRadar() {
    	usandoRadar=true;
    }
    
    private void iniciarJogo() {
    	if (navioIndex >= 4) {

            todosNaviosPosicionados = true; // Todos os navios foram posicionados
        }
        if (todosNaviosPosicionados) {
            jogoIniciado = true;
            iniciarJogoButton.setDisable(true); // Desativa o botão após o início do jogo
        }
    }

    private void jogoAcabou() {
        // Verificar condição de vitória do jogador
        if (tabuleiroInimigo.getTabuleiro().todosNaviosAfundados()) {
        	 appendToTerminal("Parabéns! Você afundou todos os navios inimigos e venceu o jogo!");
            jogoAcabado=true;
        }
        // Verificar condição de derrota do jogador
        else if (tabuleiroJogador.todosNaviosAfundados()) {
        	 appendToTerminal("Você perdeu! Todos os seus navios foram afundados.");
            jogoAcabado=true;
        }
    }

    private void reiniciarJogo() {
        // Limpar os tabuleiros
        gridPaneInimigo.getChildren().clear();
        gridPaneJogador.getChildren().clear();
        iniciarJogoButton.setDisable(false);

        // Reinicializar os tabuleiros chamando o método initialize
        initialize(null, null);
    }

    // Método para realizar o ataque do inimigo
    private void realizarAtaqueInimigo() {
        Coordenadas cordenada = tabuleiroInimigo.realizarAtaque(tabuleiroJogador);
        if (tabuleiroJogador.getBoard()[cordenada.getX()][cordenada.getY()] == 2) {
        	appendToTerminal("Bot atirou em (" +cordenada.getX() + ", " + cordenada.getY()+ "): Acertou um Navio aliado");
        } else if (tabuleiroJogador.getBoard()[cordenada.getX()][cordenada.getY()] == 3) {
        	appendToTerminal("Bot atirou em (" +cordenada.getX() + ", " + cordenada.getY()+ "): Acertou o Mar");
        }// Ataca o tabuleiro do jogador
        // Atualiza as imagens do tabuleiro do jogador de acordo com o resultado do ataque
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                ImageView imageView = (ImageView) getNodeByRowColumnIndex(i, j, gridPaneJogador);
                if (imageView != null) {
                    if (tabuleiroJogador.getBoard()[j][i] == 2) {
                    	 Image hitImage = new Image("file:src/resources/imagens/Explosao2.png");
                        imageView.setImage(hitImage); // Navio atingido, muda para imagem de navio atingido
                    } else if (tabuleiroJogador.getBoard()[j][i] == 3) {
                        Image missImage = new Image("file:src/resources/imagens/MarAcertado.png");
                        imageView.setImage(missImage); // Água atingida, muda para imagem de água atingida
                    }
                }
            }
        }
    }
    
    private void selecionarNavio(int x, ImageView image) {
    	if(x==1||x==2) {
    		this.navioClicado=this.corveta;
    	}else if(x==4||x==5||x==6) {
    		this.navioClicado=this.submarino;
    	}else if(x==8||x==9||x==10||x==11) {
    		this.navioClicado=this.fragata;
    	}else if(x==13||x==14||x==15||x==16||x==17) {
    		this.navioClicado=this.destroyer;
    	}
    	
    }
    private void appendToTerminal(String message) {
        terminalTextArea.appendText(message + "\n");
    }
    
    private void posicionarNavio(int x, int y, Navio navio) {
        if (jogoIniciado) {
            return; // Não permite posicionar mais navios se todos já foram posicionados
        }
        if (navio != null) {
       	 System.out.println("1");
        	tabuleiroJogador.limparPosicaoNavio(navio);
            navio.setStartX(x);
            navio.setStartY(y);
            if (tabuleiroJogador.placeShip(navio)) {
           	 System.out.println("4");
                if (navio.isHorizontal() && navio.isDirecao()) {
               	 posicionarImagensNavio(navio, 1);
               } else if (!navio.isHorizontal() && !navio.isDirecao()) {
               	 posicionarImagensNavio(navio, 2);
               } else if (navio.isHorizontal() && !navio.isDirecao()) {
               	 posicionarImagensNavio(navio, 3);
               } else if (!navio.isHorizontal() && navio.isDirecao()) {
               	 posicionarImagensNavio(navio, 4);
               }
               }
           }
	        navioIndex++;
	        this.navioClicado=null;
	        atualizarTabuleiroJogador();     
    }
    
    // Método para criar navios em sequência
    private void criarNavio() {
    	   
           this.corveta= new Corveta(true, true,10, 0);
           this.submarino= new Submarino(true, true,10, 0);
           this.fragata= new Fragata(true, true,10, 0);
           this.destroyer= new Destroyer(true, true,10, 0);
           
           Image image1 = new Image("file:src/resources/imagens/Corveta1.png");
           ImageView imageView1 = new ImageView(image1);
           Image image2 = new Image("file:src/resources/imagens/Corveta2.png");
           ImageView imageView2 = new ImageView(image2);
           imageView1.setOnMouseClicked(event -> selecionarNavio(1, imageView1));
           gridPaneNavios.add(imageView1, 1, 1);
           imageView2.setOnMouseClicked(event -> selecionarNavio(2, imageView2));
           gridPaneNavios.add(imageView2, 2, 1);
           
           Image image4 = new Image("file:src/resources/imagens/Submarino1.png");
           ImageView imageView4 = new ImageView(image4);
           Image image5 = new Image("file:src/resources/imagens/Submarino2.png");
           ImageView imageView5 = new ImageView(image5);
           Image image6 = new Image("file:src/resources/imagens/Submarino3.png");
           ImageView imageView6 = new ImageView(image6);
           imageView4.setOnMouseClicked(event -> selecionarNavio(4, imageView4));
           gridPaneNavios.add(imageView4, 4, 1);
           imageView5.setOnMouseClicked(event -> selecionarNavio(5, imageView5));
           gridPaneNavios.add(imageView5, 5, 1);
           imageView6.setOnMouseClicked(event -> selecionarNavio(6, imageView6));
           gridPaneNavios.add(imageView6, 6, 1);
           
           Image image8 = new Image("file:src/resources/imagens/Fragata1.png");
           ImageView imageView8 = new ImageView(image8);
           Image image9 = new Image("file:src/resources/imagens/Fragata2.png");
           ImageView imageView9 = new ImageView(image9);
           Image image10 = new Image("file:src/resources/imagens/Fragata3.png");
           ImageView imageView10 = new ImageView(image10);
           Image image11 = new Image("file:src/resources/imagens/Fragata4.png");
           ImageView imageView11 = new ImageView(image11);
           imageView8.setOnMouseClicked(event -> selecionarNavio(8, imageView8));
           gridPaneNavios.add(imageView8, 8, 1);
           imageView9.setOnMouseClicked(event -> selecionarNavio(9, imageView9));
           gridPaneNavios.add(imageView9, 9, 1);
           imageView10.setOnMouseClicked(event -> selecionarNavio(10, imageView10));
           gridPaneNavios.add(imageView10, 10, 1);
           imageView11.setOnMouseClicked(event -> selecionarNavio(11, imageView11));
           gridPaneNavios.add(imageView11, 11, 1);
           
           Image image13 = new Image("file:src/resources/imagens/Destroyer1.png");
           ImageView imageView13 = new ImageView(image13);
           Image image14 = new Image("file:src/resources/imagens/Destroyer2.png");
           ImageView imageView14 = new ImageView(image14);
           Image image15 = new Image("file:src/resources/imagens/Destroyer3.png");
           ImageView imageView15 = new ImageView(image15);
           Image image16 = new Image("file:src/resources/imagens/Destroyer4.png");
           ImageView imageView16 = new ImageView(image16);
           Image image17 = new Image("file:src/resources/imagens/Destroyer5.png");
           ImageView imageView17 = new ImageView(image17);
           imageView13.setOnMouseClicked(event -> selecionarNavio(13, imageView13));
           gridPaneNavios.add(imageView13, 13, 1);
           imageView14.setOnMouseClicked(event -> selecionarNavio(14, imageView14));
           gridPaneNavios.add(imageView14, 14, 1);
           imageView15.setOnMouseClicked(event -> selecionarNavio(15, imageView15));
           gridPaneNavios.add(imageView15, 15, 1);
           imageView16.setOnMouseClicked(event -> selecionarNavio(16, imageView16));
           gridPaneNavios.add(imageView16, 16, 1);
           imageView17.setOnMouseClicked(event -> selecionarNavio(17, imageView17));
           gridPaneNavios.add(imageView17, 17, 1);
             
     }
    
    // Método para obter o navio pelas coordenadas
    private Navio getNavioPorCoordenadas(int x, int y) {
        for (Navio navio : tabuleiroJogador.getNavios()) {
            for (Coordenadas coordenada : navio.getCoordenadas()) {
                if (coordenada.getX() == x && coordenada.getY() == y) {
                    return navio;
                }
            }
        }
        return null;
    }

    
    // Método para girar navio
    private void girarNavio(int x, int y, ImageView imageView) {
        if (!jogoIniciado) {
            Navio navio = getNavioPorCoordenadas(x, y);
            // Limpa as posições atuais do navio no tabuleiro
            tabuleiroJogador.limparPosicaoNavio(navio);
            int pos=0;
            boolean rotated = false;
            do {
                if (navio.isHorizontal() && navio.isDirecao()) {
                	pos=2;
                    navio.setHorizontal(false);
                    navio.setDirecao(false);
                } else if (!navio.isHorizontal() && !navio.isDirecao()) {
                	pos=3;
                    navio.setHorizontal(true);
                    navio.setDirecao(false);
                } else if (navio.isHorizontal() && !navio.isDirecao()) {
                	pos=4;
                    navio.setHorizontal(false);
                    navio.setDirecao(true);
                } else if (!navio.isHorizontal() && navio.isDirecao()) {
                	pos=1;
                    navio.setHorizontal(true);
                    navio.setDirecao(true);
                }
                rotated = tabuleiroJogador.replaceShip(navio);
                if (!rotated) {
                    System.out.println("Não foi possível rotacionar o navio.");
                }
            } while (!rotated);
            posicionarImagensNavio(navio, pos);
            atualizarTabuleiroJogador(); // Atualiza o tabuleiro do jogador
        }
    }

    private ImageView getNodeByRowColumnIndex(final int row, final int column, GridPane gridPane) {
    for (Node node : gridPane.getChildren()) {
        Integer nodeRow = GridPane.getRowIndex(node);
        Integer nodeColumn = GridPane.getColumnIndex(node);
        
        // Use valores padrão caso os índices sejam nulos
        int nodeRowIndex = (nodeRow == null) ? 0 : nodeRow;
        int nodeColumnIndex = (nodeColumn == null) ? 0 : nodeColumn;

        if (nodeRowIndex == row && nodeColumnIndex == column && node instanceof ImageView) {
            return (ImageView) node;
        }
    }
    return null;
}
    
    private void posicionarImagensNavio(Navio navio, int pos) {
        int index = 0;
        if(pos==1) {
        for (Coordenadas coordenada : navio.getCoordenadas()) {
            int coordX = coordenada.getX();
            int coordY = coordenada.getY();
            ImageView imageView = getNodeByRowColumnIndex(coordY, coordX, gridPaneJogador);
            if (imageView != null) {
                imageView.setImage(navio.getImagens1().get(index).getImage());
                index++;
            }
        }
      } else if(pos==2) {
            for (Coordenadas coordenada : navio.getCoordenadas()) {
                int coordX = coordenada.getX();
                int coordY = coordenada.getY();
                ImageView imageView = getNodeByRowColumnIndex(coordY, coordX, gridPaneJogador);
                if (imageView != null) {
                    imageView.setImage(navio.getImagens2().get(index).getImage());
                    index++;
                }
            }
          } else if(pos==3) {
              for (Coordenadas coordenada : navio.getCoordenadas()) {
                  int coordX = coordenada.getX();
                  int coordY = coordenada.getY();
                  ImageView imageView = getNodeByRowColumnIndex(coordY, coordX, gridPaneJogador);
                  if (imageView != null) {
                      imageView.setImage(navio.getImagens3().get(index).getImage());
                      index++;
                  }
              }
            } else if(pos==4) {
                for (Coordenadas coordenada : navio.getCoordenadas()) {
                    int coordX = coordenada.getX();
                    int coordY = coordenada.getY();
                    ImageView imageView = getNodeByRowColumnIndex(coordY, coordX, gridPaneJogador);
                    if (imageView != null) {
                        imageView.setImage(navio.getImagens4().get(index).getImage());
                        index++;
                    }
                }
              }
    }

    // Método para atualizar o tabuleiro do jogador
    private void atualizarTabuleiroJogador() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                ImageView imageView = (ImageView) getNodeByRowColumnIndex(i, j, gridPaneJogador);
                if (imageView != null) {
                    final int x = i;
                    final int y = j;
                    int cellState = tabuleiroJogador.getBoard()[j][i];
                    if (cellState == 2) {
                    	Image hitImage = new Image("file:src/resources/imagens/Explosao2.png");
                        imageView.setImage(hitImage); // Navio atingido, muda para imagem de navio atingido
                    } else if (cellState == 3) {
                        Image missImage = new Image("file:src/resources/imagens/MarAcertado.png");
                        imageView.setImage(missImage); // Água atingida, muda para imagem de água atingida
                    } else if (cellState == 1) {
                        imageView.setOnMouseClicked(event -> girarNavio(y, x, imageView)); // Adiciona evento de clique
                        //imageView.setOnMousePressed(this::handleMousePressed); // Adiciona evento de mouse pressed
                        //imageView.setOnMouseReleased(this::handleMouseReleased); // Adiciona evento de mouse released
                    } else if (cellState == 0) {
                        Image waterImage = new Image("file:src/resources/imagens/Mar.png");
                        imageView.setImage(waterImage); // Água
                        imageView.setOnMouseClicked(event -> posicionarNavio(y, x, navioClicado)); // Adiciona evento de clique
                    }
                }
            }
        }
    }

   
   /*private void handleMousePressed(MouseEvent event) {
    	 System.out.println("prendendo");
        if (event.getButton() == MouseButton.PRIMARY) { // Verifica se o botão pressionado é o principal (esquerdo)
        	System.out.println("prendendo2");
            // Obtenha a referência do retângulo clicado
            Rectangle clickedRectangle = (Rectangle) event.getSource();
            
            // Obtenha a coluna e linha na GridPane do retângulo clicado
            Integer colIndex = GridPane.getColumnIndex(clickedRectangle);
            Integer rowIndex = GridPane.getRowIndex(clickedRectangle);
            
            // Certifique-se de que os índices não sejam nulos
            if (colIndex != null && rowIndex != null) {
            	System.out.println("prendendo3");
                int col = colIndex.intValue();
                int row = rowIndex.intValue();
                System.out.println("prendendo3:" +"(" + col + ","+ row +")");
                
                // Encontre o navio associado às coordenadas da grade
                navioClicado = getNavioPorCoordenadas(col, row);
            }
        }
    }

	private Coordenadas calcularCoordenadaGrid(double coordenadaMouseX, double coordenadaMouseY, GridPane gridPane) {
	    // Tamanho do gridPane (largura e altura)
	    double larguraGrid = 310;
	    double alturaGrid = 310;
	    
	    // Número de linhas e colunas no gridPane
	    int linhas = SIZE; // Assumindo que SIZE é o número de linhas e colunas do gridPane
	    int colunas = SIZE;
	    
	    // Tamanho da célula no gridPane (considerando 30x30 pixels como no seu exemplo)
	    double tamanhoCelulaX = larguraGrid / colunas;
	    double tamanhoCelulaY = alturaGrid / linhas;
	    
	    // Calcula o índice da grade com base na coordenada do mouse
	    int indiceX = (int) (coordenadaMouseX / tamanhoCelulaX);
	    int indiceY = (int) (coordenadaMouseY / tamanhoCelulaY);
	    
	    // Imprime os nomes e valores das variáveis
	    System.out.println("Variáveis calculadas:");
	    System.out.println("coordenadaMouseX: " + coordenadaMouseX);
	    System.out.println("coordenadaMouseY: " + coordenadaMouseY);
	    System.out.println("larguraGrid: " + larguraGrid);
	    System.out.println("alturaGrid: " + alturaGrid);
	    System.out.println("linhas: " + linhas);
	    System.out.println("colunas: " + colunas);
	    System.out.println("tamanhoCelulaX: " + tamanhoCelulaX);
	    System.out.println("tamanhoCelulaY: " + tamanhoCelulaY);
	    System.out.println("indiceX: " + indiceX);
	    System.out.println("indiceY: " + indiceY);
	    
	    // Retorna os índices da grade como um objeto Coordenada
	    return new Coordenadas(indiceX, indiceY);
	}
	
	private void handleMouseReleased(MouseEvent event) {
		 System.out.println("soltando");
	    if (navioClicado != null) {
	    	System.out.println("soltando2");
	        // Coordenadas atuais do navio
	        int currentX = navioClicado.getStartX();
	        int currentY = navioClicado.getStartY();
	
	        // Calcula as novas coordenadas do navio com base no mouse solto
	        Coordenadas novaCoordenada = calcularCoordenadaGrid(event.getX(), event.getY(), gridPaneJogador);
	        int newX = novaCoordenada.getX();
	        int newY = novaCoordenada.getY();
	        System.out.println("replaceando: "+"("+currentX+","+currentY+") "+"("+newX+","+newY+")");
	        // Verifica se a nova posição é válida (água)
	        if (tabuleiroJogador.getBoard()[newY][newX] == 0) {
	        	System.out.println("soltando3");
	            // Remove o navio temporariamente para permitir o movimento
	            tabuleiroJogador.limparPosicaoNavio(navioClicado);
	
	            // Atualiza as coordenadas do navio
	            navioClicado.setStartX(newX);
	            navioClicado.setStartY(newY);
	            
	            // Reposiciona o navio na nova posição
	            boolean navioMovido = tabuleiroJogador.placeShip(navioClicado);
	
	            if (!navioMovido) {
	                // Se não for possível mover o navio, restaura para a posição inicial
	            	navioClicado.setStartX(currentX);
	                navioClicado.setStartY(currentY);
	                tabuleiroJogador.placeShip(navioClicado);
	            }
	
	            // Atualiza o tabuleiro após a mudança no navio
	            atualizarTabuleiroJogador();
	        } else {
	            // Se a nova posição não for válida, reposiciona o navio na posição inicial
	        	navioClicado.setStartX(currentX);
	        	navioClicado.setStartY(currentY);
	            tabuleiroJogador.placeShip(navioClicado);
	        }
	
	        // Limpa a referência ao navio selecionado após o movimento
	        navioClicado = null;
	    }
	}*/
	}

