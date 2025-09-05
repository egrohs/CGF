package bgf.rules.turn;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.badlogic.gdx.utils.Array;

import bgf.Player;

@Component
// TurnManager.java - Gerenciador de turnos com suporte a múltiplas estratégias
public class TurnManager {
    private Array<Player> players;
    private TurnStrategy turnStrategy;
    private int turnCount;
    private List<TurnListener> listeners;
    
    public TurnManager() {
        this.players = new Array<>();
        this.listeners = new ArrayList<>();
        this.turnCount = 0;
    }
    
    public void initialize(Array<Player> players, TurnStrategyType strategyType) {
        initialize(players, strategyType, null);
    }
    
    public void initialize(Array<Player> players, TurnStrategyType strategyType, Map<String, Object> config) {
        this.players = players;
        setTurnStrategy(strategyType, config);
        this.turnCount = 0;
    }
    
    public void setTurnStrategy(TurnStrategyType strategyType) {
        setTurnStrategy(strategyType, null);
    }
    
    public void setTurnStrategy(TurnStrategyType strategyType, Map<String, Object> config) {
        this.turnStrategy = TurnStrategyFactory.createStrategy(strategyType, config);
        this.turnStrategy.initialize(players);
        
        for (TurnListener listener : listeners) {
            listener.onTurnStrategyChanged(strategyType);
        }
    }
    
    public void setTurnStrategy(TurnStrategy strategy) {
        this.turnStrategy = strategy;
        this.turnStrategy.initialize(players);
        
        for (TurnListener listener : listeners) {
            listener.onTurnStrategyChanged(strategy.getType());
        }
    }
    
    public Player getCurrentPlayer() {
        return turnStrategy.getNextPlayer();
    }
    
    public void nextTurn() {
        Player previousPlayer = getCurrentPlayer();
        turnStrategy.nextTurn();
        turnCount++;
        
        Player currentPlayer = getCurrentPlayer();
        
        for (TurnListener listener : listeners) {
            listener.onTurnEnded(previousPlayer, turnCount - 1);
            listener.onTurnStarted(currentPlayer, turnCount);
        }
    }
    
    public int getTurnCount() {
        return turnCount;
    }
    
    public Array<Player> getTurnOrder() {
        return turnStrategy.getTurnOrder();
    }
    
    public TurnStrategyType getTurnStrategyType() {
        return turnStrategy.getType();
    }
    
    public void addTurnListener(TurnListener listener) {
        listeners.add(listener);
    }
    
    public void removeTurnListener(TurnListener listener) {
        listeners.remove(listener);
    }
    
    public interface TurnListener {
        void onTurnStarted(Player player, int turnNumber);
        void onTurnEnded(Player player, int turnNumber);
        void onTurnStrategyChanged(TurnStrategyType newStrategy);
    }
    
    // Método para salvar o estado atual dos turnos
    public TurnState saveState() {
        TurnState state = new TurnState();
        state.turnCount = turnCount;
        state.strategyType = turnStrategy.getType();
        // Salvar outros estados necessários conforme a estratégia
        return state;
    }
    
    // Método para restaurar o estado dos turnos
    public void restoreState(TurnState state) {
        this.turnCount = state.turnCount;
        setTurnStrategy(state.strategyType);
        // Restaurar outros estados conforme necessário
    }
    
    public static class TurnState {
        public int turnCount;
        public TurnStrategyType strategyType;
        // Outros campos de estado necessários
    }

    public void nextPhase() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'nextPhase'");
    }
}