package sample;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.Random;

public class Controller {

    public Pane gamePane;
    private Options options = new Options();
    private Random r = new Random();

    public void newGame() {
        GridPane gp = new GridPane();

        int probability = 100;
        int percentage = options.getDifficulty() * 10;

        for (int j = 0; j < options.getColumnCount(); j++) {

            for (int i = 0; i < options.getRowCount(); i++) {
                boolean isMine;

                isMine = r.nextInt(probability) < percentage;

                if (j == 0 && i == 0) {
                    isMine = false;
                }

                Mine b = new Mine(isMine);

                if (b.isMine()) {
                    b.setStyle("-fx-background-color: black");
                }

                b.setMinHeight(20);
                b.setMinWidth(30);

                int finalI = i;
                int finalJ = j;

                b.setOnAction(e -> {
                    if (b.isMine()) {
                        Components.simpleInfoBox("Dead", "You Died", "OK");
                        gp.setDisable(true);
                    } else {
                        b.setDisable(true);
                        b.setClicked(true);
                        checkWon(gp);

                        if (countMines(finalJ, finalI, gp) == 0) {
                            b.setText("");
                            b.setDisable(true);
                            b.setStyle("-fx-background-color: dimgrey");
                        } else {
                            b.setText("" + countMines(finalJ, finalI, gp));
                        }

                        collapseEmpty(gp, b);
                    }
                });

                b.setX(finalJ);
                b.setY(finalI);

                double width = options.getColumnCount()*30  +8;
                double height = options.getRowCount()*30 + 88;


                gamePane.getScene().getWindow().setHeight(height);
                gamePane.getScene().getWindow().setWidth(width);


                gp.add(b, j, i);
                gamePane.getChildren().clear();
                gamePane.getChildren().add(gp);
            }
        }
    }

    private int countMines(int x, int y, GridPane gp) {
        int nearMines = 0;
        int counter = 0;

        counter += options.getRowCount() * (x);
        counter += y + 1;

        if (y != options.getRowCount() - 1) {
            if (counter != gp.getChildren().size() - 1) {
                if (gp.getChildren().get(counter + 1) instanceof Mine) {
                    if (((Mine) gp.getChildren().get(counter + 1)).isMine()) {
                        nearMines++;
                    }
                }
            }
        }

        if (y != 0) {
            if (counter != gp.getChildren().size() - 1) {
                if (gp.getChildren().get(counter - 1) instanceof Mine) {
                    if (((Mine) gp.getChildren().get(counter - 1)).isMine()) {
                        nearMines++;
                    }
                }
            }
        }

        if (x != options.getColumnCount() - 1) {
            if (gp.getChildren().get(counter + options.getRowCount()) instanceof Mine) {
                if (((Mine) gp.getChildren().get(counter + options.getRowCount())).isMine()) {
                    nearMines++;
                }
            }
        }

        if (x != 0) {
            if (gp.getChildren().get(counter - options.getRowCount()) instanceof Mine) {
                if (((Mine) gp.getChildren().get(counter - options.getRowCount())).isMine()) {
                    nearMines++;
                }
            }
        }

        if (y != 0 && x != 0) {
            if (gp.getChildren().get(counter - 1 - options.getRowCount()) instanceof Mine) {
                if (((Mine) gp.getChildren().get(counter - 1 - options.getRowCount())).isMine()) {
                    nearMines++;
                }
            }
        }

        if (y != options.getRowCount() - 1 && x != 0) {
            if (gp.getChildren().get(counter + 1 - options.getRowCount()) instanceof Mine) {
                if (((Mine) gp.getChildren().get(counter + 1 - options.getRowCount())).isMine()) {
                    nearMines++;
                }
            }
        }

        if (y != options.getRowCount() - 1 && x  != options.getColumnCount() - 1) {
            if (gp.getChildren().get(counter + 1 + options.getRowCount()) instanceof Mine) {
                if (((Mine) gp.getChildren().get(counter + 1 + options.getRowCount())).isMine()) {
                    nearMines++;
                }
            }
        }

        if (y != 0 && x  != options.getColumnCount() - 1) {
            if (gp.getChildren().get(counter - 1 + options.getRowCount()) instanceof Mine) {
                if (((Mine) gp.getChildren().get(counter - 1 + options.getRowCount())).isMine()) {
                    nearMines++;
                }
            }
        }

        return nearMines;
    }

    private void collapseEmpty(GridPane gp, Mine m) {
        ArrayList<Mine> next = new ArrayList<>();
        ArrayList<Mine> next2 = new ArrayList<>();

        for (Mine mine : getSurrounding(gp, m)) {
            if (countMines(mine.getX(), mine.getY(), gp) == 0) {
                if (!mine.isMine()) {
                    mine.setText("");
                    mine.setDisable(true);
                    mine.setClicked(true);
                    mine.setStyle("-fx-background-color: dimgrey");
                    next.add(mine);
                }
            }
        }

        for (Mine mine2 : next) {
            for (Mine mine : getSurrounding(gp, mine2)) {

                if (!mine.isMine()) {
                    int mines = countMines(mine.getX(), mine.getY(), gp);
                    if (mines == 0) {
                        next2.add(mine);
                        mine.setText("");
                        mine.setDisable(true);
                        mine.setClicked(true);
                        mine.setStyle("-fx-background-color: dimgrey");
                    } else {
                        mine.setText("" + mines);
                        mine.setDisable(true);
                        mine.setClicked(true);
                    }
                }

            }
        }

        for (Mine mine2 : next2) {
            for (Mine mine : getSurrounding(gp, mine2)) {

                if (!mine.isMine()) {
                    int mines = countMines(mine.getX(), mine.getY(), gp);
                    if (mines == 0) {
                        mine.setText("");
                        mine.setDisable(true);
                        mine.setClicked(true);
                        mine.setStyle("-fx-background-color: dimgrey");
                    } else {
                        mine.setText("" + mines);
                        mine.setDisable(true);
                        mine.setClicked(true);
                    }
                }

            }
        }
    }

    private ArrayList<Mine> getSurrounding(GridPane gp, Mine m) {
        ArrayList<Mine> mines = new ArrayList<>();

        for (Node n : gp.getChildren()) {
            if (n instanceof Mine) {
                // Top Left
                if (m.getX() > 0 && m.getY() > 0) {
                    if (((Mine) n).getX() == m.getX() - 1 && ((Mine) n).getY() == m.getY() - 1) {
                        mines.add((Mine) n);
                    }
                }

                // Top Middle
                if (m.getY() > 0) {
                    if (((Mine) n).getX() == m.getX() && ((Mine) n).getY() == m.getY() - 1) {
                        mines.add((Mine) n);
                    }
                }

                // Top Right
                if (m.getX() < options.getColumnCount() && m.getY() > 0) {
                    if (((Mine) n).getX() == m.getX() + 1 && ((Mine) n).getY() == m.getY() - 1) {
                        mines.add((Mine) n);
                    }
                }

                // Left
                if (m.getX() > 0) {
                    if (((Mine) n).getX() == m.getX() - 1 && ((Mine) n).getY() == m.getY()) {
                        mines.add((Mine) n);
                    }
                }

                // Right
                if (m.getX() < options.getColumnCount()) {
                    if (((Mine) n).getX() == m.getX() + 1 && ((Mine) n).getY() == m.getY()) {
                        mines.add((Mine) n);
                    }
                }

                // Bottom Left
                if (m.getX() < options.getRowCount() && m.getY() > 0) {
                    if (((Mine) n).getX() == m.getX() - 1 && ((Mine) n).getY() == m.getY() + 1) {
                        mines.add((Mine) n);
                    }
                }

                // Bottom Middle
                if (m.getX() < options.getRowCount()) {
                    if (((Mine) n).getX() == m.getX() && ((Mine) n).getY() == m.getY() + 1) {
                        mines.add((Mine) n);
                    }
                }

                // Bottom Right
                if (m.getX() < options.getColumnCount() && m.getY() < options.getRowCount()) {
                    if (((Mine) n).getX() == m.getX() + 1 && ((Mine) n).getY() == m.getY() + 1) {
                        mines.add((Mine) n);
                    }
                }
            }
        }

        return mines;
    }

    private void checkWon (GridPane gp){
                boolean won = true;

                for (Node n : gp.getChildren()) {
                    if (n instanceof javafx.scene.control.Button) {

                        if (!((Mine) n).isMine()) {
                            if (!n.isDisable()) {
                                won = false;
                            }
                        }

                    }
                }

                if (won) {
                    Components.simpleInfoBox("Success", "You won the game!", "OK");
                }
            }

    public void options () {
                options = Components.options();
            }

    public void close () {
                if (Components.ok("Warning", "Do you want to close the game?")) {
                    Platform.exit();
                }
            }

}