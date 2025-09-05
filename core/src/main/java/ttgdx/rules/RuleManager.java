package ttgdx.rules;

import java.util.ArrayList;
import java.util.List;

import ttgdx.libgdx.PieceMoveEvent;
import ttgdx.libgdx.components.Piece;
import ttgdx.libgdx.components.Tile;

// RuleManager.java - Gerenciador de regras modular
public class RuleManager {
    private List<GameRule> rules;
    
    public RuleManager() {
        rules = new ArrayList<>();
    }
    
    public void addRule(GameRule rule) {
        rules.add(rule);
    }
    
    public boolean validateAction(GameAction action, GameState state) {
        for (GameRule rule : rules) {
            if (!rule.validate(action, state)) {
                return false;
            }
        }
        return true;
    }
    
    public void applyAction(GameAction action, GameState state) {
        if (validateAction(action, state)) {
            action.execute(state);
        }
    }

    public static List<Tile> getValidMoves(Piece piece) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getValidMoves'");
    }

    public boolean validateMove(PieceMoveEvent event) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'validateMove'");
    }
}