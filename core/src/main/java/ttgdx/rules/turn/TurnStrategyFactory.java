package ttgdx.rules.turn;

import java.util.Map;

// TurnStrategyFactory.java - Fábrica para criar estratégias de turno
public class TurnStrategyFactory {
    
    public static TurnStrategy createStrategy(TurnStrategyType type) {
        return createStrategy(type, null);
    }
    
    public static TurnStrategy createStrategy(TurnStrategyType type, Map<String, Object> config) {
        switch (type) {
            case STANDARD:
                return new StandardTurnStrategy();
                
            // case REVERSE:
            //     return new ReverseTurnStrategy();
                
            case RANDOM:
                return new RandomTurnStrategy();
                
            case STAT_BASED:
                String statKey = (String) config.get("statKey");
                Boolean ascending = (Boolean) config.get("ascending");
                return new StatBasedTurnStrategy(statKey, ascending != null ? ascending : false);
                
            case AUCTION:
                return new AuctionTurnStrategy();
                
            // case TIME_TRACK:
            //     return new TimeTrackTurnStrategy();
                
            // case ROLE_ORDER:
            //     return new RoleOrderTurnStrategy();
                
            // case PROGRESSIVE:
            //     return new ProgressiveTurnStrategy();
                
            // case CLAIM_ACTION:
            //     return new ClaimActionTurnStrategy();
                
            // case PASS_ORDER:
            //     return new PassOrderTurnStrategy();
                
            case CUSTOM:
                // Para estratégias personalizadas, espera-se uma instância já criada
                return (TurnStrategy) config.get("strategy");
                
            default:
                return new StandardTurnStrategy();
        }
    }
    
    public static TurnStrategy createFromConfig(TurnConfig config) {
        return createStrategy(config.getType(), config.getParameters());
    }
}