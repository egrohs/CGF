package bgf.rules.turn;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

import bgf.Player;

// RandomTurnStrategy.java - Ordem aleatória de turnos
public class RandomTurnStrategy implements TurnStrategy {
    private Array<Player> players;
    private int currentIndex;
    
    @Override
    public void initialize(Array<Player> players) {
        this.players = new Array<>(players);
        shufflePlayers();
        this.currentIndex = 0;
    }
    
    private void shufflePlayers() {
        // Algoritmo Fisher-Yates para embaralhar
        for (int i = players.size - 1; i > 0; i--) {
            int index = MathUtils.random(i);
            Player temp = players.get(i);
            players.set(i, players.get(index));
            players.set(index, temp);
        }
    }
    
    @Override
    public Player getNextPlayer() {
        return players.get(currentIndex);
    }
    
    @Override
    public void nextTurn() {
        currentIndex = (currentIndex + 1) % players.size;
        
        // Se completou um ciclo, reembaralha
        if (currentIndex == 0) {
            shufflePlayers();
        }
    }
    
    @Override
    public Array<Player> getTurnOrder() {
        return players;
    }
    
    @Override
    public TurnStrategyType getType() {
        return TurnStrategyType.RANDOM;
    }
}