package bgf.rules.turn;

import com.badlogic.gdx.utils.Array;

import bgf.Player;

// TurnStrategy.java - Interface para estratégias de ordenação de turnos
public interface TurnStrategy {
    /**
     * Inicializa ou reinicia a estratégia com a lista de jogadores
     */
    void initialize(Array<Player> players);
    
    /**
     * Retorna o próximo jogador da sequência
     */
    Player getNextPlayer();
    
    /**
     * Avança para o próximo turno
     */
    void nextTurn();
    
    /**
     * Retorna a lista completa de jogadores na ordem atual
     */
    Array<Player> getTurnOrder();
    
    /**
     * Retorna o tipo da estratégia
     */
    TurnStrategyType getType();
}