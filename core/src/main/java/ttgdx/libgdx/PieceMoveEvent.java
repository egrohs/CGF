package ttgdx.libgdx;

import lombok.Data;
import ttgdx.libgdx.components.Piece;
import ttgdx.libgdx.components.Tile;

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