package ttgdx.libgdx;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;

import ttgdx.controlers.GameEventBus;
import ttgdx.libgdx.components.Board;
import ttgdx.libgdx.components.Piece;
import ttgdx.libgdx.components.Tile;
import ttgdx.rules.RuleManager;
import ttgdx.rules.turn.TurnManager;

// BoardInputHandler.java - Manipulador de input para o tabuleiro
public class BoardInputHandler extends InputAdapter {
    private Board board;
    private Piece selectedPiece;
    private List<Tile> validMoveTiles;
    @Autowired
    private TurnManager turnManager;
    
    public BoardInputHandler(Board board) {
        this.board = board;
        this.validMoveTiles = new ArrayList<>();
    }
    
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        // Converter coordenadas de tela para coordenadas do stage
//TODO revisar        Vector3 worldCoords = board.stageToScreenCoordinates(screenX, screenY);
        Vector2 worldCoords = board.stageToLocalCoordinates(new Vector2(screenX, screenY));

        // Verificar se clicou em uma peça
        Tile clickedTile = board.getTileAtScreenPos(worldCoords.x, worldCoords.y);
        
        if (clickedTile != null && clickedTile.hasPiece()) {
            Piece piece = clickedTile.getPiece();
            
            // Verificar se o jogador atual é o dono da peça
            if (piece.getOwner().equals(turnManager.getCurrentPlayer())) {
                selectPiece(piece);
                return true;
            }
        }
        
        // Verificar se clicou em um tile válido para movimento
        if (selectedPiece != null && clickedTile != null) {
            if (validMoveTiles.contains(clickedTile)) {
                moveSelectedPiece(clickedTile);
                return true;
            }
        }
        
        // Desselecionar se clicou em área vazia
        clearSelection();
        return false;
    }
    
    private void selectPiece(Piece piece) {
        clearSelection();
        selectedPiece = piece;
        
        // Obter movimentos válidos (consultar RuleManager)
        validMoveTiles = RuleManager.getValidMoves(piece);
        
        // Destacar tiles válidos
        for (Tile tile : validMoveTiles) {
            tile.setHighlighted(true, Tile.HighlightStyle.VALID_MOVE);
        }
        
        // Destacar a peça selecionada
        selectedPiece.getCurrentTile().setHighlighted(true, Tile.HighlightStyle.SELECTED);
    }
    
    private void moveSelectedPiece(Tile targetTile) {
        if (selectedPiece != null) {
            // Publicar evento de movimento
            GameEventBus.getInstance().publish(new PieceMoveEvent(
                selectedPiece, selectedPiece.getCurrentTile(), targetTile));
            
            // Mover a peça
            selectedPiece.moveTo(targetTile);
            clearSelection();
        }
    }
    
    private void clearSelection() {
        // Remover destaque dos tiles
        for (Tile tile : validMoveTiles) {
            tile.setHighlighted(false, null);
        }
        
        if (selectedPiece != null && selectedPiece.getCurrentTile() != null) {
            selectedPiece.getCurrentTile().setHighlighted(false, null);
        }
        
        selectedPiece = null;
        validMoveTiles.clear();
    }
}