package com.example.game;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class Tile extends StackPane{

    public int x,y;
    public boolean hasMine;
    public boolean tileOpen = false;

    public Rectangle border = new Rectangle(HelloApplication.TILE_SIZE - 2, HelloApplication.TILE_SIZE - 2);
    public Text text = new Text();

    public Tile(int x, int y, boolean hasMine) {
        this.x = x;
        this.y = y;
        this.hasMine = hasMine;

        border.setFill(Color.LIGHTGRAY);
        border.setStroke(Color.BLACK);

        text.setVisible(false);
        text.setText(hasMine ? "X" : "");

        getChildren().addAll(border, text);

        setTranslateX(x * HelloApplication.TILE_SIZE); // positions tile
        setTranslateY(y * HelloApplication.TILE_SIZE); // positions tile

        setOnMouseClicked(e -> open());
    }

    public void open() {
        if (tileOpen) {
            return;
        } else if (hasMine) {
            HelloApplication.gameOver();

        } else if (tileOpen = true) {
            text.setVisible(true);
            border.setFill(null);

            if (text.getText().isEmpty()) {
                HelloApplication.getNeighbors(this).forEach(Tile::open); // if you open a tile that does not have a bomb and the
                // adjacent cells do not have bombs, it will recursively
                // open those cells
            }

            if (!hasMine && !text.getText().isEmpty()) {
                HelloApplication.mineCount--;
                System.out.println(HelloApplication.mineCount);
            }

            if (HelloApplication.mineCount == 0) {
                for (int y = 0; y < HelloApplication.yLen; y++) {
                    for (int x = 0; x < HelloApplication.xLen; x++) {
                        HelloApplication.grid[x][y].setDisable(true);
                    }
                }
                System.out.println("WINNER WINNER CHICKEN DINNER!!!");
            }
        }
    }
}
