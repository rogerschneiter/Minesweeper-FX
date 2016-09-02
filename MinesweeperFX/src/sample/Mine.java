package sample;

import javafx.scene.control.Button;

public class Mine extends Button {

    private boolean mine;
    private boolean clicked;

    private int x;
    private int y;

    public Mine(boolean isMine) {
        this.mine = isMine;
    }

    public boolean isMine() {
        return mine;
    }

    public void setMine(boolean mine) {
        this.mine = mine;
    }

    public boolean isClicked() {
        return clicked;
    }

    public void setClicked(boolean clicked) {
        this.clicked = clicked;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
