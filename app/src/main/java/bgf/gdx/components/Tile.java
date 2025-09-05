package bgf.gdx.components;

import java.util.Map;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import lombok.Data;

// Tile.java - Espaço do tabuleiro
@Data
public class Tile extends Image {
    private int row, col;
    private String type;
    private Texture texture;
    private Piece currentPiece;
    private boolean highlighted;
    private HighlightStyle highlightStyle;
    private Map<String, Object> properties;
    
    public Tile(int row, int col, float size) {
        this.row = row;
        this.col = col;
        this.highlighted = false;
        
        setSize(size, size);
//TODO        setDefaultTexture(); // Definir textura padrão
    }
    
    public boolean hasPiece() {
        return currentPiece != null;
    }
    
    public Piece getPiece() {
        return currentPiece;
    }
    
    public void setPiece(Piece piece) {
        this.currentPiece = piece;
        if (piece != null) {
            piece.setPosition(getX(), getY());
        }
    }
    
    public void setHighlighted(boolean highlighted, HighlightStyle style) {
        this.highlighted = highlighted;
        this.highlightStyle = style;
        updateAppearance();
    }
    
    private void updateAppearance() {
        if (highlighted) {
            // Aplicar efeito visual de destaque baseado no estilo
            setColor(highlightStyle.getColor());
        } else {
            // Voltar à aparência normal
            setColor(Color.WHITE);
        }
    }
    
    public enum HighlightStyle {
        VALID_MOVE(Color.GREEN),
        INVALID_MOVE(Color.RED),
        SELECTED(Color.YELLOW);
        
        private Color color;
        
        HighlightStyle(Color color) {
            this.color = color;
        }
        
        public Color getColor() {
            return color;
        }
    }

    public void setProperty(String key, Object value) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setProperty'");
    }
}