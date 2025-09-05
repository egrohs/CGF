package bgf.rules.turn;

import com.badlogic.gdx.utils.Array;

import bgf.Player;

// StatBasedTurnStrategy.java - Ordem baseada em estatísticas
public class StatBasedTurnStrategy implements TurnStrategy {
    private Array<Player> players;
    private int currentIndex;
    private String statKey;
    private boolean ascending;
    
    public StatBasedTurnStrategy(String statKey, boolean ascending) {
        this.statKey = statKey;
        this.ascending = ascending;
    }
    
    @Override
    public void initialize(Array<Player> players) {
        this.players = new Array<>(players);
        sortPlayersByStat();
        this.currentIndex = 0;
    }
    
    private void sortPlayersByStat() {
        
        players.sort((p1, p2) -> {
            int stat1=0, stat2=0;
            try {
                stat1 = (int)Player.class.getField(statKey).get(p1);
                stat2 = (int)Player.class.getField(statKey).get(p2);
            } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            // int stat1 = p1.getStat(statKey);
            // int stat2 = p2.getStat(statKey);
            
            if (ascending) {
                return Integer.compare(stat1, stat2);
            } else {
                return Integer.compare(stat2, stat1);
            }
        });
    }
    
    @Override
    public Player getNextPlayer() {
        return players.get(currentIndex);
    }
    
    @Override
    public void nextTurn() {
        currentIndex = (currentIndex + 1) % players.size;
    }
    
    @Override
    public Array<Player> getTurnOrder() {
        return players;
    }
    
    @Override
    public TurnStrategyType getType() {
        return TurnStrategyType.STAT_BASED;
    }
}