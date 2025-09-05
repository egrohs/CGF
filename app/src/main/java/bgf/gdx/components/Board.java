package bgf.gdx.components;

import com.badlogic.gdx.scenes.scene2d.Group;

// Board.java - Representação do tabuleiro
public class Board extends Group {
    private Tile[][] tiles;
    private int rows, cols;
    private float tileSize;
    
    public Board(int rows, int cols, float tileSize) {
        this.rows = rows;
        this.cols = cols;
        this.tileSize = tileSize;
        this.tiles = new Tile[rows][cols];
        
        initializeTiles();
    }
    
    private void initializeTiles() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                Tile tile = new Tile(row, col, tileSize);
                tile.setPosition(col * tileSize, row * tileSize);
                tiles[row][col] = tile;
                addActor(tile);
            }
        }
    }
    
    public Tile getTileAt(int row, int col) {
        if (row >= 0 && row < rows && col >= 0 && col < cols) {
            return tiles[row][col];
        }
        return null;
    }
    
    public Tile getTileAtScreenPos(float screenX, float screenY) {
        // Converter coordenadas de tela para coordenadas do tabuleiro
        int col = (int) (screenX / tileSize);
        int row = (int) (screenY / tileSize);
        
        return getTileAt(row, col);
    }
    
    // Outros métodos utilitários...
}