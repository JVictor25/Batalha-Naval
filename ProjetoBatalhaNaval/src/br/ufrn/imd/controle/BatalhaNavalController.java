/**
 * Controlador para a interface gráfica da Batalha Naval.
 * Este controlador lida com a lógica do jogo, incluindo a inicialização dos componentes,
 * manipulação de eventos de usuário, e atualizações visuais do estado do jogo.
 *
 * @author Seu Nome
 */
package br.ufrn.imd.controle;


import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.Node;
import br.ufrn.imd.dao.Tabuleiro;
import br.ufrn.imd.dao.TabuleiroInimigo;
import br.ufrn.imd.modelo.Coordenadas;
import br.ufrn.imd.modelo.Corveta;
import br.ufrn.imd.modelo.Destroyer;
import br.ufrn.imd.modelo.Fragata;
import br.ufrn.imd.modelo.Navio;
import br.ufrn.imd.modelo.Submarino;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;


public class BatalhaNavalController implements Initializable {

    @FXML
    private GridPane gridPaneInimigo;

    @FXML
    private GridPane gridPaneJogador;
    
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
    
    private final int SIZE = 10;
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
    

    /**
     * Construtor ou inicializador da classe.
     * Inicializa o controlador e configura o ambiente do jogo.
     *
     * @param location URL do recurso localizado
     * @param resources Recursos locais
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	tabuleiroInimigo = new TabuleiroInimigo();
        tabuleiroJogador = new Tabuleiro(); 
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
        terminalTextArea.setText(""); 
        
        Image marImage = new Image("file:src/resources/imagens/Mar2.png");
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
                imageView.setOnMouseClicked(event -> atirar(x, y, imageView));
                gridPaneInimigo.add(imageView, j, i);
            }
        }
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
                imageView.setOnMouseClicked(event -> posicionarNavio(x, y, navioClicado)); 
                gridPaneJogador.add(imageView, j, i);
            }
        }
        appendToTerminal("Bem-vindo ao Batalha Naval!");
        appendToTerminal("Posicione seus navios para iniciar o jogo.");
 

    }
       
    /**
     * Realiza um tiro no tabuleiro do inimigo nas coordenadas especificadas.
     * Este metodo verifica se o jogo foi iniciado, se uma celula foi selecionada para mirar,
     * e então realiza o tiro. Depois de atirar, atualiza o estado do jogo e o tabuleiro visualmente.
     *
     * @param x A coordenada X onde o tiro sera disparado.
     * @param y A coordenada Y onde o tiro sera disparado.
     * @param imageView O ImageView usado para visualizar o tiro.
     */
    private void atirar(int x, int y, ImageView imageView) {
        if (!jogoIniciado) {
        	appendToTerminal("Posicione todos os navios e clique em Jogar para iniciar o jogo!");
            return; 
        }
        if(usandoRadar&&usosRadar>0) {
        	appendToTerminal(tabuleiroInimigo.radar(x, y));
        	usandoRadar=false;
        	usosRadar--;
        	updateRadarLabel();
        	return;
        }
        else if(jogoAcabado){
            return;
        }
        tabuleiroInimigo.getTabuleiro().shoot(x, y); 

        Image hitImage = new Image("file:src/resources/imagens/Explosao2.png");
        Image missImage = new Image("file:src/resources/imagens/MarAcertado.png");

        if (tabuleiroInimigo.getTabuleiro().getBoard()[x][y] == 2) {
        	ImageView imageViewhit = new ImageView(hitImage);
        	gridPaneInimigo.add(imageViewhit, x, y);
            appendToTerminal("Você atirou em (" + x + ", " + y+ "): Acertou um Navio inimigo");
        } else if (tabuleiroInimigo.getTabuleiro().getBoard()[x][y] == 3) {
        	ImageView imageViewmiss = new ImageView(missImage);
        	gridPaneInimigo.add(imageViewmiss, x, y);
            appendToTerminal("Você atirou em (" + x + ", " + y+ "): Acertou o Mar");
        }

        imageView.setDisable(true); 

        realizarAtaqueInimigo();
        jogoAcabou();
    }
    
    /**
     * Simula o ataque do inimigo contra o tabuleiro do jogador.
     * Este metodo calcula a proxima posicao de ataque do inimigo, atualiza o terminal com a posicao atacada,
     * e verifica se o ataque acertou um navio ou mar. Em seguida, atualiza visualmente o tabuleiro do jogador
     * com base nos resultados do ataque.
     *
     * @void
     */
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
    
    /**
     * Configura o componente visual do radar no jogo.
     * Este metodo cria um ImageView com a imagem do radar e define um evento de clique
     * que aciona a utilização do radar. O radar pode ser configurado para ter multiplas imagens
     * para indicar diferentes estados ou usos, mas neste caso, apenas uma imagem e implementada.
     *
     * @void
     */
    private void criarRadar() {
    	Image radarImage = new Image("file:src/resources/imagens/Radar.png");
        ImageView imageViewRadar = new ImageView(radarImage);
        imageViewRadar.setOnMouseClicked(event -> usaRadar());
        gridPaneRadar.add(imageViewRadar, 0, 0);
    }
    
    /**
     * Ativa o uso do radar, marcando o estado como verdadeiro e exibindo uma mensagem no terminal.
     * Este método e chamado quando o jogador interage com o componente visual do radar,
     * indicando que deseja utilizar o radar para obter informacoes sobre o tabuleiro do inimigo.
     *
     * @void
     */
    private void usaRadar() {
    	usandoRadar=true;
    	appendToTerminal("Radar selecionado!");
    }
   
    /**
     * Atualiza o texto exibido no rotulo que indica os usos restantes do radar.
     * Este metodo e responsável por refletir a quantidade atual de usos permitidos do radar.
     *
     * @void
     */
    private void updateRadarLabel() {
        labelUsosRadar.setText("Usos restantes: " + usosRadar);
    }
     
    /**
     * Inicia o jogo apos todos os navios terem sido posicionados pelo jogador.
     * Este metodo verifica se todos os navios foram colocados, habilita o modo de atirar,
     * e desabilita o botao de iniciar o jogo para prevenir re-inicializacoes durante o jogo.
     *
     * @void
     */
    private void iniciarJogo() {
    	if (navioIndex >= 4) {

            todosNaviosPosicionados = true; // Todos os navios foram posicionados
        }
        if (todosNaviosPosicionados) {
            jogoIniciado = true;    
            iniciarJogoButton.setDisable(true);
        }
    }
    /**
     * Verifica se o jogo terminou, seja pela vitória ou derrota do jogador.
     * Este metodo compara o estado dos navios do jogador e do inimigo para determinar o resultado final do jogo.
     * Apos a conclusao do jogo, ele atualiza o estado global do jogo e exibe mensagens apropriadas ao jogador.
     *
     * @void
     */
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
    
    /**
     * Reinicia o jogo, limpando os tabuleiros e resetando o estado do jogo para o início.
     * Este metodo limpa os elementos gráficos dos tabuleiros, reabilita o botão de iniciar o jogo,
     * e chama o metodo de inicializacao para preparar o jogo para uma nova partida.
     *
     * @void
     */
    private void reiniciarJogo() {
        // Limpar os tabuleiros
        gridPaneInimigo.getChildren().clear();
        gridPaneJogador.getChildren().clear();
        iniciarJogoButton.setDisable(false);

        // Reinicializar os tabuleiros chamando o método initialize
        initialize(null, null);
    }
    
    /**
     * Cria e configura os navios do jogador antes do jogo começar.
     * Este metodo instancia os tipos de navios disponíveis, configura suas propriedades basicas,
     * e adiciona imagens representativas para cada tipo de navio no painel de selecao de navios.
     *
     * @void
     */
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
   
    /**
     * Seleciona o tipo de navio baseado na coordenada X passada.
     * Este método atribui o tipo de navio correspondente à variavel de instancia {@code navioClicado},
     * que e utilizado posteriormente para posicionar navios no tabuleiro.
     *
     * @param x A coordenada X que determina o tipo de navio a ser selecionado.
     * @param image O ImageView associado ao navio clicado, usado para visualização.
     */
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
    
    /**
     * Posiciona um navio no tabuleiro do jogador.
     * Este metodo verifica se o jogo já começou, limpa a posicao anterior do navio (se houver),
     * coloca o navio no tabuleiro, atualiza as imagens visuais do navio no tabuleiro, e incrementa o indice do navio.
     * Se o jogo ja estiver iniciado, o metodo retorna imediatamente sem fazer nada.
     *
     * @param x A coordenada X onde o navio deve ser posicionado.
     * @param y A coordenada Y onde o navio deve ser posicionado.
     * @param navio O objeto Navio a ser posicionado.
     */
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
    
    /**
     * Gira um navio no tabuleiro do jogador.
     * Este metodo verifica se o jogo já comecou, obtem o navio pela posição fornecida, limpa sua posicao atual,
     * tenta girar o navio no tabuleiro, e atualiza as imagens visuais do navio no tabuleiro conforme necessario.
     * Se o jogo ainda nao tiver comecado, o método retorna imediatamente sem fazer nada.
     *
     * @param x A coordenada X da posicao central do navio.
     * @param y A coordenada Y da posicao central do navio.
     * @param imageView O ImageView associado ao navio que esta sendo girado.
     */
    private void girarNavio(int x, int y, ImageView imageView) {
        if (!jogoIniciado) {
            Navio navio = getNavioPorCoordenadas(x, y);
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
     
    /**
	 * Atualiza as imagens dos navios no tabuleiro do jogador com base na orientacao do navio.
	 * Este metodo recebe um navio e um codigo de posicao (representando a orientacao do navio), e atualiza as imagens
	 * dos pontos do navio no tabuleiro do jogador de acordo com as imagens associadas à orientacao especificada.
	 *
	 * @param navio O navio cujas imagens devem ser atualizadas.
	 * @param pos Codigo de posicao indicando a orientacao do navio.
	 */
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
    
    /**
     * Atualiza visualmente o tabuleiro do jogador.
     * Este metodo percorre todas as celulas do tabuleiro do jogador, atualizando as imagens de cada celula com base
     * no estado atual da celula (por exemplo, se um navio foi atingido, se Ha agua, etc.). Alem disso, adiciona ou remove
     * eventos de clique para permitir a interação do usuario com as celulas do tabuleiro.
     *
     * @void
     */
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
                        Image waterImage = new Image("file:src/resources/imagens/Mar2.png");
                        imageView.setImage(waterImage); // Água
                        imageView.setOnMouseClicked(event -> posicionarNavio(y, x, navioClicado)); // Adiciona evento de clique
                    }
                }
            }
        }
    }

    /**
     * Anexa uma mensagem ao terminal do jogo.
     * Este metodo e utilizado para registrar eventos, acoes do jogador, e feedbacks do jogo no terminal,
     * facilitando a visualização e acompanhamento das atividades dentro do jogo.
     *
     * @param message A mensagem a ser anexada ao terminal.
     */
    private void appendToTerminal(String message) {
        terminalTextArea.appendText(message + "\n");
    }
      
    /**
     * Retorna o navio posicionado nas coordenadas especificas no tabuleiro do jogador.
     * Este metodo percorre todos os navios registrados no tabuleiro do jogador e verifica se alguma das coordenadas
     * do navio corresponde as coordenadas fornecidas. Se encontrar uma correspondencia, retorna o navio correspondente.
     * Caso contrario, retorna null.
     *
     * @param x A coordenada X a ser verificada.
     * @param y A coordenada Y a ser verificada.
     * @return O navio encontrado nas coordenadas especificadas, ou null se nenhum navio corresponder.
     */
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

   /**
     * Obtem um no por indice de linha e coluna em um GridPane.
     * Este metodo percorre todos os nos filhos do GridPane fornecido e retorna o primeiro no que corresponda aos indices de linha e coluna especificados.
     * Se nenhum no corresponder, retorna null.
     *
     * @param row Indice da linha do no a ser encontrado.
     * @param column Indice da coluna do no a ser encontrado.
     * @param gridPane O GridPane no qual procurar o nó.
     * @return O no encontrado, ou null se nenhum no corresponder.
     */
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
	
}
    

