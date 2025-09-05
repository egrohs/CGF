package aplicacao;

import com.badlogic.gdx.utils.Array;

import ttgdx.Player;
import ttgdx.rules.turn.TurnStrategy;
import ttgdx.rules.turn.TurnStrategyType;

// Exemplo de estratégia personalizada
public class CustomTurnStrategy implements TurnStrategy {
    // Implementação de uma estratégia específica para um jogo
    
    @Override
    public void initialize(Array<Player> players) {
        // Lógica de inicialização personalizada
    }
    
    @Override
    public Player getNextPlayer() {
        // Lógica personalizada para determinar o próximo jogador
        return null;
    }
    
    @Override
    public void nextTurn() {
        // Lógica personalizada para avançar o turno
    }
    
    @Override
    public Array<Player> getTurnOrder() {
        // Retorna a ordem atual personalizada
        return null;
    }
    
    @Override
    public TurnStrategyType getType() {
        return TurnStrategyType.CUSTOM;
    }
}