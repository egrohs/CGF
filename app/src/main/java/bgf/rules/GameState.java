package bgf.rules;

import java.util.HashMap;
import java.util.Map;

public class GameState {
    // Estado serializável do jogo
    private Map<String, Object> stateData;
    
    public GameState() {
        stateData = new HashMap<>();
    }
    
    public void setValue(String key, Object value) {
        stateData.put(key, value);
    }
    
    public Object getValue(String key) {
        return stateData.get(key);
    }
    
    public String toJson() {
        // Implementar serialização para JSON
        return "";
    }
    
    public static GameState fromJson(String json) {
        // Implementar desserialização de JSON
        return new GameState();
    }
}