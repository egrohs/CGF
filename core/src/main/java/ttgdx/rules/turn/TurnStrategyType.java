package ttgdx.rules.turn;

// TurnStrategyType.java - Enum com os tipos de estratégia
public enum TurnStrategyType {
    STANDARD,           // Sequência padrão (0, 1, 2, 3...)
    REVERSE,            // Sequência reversa (3, 2, 1, 0...)
    AUCTION,            // Turnos por leilão
    CLAIM_ACTION,       // Turnos por reivindicação de ação
    PASS_ORDER,         // Turnos por ordem de passe
    PROGRESSIVE,        // Turnos progressivos
    RANDOM,             // Ordem aleatória
    ROLE_ORDER,         // Ordem por papel/função
    STAT_BASED,         // Ordem baseada em estatísticas
    TIME_TRACK,         // Ordem por trilha de tempo
    CUSTOM              // Ordem personalizada
}