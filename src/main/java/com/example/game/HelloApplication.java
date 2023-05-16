package com.example.game;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HelloApplication extends Application {

    public static VBox vbox = new VBox();
    public static Stage pStage;
    public static Pane root = new Pane();
    public static MenuBar mb = new MenuBar();
    public static Button smiley = new Button();
    public static Parent p;
    public static final int TILE_SIZE = 20;
    public static int x = 320;
    public static int y = 320;
    public static int xLen = x / TILE_SIZE;; // num tiles on X axis
    public static int yLen = y / TILE_SIZE;; // num tiles on Y axis
    public static Tile[][] grid = new Tile[xLen][yLen];
    public static int mineCount = 0;
    //Default difficulty is Intermediate with a 6.4% probability of selecting a bomb
    public static double difficulty = 0.16;



    public static final Image SMILEY = new Image("com/example/game/smiley.png");
    public static final Image DEAD = new Image(new File("dead.jpg").toURI().toString());
    public static final Image MINE = new Image(new File("mine.jpg").toURI().toString());

    public static ImageView view = new ImageView(SMILEY);



    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        // Create ui items
        ui();

        p = initialize(x, xLen, y, yLen, difficulty);

        // Create vbox
       vbox(p);

       // Default settings (Intermediate)
       Scene scene = new Scene(vbox, 325, 395);
       stage.setTitle("Minesweeper");
       //stage.setResizable(false);
       stage.setScene(scene);
       stage.show();
       pStage = stage;
    }

    //pass x, y, and difficulty
    public static Parent initialize(int x, int xLen, int y, int yLen,  double difficulty) {
        root.setPrefSize(x, y);

        for (int n = 0; n < yLen; n++) {
            for (int m = 0; m < xLen; m++) {
                Tile tile = new Tile(m, n, Math.random() < difficulty);

                grid[m][n] = tile; // assign tile to a grid
                root.getChildren().add(tile); // add this tile to the list of children under root so it can be added to
                // scene graph and later be displayed to screen
            }
        }
        for (int n = 0; n < yLen; n++) {
            for (int m = 0; m < xLen; m++) {
                Tile tile = grid[m][n];

                if (tile.hasMine) {
                    continue;
                }

                // calculate number of mines
                long numMines = getNeighbors(tile).stream().filter(t -> t.hasMine).count();

                if (numMines > 0) {
                    tile.text.setText(String.valueOf(numMines)); // set numerical value to the text that tile has
                    mineCount++;

                    if (numMines == 1) {
                        tile.text.setFill(Color.BLUE);
                    } else if (numMines == 2) {
                        tile.text.setFill(Color.GREEN);
                    } else if (numMines == 3) {
                        tile.text.setFill(Color.RED);
                    } else if (numMines == 4) {
                        tile.text.setFill(Color.DARKBLUE);
                    } else if (numMines == 5) {
                        tile.text.setFill(Color.DARKRED);
                    }
                }
            }
        }
        return root;
    }

    public static void vbox(Parent p) {
        vbox.setSpacing(5);
        vbox.setAlignment(Pos.CENTER);
        vbox.setMargin(mb, new Insets(-5, 0, 5, -10));
        vbox.setMargin(p, new Insets(2.5));
        vbox.getChildren().addAll(mb, smiley, p);
    }

    public static void ui() {
        Menu menu = new Menu("Select Difficulty");

        // create menuitems
        MenuItem easy = new MenuItem("Beginner");
        MenuItem medium = new MenuItem("Intermediate");
        MenuItem hard = new MenuItem("Expert");

        // add menuitems to menu
        menu.getItems().add(easy);
        menu.getItems().add(medium);
        menu.getItems().add(hard);

        // add actions for menuitems
        easy.setOnAction(e -> easy());
        medium.setOnAction(e -> medium());
        hard.setOnAction(e -> hard());

        // add menu to menubar
        mb.getMenus().add(menu);
        mb.setMaxHeight(10);

        smiley.setPrefSize(25,30);

        view.setFitWidth(20);
        view.setFitHeight(20);
        view.setPreserveRatio(true);
        smiley.setGraphic(view);
        //smiley.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        smiley.setOnMouseClicked(e -> reset());
    }

    public static void easy() {
        x = 160;
        y = 160;
        xLen = x / TILE_SIZE;
        yLen = y / TILE_SIZE;
        difficulty = 0.16;

       vbox.getChildren().clear();
       p = initialize(x, xLen, y, yLen, difficulty);
        vbox(p);

        Stage stage = new Stage();
        Scene scene = new Scene(vbox, 165, 235); //change to easy
        //pStage.setTitle("Minesweeper");
        //pStage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void medium() {
        x = 320;
        y = 320;
        xLen = x / TILE_SIZE;
        yLen = y / TILE_SIZE;
        difficulty = 0.16;

        vbox.getChildren().clear();
        p = initialize(x, xLen, y, yLen, difficulty);
        vbox(p);

        Stage stage = new Stage();
        Scene scene = new Scene(vbox, 325, 395); //change to medium
        //pStage.setTitle("Minesweeper");
        //pStage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void hard() {
        x = 600;
        y = 320;
        xLen = x / TILE_SIZE;
        yLen = y / TILE_SIZE;
        difficulty = 0.21;

        vbox.getChildren().clear();
        p = initialize(x, xLen, y, yLen, difficulty);
        vbox(p);

        Stage stage = new Stage();
        Scene scene = new Scene(vbox, 605, 395); //change to hard
        //pStage.setTitle("Minesweeper");
        //pStage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void gameOver() {
        for (int y = 0; y < yLen; y++) {
            for (int x = 0; x < xLen; x++) {
                if (grid[x][y].hasMine) {
                    grid[x][y].text.setVisible(true);
                    grid[x][y].border.setFill(null);
                    grid[x][y].setStyle("-fx-background-color: red");
                }
            }
        }
        // Does not allow player to open any more tiles
        for (int y = 0; y < yLen; y++) {
            for (int x = 0; x < xLen; x++) {
                grid[x][y].setDisable(true);
            }
        }
    }

    public static void reset() {
        vbox.getChildren().clear();
        p = initialize(x, xLen, y, yLen, difficulty);
        vbox(p);
        pStage.show();
    }

    public static List<Tile> getNeighbors(Tile tile) { // delivers list of tiles; pass in tile so we can get neighbors
        // of tile

        List<Tile> neighbors = new ArrayList<>();

        // obtain x and y values of neighbors; contain dx and dy values relative to tile
        // given
        int[] nPoints = new int[] { -1, 1, -1, 0, -1, -1, 0, 1, 0, -1, 1, 1, 1, 0, 1, -1 };

        for (int i = 0; i < nPoints.length; i++) {
            int dx = nPoints[i];
            int dy = nPoints[++i];

            int newX = tile.x + dx; // neighbors x-coordinate
            int newY = tile.y + dy; // neighbors y-coordinate

            validate(newX, newY, neighbors);
        }
    return neighbors;
    }

    public static void validate(int newX, int newY, List<Tile> neighbors) {
        if (newX >= 0 && newX < xLen && newY >= 0 && newY < xLen) {
            neighbors.add(grid[newX][newY]);
        }
    }
}