package bgf.rules.turn;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.utils.Json;

// TurnConfig.java - Classe de configuração para serialização
public class TurnConfig {
    private TurnStrategyType type;
    private Map<String, Object> parameters;
    
    public TurnConfig() {
        parameters = new HashMap<>();
    }
    
    public TurnConfig(TurnStrategyType type) {
        this();
        this.type = type;
    }
    
    public TurnStrategyType getType() {
        return type;
    }
    
    public void setType(TurnStrategyType type) {
        this.type = type;
    }
    
    public Map<String, Object> getParameters() {
        return parameters;
    }
    
    public void setParameter(String key, Object value) {
        parameters.put(key, value);
    }
    
    public Object getParameter(String key) {
        return parameters.get(key);
    }
    
    public String toJson() {
        Json json = new Json();
        return json.toJson(this);
    }
    
    public static TurnConfig fromJson(String jsonString) {
        Json json = new Json();
        return json.fromJson(TurnConfig.class, jsonString);
    }
}