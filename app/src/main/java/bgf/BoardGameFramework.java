package bgf;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;

import bgf.controlers.GameEventBus;
import bgf.controlers.NetworkManager;
import bgf.controlers.PlayerManager;
import bgf.creators.BoardConfig;
import bgf.creators.BoardFactory;
import bgf.gdx.BoardInputHandler;
import bgf.gdx.PieceMoveEvent;
import bgf.gdx.components.Board;
import bgf.gdx.components.DebugOverlay;
import bgf.rules.RuleManager;
import bgf.rules.turn.TurnConfig;
import bgf.rules.turn.TurnManager;
import bgf.rules.turn.TurnStrategyType;

// BoardGameFramework.java - Classe principal do framework
public class BoardGameFramework extends Game {
    private Stage stage;
    private Board board;
    private TurnManager turnManager;
    private RuleManager ruleManager;
    private NetworkManager networkManager;
    private DebugOverlay debugOverlay;
    private PlayerManager playerManager;
    
    @Override
    public void create() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        
        // Inicializar gerenciadores
        ruleManager = new RuleManager();
        //turnManager = new TurnManager(createPlayers());
        turnManager = new TurnManager();
//TODO        networkManager = new NetworkManager(NetworkManager.NetworkMode.LOCAL);

        // Configurar estratégia de turnos a partir de arquivo de configuração
        String turnConfigFile = Gdx.files.internal("config/turn_config.json").readString();
        TurnConfig config = TurnConfig.fromJson(turnConfigFile);
        
        turnManager.initialize(playerManager.getPlayers(), config.getType(), config.getParameters());
        
        // Carregar tabuleiro a partir de configuração
        BoardConfig boardconfig = BoardConfig.loadFromJson("board_config.json");
        board = BoardFactory.createBoard(boardconfig);
        stage.addActor(board);
        
        // Configurar input handling
        BoardInputHandler inputHandler = new BoardInputHandler(board);
        Gdx.input.setInputProcessor(new InputMultiplexer(stage, inputHandler));
        
        // Inicializar overlay de debug
        debugOverlay = new DebugOverlay();
        
        // Registrar listeners de eventos
        GameEventBus.getInstance().subscribe(PieceMoveEvent.class, this::onPieceMoved);
        
//TODO Adicionar regras do jogo
        // ruleManager.addRule(new MovementRule());
        // ruleManager.addRule(new PlacementRule());
    }
    
    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
        
        debugOverlay.act();
        debugOverlay.draw();
    }
    
    private void onPieceMoved(PieceMoveEvent event) {
        // Validar e processar movimento
        if (ruleManager.validateMove(event)) {
            // Executar movimento
            event.getPiece().moveTo(event.getToTile());
            
            // Avançar turno se aplicável
            turnManager.nextPhase();
        }
    }
    
    private List<Player> createPlayers() {
        // Criar jogadores baseado na configuração
        List<Player> players = new ArrayList<>();
        players.add(new Player("Player 1", Color.RED));
        players.add(new Player("Player 2", Color.BLUE));
        return players;
    }
    
    // Outros métodos do framework...
    public void nextTurn() {
        turnManager.nextTurn();
        
        // Atualizar UI com informações do turno
        updateTurnUI();
    }
    
    private void updateTurnUI() {
        Player currentPlayer = turnManager.getCurrentPlayer();
        int turnNumber = turnManager.getTurnCount();
        
        // Atualizar elementos de UI com informações do turno atual
    }
    
    // Método para mudar dinamicamente a estratégia de turnos durante o jogo
    public void changeTurnStrategy(TurnStrategyType newStrategy) {
        turnManager.setTurnStrategy(newStrategy);
    }
}