package br.ufrn.imd.controle;

import br.ufrn.imd.modelo.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.ResourceBundle;

public class BatalhaNavalController implements Initializable {

    @FXML
    private GridPane gridPaneInimigo;

    @FXML
    private GridPane gridPaneJogador;

    private final int SIZE = 10; // Tamanho do tabuleiro
    private TabuleiroInimigo tabuleiroInimigo;
    private Tabuleiro tabuleiroJogador;
    private int navioIndex;
    private boolean todosNaviosPosicionados;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tabuleiroInimigo = new TabuleiroInimigo(); // Inicializa o TabuleiroInimigo
        tabuleiroJogador = new Tabuleiro(); // Inicializa o TabuleiroJogador
        navioIndex = 0;
        todosNaviosPosicionados = false;

        // Criar o tabuleiro do inimigo com retângulos azuis
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                Rectangle rectangle = new Rectangle(30, 30, Color.BLUE);
                rectangle.setStroke(Color.BLACK); // Adiciona borda preta
                rectangle.setStrokeWidth(1); // Define a largura da borda
                int x = j;
                int y = i;
                rectangle.setOnMouseClicked(event -> atirar(x, y, rectangle)); // Adiciona evento de clique
                gridPaneInimigo.add(rectangle, j, i);
            }
        }

        // Criar o tabuleiro do jogador com retângulos azuis
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                Rectangle rectangle = new Rectangle(30, 30, Color.BLUE);
                rectangle.setStroke(Color.BLACK); // Adiciona borda preta
                rectangle.setStrokeWidth(1); // Define a largura da borda
                int x = j;
                int y = i;
                rectangle.setOnMouseClicked(event -> posicionarNavio(x, y, rectangle)); // Adiciona evento de clique
                gridPaneJogador.add(rectangle, j, i);
            }
        }
    }

    // Método para simular um tiro
    private void atirar(int x, int y, Rectangle rectangle) {
        if (!todosNaviosPosicionados) {
            return; // Não permite tiros até que todos os navios estejam posicionados
        }

        tabuleiroInimigo.getTabuleiro().shoot(x, y); // Atira no tabuleiro do inimigo

        // Atualiza a cor do retângulo com base no resultado do tiro
        if (tabuleiroInimigo.getTabuleiro().getBoard()[x][y] == 2) {
            rectangle.setFill(Color.RED); // Navio atingido, muda para vermelho
        } else if (tabuleiroInimigo.getTabuleiro().getBoard()[x][y] == 3) {
            rectangle.setFill(Color.BLACK); // Água atingida, muda para preto
        }

        rectangle.setDisable(true); // Desativa o rectangle para evitar múltiplos tiros no mesmo lugar

        // Chama a função de ataque do tabuleiro inimigo
        realizarAtaqueInimigo();
        jogoAcabou();
    }
    
    private void jogoAcabou() {
        // Verificar condição de vitória do jogador
        if (tabuleiroInimigo.getTabuleiro().todosNaviosAfundados()) {
            System.out.println("Parabéns! Você afundou todos os navios inimigos e venceu o jogo!");
            reiniciarJogo();
            return;
        }
        // Verificar condição de derrota do jogador
        if (tabuleiroJogador.todosNaviosAfundados()) {
            System.out.println("Você perdeu! Todos os seus navios foram afundados.");
            reiniciarJogo();
        }
    }

    private void reiniciarJogo() {
        // Limpar os tabuleiros
        gridPaneInimigo.getChildren().clear();
        gridPaneJogador.getChildren().clear();

        // Reinicializar os tabuleiros chamando o método initialize
        initialize(null, null);
    }

    // Método para realizar o ataque do inimigo
    private void realizarAtaqueInimigo() {
        tabuleiroInimigo.realizarAtaque(tabuleiroJogador); // Ataca o tabuleiro do jogador

        // Atualiza os retângulos do tabuleiro do jogador de acordo com o resultado do ataque
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                Rectangle rectangle = (Rectangle) getNodeByRowColumnIndex(i, j, gridPaneJogador);
                if (rectangle != null) {
                    if (tabuleiroJogador.getBoard()[j][i] == 2) {
                        rectangle.setFill(Color.RED); // Navio atingido, muda para vermelho
                    } else if (tabuleiroJogador.getBoard()[j][i] == 3) {
                        rectangle.setFill(Color.BLACK); // Água atingida, muda para preto
                    }
                }
            }
        }
    }

    // Método para posicionar navios
    private void posicionarNavio(int x, int y, Rectangle rectangle) {
        if (todosNaviosPosicionados) {
            return; // Não permite posicionar mais navios se todos já foram posicionados
        }

        Navio navio = criarNavio();
        if (navio != null) {
            navio.setStartX(x);
            navio.setStartY(y);
            if (tabuleiroJogador.placeShip(navio)) {
                for (Coordenadas coordenada : navio.getCoordenadas()) {
                    int coordX = coordenada.getX();
                    int coordY = coordenada.getY();
                    Rectangle rect = (Rectangle) getNodeByRowColumnIndex(coordY, coordX, gridPaneJogador);
                    if (rect != null) {
                        rect.setFill(Color.GREEN);
                    }
                }
                navioIndex++;
                if (navioIndex >= 4) {
                    todosNaviosPosicionados = true; // Todos os navios foram posicionados
                }
            }
        }
    }

    // Método para criar navios em sequência
    private Navio criarNavio() {
        switch (navioIndex) {
            case 0:
                return new Corveta(true, true, 0, 0);
            case 1:
                return new Submarino(true, true, 0, 0);
            case 2:
                return new Fragata(true, true, 0, 0);
            case 3:
                return new Destroyer(true, true, 0, 0);
            default:
                return null;
        }
    }

    // Método para obter o nó (Rectangle) de uma célula específica do GridPane
    private Rectangle getNodeByRowColumnIndex(final int row, final int column, GridPane gridPane) {
        for (javafx.scene.Node node : gridPane.getChildren()) {
            if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column) {
                return (Rectangle) node;
            }
        }
        return null;
    }
}
