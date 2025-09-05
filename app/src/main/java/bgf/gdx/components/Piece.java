package bgf.gdx.components;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import bgf.Player;

// Piece.java - Peça do jogo
public class Piece extends Image {
    private String pieceId;
    private Player owner;
    private Tile currentTile;
    
    public Piece(Texture texture, String pieceId, Player owner) {
        super(texture);
        this.pieceId = pieceId;
        this.owner = owner;
    }
    
    public void moveTo(Tile targetTile) {
        if (currentTile != null) {
            currentTile.setPiece(null);
        }
        
        targetTile.setPiece(this);
        currentTile = targetTile;
        
        // Animação de movimento
        Actions.moveTo(targetTile.getX(), targetTile.getY(), 0.3f);
    }
    
    public Tile getCurrentTile() {
        return currentTile;
    }
    
    public Player getOwner() {
        return owner;
    }
    
    // Outros métodos específicos da peça...
}