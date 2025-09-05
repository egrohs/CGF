package ttgdx.controlers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Array;

import ttgdx.Player;
import ttgdx.Player.PlayerType;

import java.util.HashMap;
import java.util.Map;

/**
 * Gerencia uma coleção de jogadores para o jogo.
 */
public class PlayerManager {
    private Map<String, Player> players;
    private Array<Player> playerOrder;
    private int currentPlayerIndex;
    
    public PlayerManager() {
        players = new HashMap<>();
        playerOrder = new Array<>();
        currentPlayerIndex = 0;
    }
    
    public void addPlayer(Player player) {
        players.put(player.getId(), player);
        playerOrder.add(player);
    }
    
    public Player getPlayer(String playerId) {
        return players.get(playerId);
    }
    
    public Player getCurrentPlayer() {
        if (playerOrder.size == 0) return null;
        return playerOrder.get(currentPlayerIndex);
    }
    
    public void nextPlayer() {
        if (playerOrder.size > 0) {
            currentPlayerIndex = (currentPlayerIndex + 1) % playerOrder.size;
        }
    }
    
    public void setPlayerOrder(Array<Player> order) {
        this.playerOrder = order;
        this.currentPlayerIndex = 0;
    }
    
    public Array<Player> getPlayers() {
        return playerOrder;
    }
    
    public int getPlayerCount() {
        return players.size();
    }
    
    public Player createPlayer(String id, String name, Color color, Player.PlayerType type) {
        Player player = new Player(id, name, color, type);
        addPlayer(player);
        return player;
    }
    
    public void removePlayer(String playerId) {
        Player player = players.remove(playerId);
        if (player != null) {
            playerOrder.removeValue(player, true);
            
            // Ajustar o índice atual se necessário
            if (currentPlayerIndex >= playerOrder.size) {
                currentPlayerIndex = 0;
            }
        }
    }
    
    public void resetAll() {
        for (Player player : players.values()) {
            player.reset();
        }
        currentPlayerIndex = 0;
    }
    
    /**
     * Retorna o vencedor com base na pontuação mais alta.
     */
    public Player getWinner() {
        if (players.isEmpty()) return null;
        
        Player winner = null;
        for (Player player : players.values()) {
            if (winner == null || player.getScore() > winner.getScore()) {
                winner = player;
            }
        }
        
        return winner;
    }
    
    /**
     * Verifica se o jogo terminou (apenas um jogador ativo).
     */
    public boolean isGameOver() {
        int activePlayers = 0;
        for (Player player : players.values()) {
            if (player.isActive()) {
                activePlayers++;
            }
        }
        return activePlayers <= 1;
    }
}