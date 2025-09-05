package bgf.gdx;

import bgf.gdx.components.Piece;
import bgf.gdx.components.Tile;
import lombok.Data;

// PieceMoveEvent.java - Evento de movimento de peça
@Data
public class PieceMoveEvent {
    private Piece piece;
    private Tile fromTile;
    private Tile toTile;
    
    public PieceMoveEvent(Piece piece, Tile fromTile, Tile toTile) {
        this.piece = piece;
        this.fromTile = fromTile;
        this.toTile = toTile;
    }
    
    // Getters...
}