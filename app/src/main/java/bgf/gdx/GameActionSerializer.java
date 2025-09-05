package bgf.gdx;

import com.badlogic.gdx.utils.Json;

import bgf.rules.GameAction;

// GameActionSerializer.java - Serializador de ações para rede
public class GameActionSerializer {
    public static String serialize(GameAction action) {
        // Serializar ação para JSON
        Json json = new Json();
        return json.toJson(action);
    }
    
    public static GameAction deserialize(String data) {
        // Desserializar ação de JSON
        Json json = new Json();
        return json.fromJson(GameAction.class, data);
    }
}