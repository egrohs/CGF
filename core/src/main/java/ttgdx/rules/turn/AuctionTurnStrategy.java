package ttgdx.rules.turn;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

import ttgdx.Player;

// AuctionTurnStrategy.java - Turnos por leilão (exemplo simplificado)
public class AuctionTurnStrategy implements TurnStrategy {
    private Array<Player> players;
    private int currentIndex;
    private Map<Player, Integer> bids;
    
    @Override
    public void initialize(Array<Player> players) {
        this.players = new Array<>(players);
        this.bids = new HashMap<>();
        conductAuction();
    }
    
    private void conductAuction() {
        // Simula um leilão onde cada jogador faz uma oferta
        for (Player player : players) {
            int bid = calculateBid(player); // Lógica específica do jogo
            bids.put(player, bid);
        }
        
        // Ordena jogadores pela oferta (maior oferta primeiro)
        players.sort((p1, p2) -> Integer.compare(bids.get(p2), bids.get(p1)));
        currentIndex = 0;
    }
    
    private int calculateBid(Player player) {
        // Lógica de licitação específica do jogo
        // Pode usar recursos, pontos, ou outros fatores
        return MathUtils.random(1, 100); // Exemplo simplificado
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
        return TurnStrategyType.AUCTION;
    }
}