package aplicacao;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Array;

import bgf.Player;
import bgf.rules.turn.TurnConfig;
import bgf.rules.turn.TurnManager;
import bgf.rules.turn.TurnStrategy;
import bgf.rules.turn.TurnStrategyFactory;
import bgf.rules.turn.TurnStrategyType;

public class Escova {
    public void setupGame() {
        // Criar jogadores
        Array<Player> players = new Array<>();
        players.add(new Player("Alice", Color.RED));
        players.add(new Player("Bob", Color.BLUE));
        players.add(new Player("Charlie", Color.GREEN));
        players.add(new Player("Diana", Color.YELLOW));
        
        // Inicializar gerenciador de turnos
        TurnManager turnManager = new TurnManager();
        
        // Exemplo 1: Ordem padrão
        turnManager.initialize(players, TurnStrategyType.STANDARD);
        
        // Exemplo 2: Ordem aleatória
        turnManager.initialize(players, TurnStrategyType.RANDOM);
        
        // Exemplo 3: Ordem baseada em estatística (pontuação)
        Map<String, Object> config = new HashMap<>();
        config.put("statKey", "score");
        config.put("ascending", false); // Maior pontuação primeiro
        turnManager.initialize(players, TurnStrategyType.STAT_BASED, config);
        
        // Exemplo 4: Usando configuração JSON
        String jsonConfig = "{\"type\": \"STAT_BASED\", \"parameters\": {\"statKey\": \"score\", \"ascending\": false}}";
        TurnConfig turnConfig = TurnConfig.fromJson(jsonConfig);
        TurnStrategy strategy = TurnStrategyFactory.createFromConfig(turnConfig);
        turnManager.setTurnStrategy(strategy);
        
        // Adicionar listener para eventos de turno
        turnManager.addTurnListener(new TurnManager.TurnListener() {
            @Override
            public void onTurnStarted(Player player, int turnNumber) {
                System.out.println("Turno " + turnNumber + " iniciado para: " + player.getName());
            }
            
            @Override
            public void onTurnEnded(Player player, int turnNumber) {
                System.out.println("Turno " + turnNumber + " finalizado para: " + player.getName());
            }
            
            @Override
            public void onTurnStrategyChanged(TurnStrategyType newStrategy) {
                System.out.println("Estratégia de turno alterada para: " + newStrategy);
            }
        });
        
        // Avançar turnos
        for (int i = 0; i < 10; i++) {
            Player current = turnManager.getCurrentPlayer();
            System.out.println("Jogador atual: " + current.getName());
            turnManager.nextTurn();
        }
    }

    public static void main(String[] args) {
        Escova jogo = new Escova();
        jogo.setupGame();
    }
}