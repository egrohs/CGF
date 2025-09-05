package bgf;

import com.badlogic.gdx.graphics.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Representa um jogador no jogo de tabuleiro.
 * Gerencia informações do jogador, recursos, pontuação e estado.
 */
public class Player {
    private String id;
    private String name;
    private Color color;
    private boolean isHuman;
    private boolean isActive;
    private int score;
    private Map<String, Integer> resources;
    private List<String> ownedPieces;
    private PlayerType type;
    
    public enum PlayerType {
        HUMAN, AI_EASY, AI_MEDIUM, AI_HARD, REMOTE
    }
    
    /**
     * Construtor completo para criar um jogador.
     */
    public Player(String id, String name, Color color, PlayerType type) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.type = type;
        this.isHuman = (type == PlayerType.HUMAN);
        this.isActive = true;
        this.score = 0;
        this.resources = new HashMap<>();
        this.ownedPieces = new ArrayList<>();
    }
    
    /**
     * Construtor simplificado para criação rápida de jogadores.
     */
    public Player(String name, Color color) {
        this(generateId(), name, color, PlayerType.HUMAN);
    }
    
    /**
     * Gera um ID único para o jogador.
     */
    private static String generateId() {
        return "player_" + System.currentTimeMillis() + "_" + (int)(Math.random() * 1000);
    }
    
    // Getters e Setters
    public String getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public Color getColor() {
        return color;
    }
    
    public void setColor(Color color) {
        this.color = color;
    }
    
    public boolean isHuman() {
        return isHuman;
    }
    
    public void setHuman(boolean human) {
        isHuman = human;
        this.type = human ? PlayerType.HUMAN : PlayerType.AI_EASY;
    }
    
    public PlayerType getType() {
        return type;
    }
    
    public void setType(PlayerType type) {
        this.type = type;
        this.isHuman = (type == PlayerType.HUMAN);
    }
    
    public boolean isActive() {
        return isActive;
    }
    
    public void setActive(boolean active) {
        isActive = active;
    }
    
    public int getScore() {
        return score;
    }
    
    public void setScore(int score) {
        this.score = score;
    }
    
    public void addScore(int points) {
        this.score += points;
    }
    
    public void subtractScore(int points) {
        this.score = Math.max(0, this.score - points);
    }
    
    // Gerenciamento de recursos
    public int getResource(String resourceName) {
        return resources.getOrDefault(resourceName, 0);
    }
    
    public void setResource(String resourceName, int amount) {
        resources.put(resourceName, Math.max(0, amount));
    }
    
    public void addResource(String resourceName, int amount) {
        int current = resources.getOrDefault(resourceName, 0);
        resources.put(resourceName, current + amount);
    }
    
    public boolean subtractResource(String resourceName, int amount) {
        int current = resources.getOrDefault(resourceName, 0);
        if (current >= amount) {
            resources.put(resourceName, current - amount);
            return true;
        }
        return false;
    }
    
    public boolean hasResources(Map<String, Integer> requiredResources) {
        for (Map.Entry<String, Integer> entry : requiredResources.entrySet()) {
            if (getResource(entry.getKey()) < entry.getValue()) {
                return false;
            }
        }
        return true;
    }
    
    public Map<String, Integer> getAllResources() {
        return new HashMap<>(resources);
    }
    
    // Gerenciamento de peças
    public List<String> getOwnedPieces() {
        return new ArrayList<>(ownedPieces);
    }
    
    public void addPiece(String pieceId) {
        if (!ownedPieces.contains(pieceId)) {
            ownedPieces.add(pieceId);
        }
    }
    
    public void removePiece(String pieceId) {
        ownedPieces.remove(pieceId);
    }
    
    public boolean ownsPiece(String pieceId) {
        return ownedPieces.contains(pieceId);
    }
    
    public int getPieceCount() {
        return ownedPieces.size();
    }
    
    /**
     * Verifica se é a vez deste jogador.
     */
    public boolean isCurrentTurn() {
        // Esta verificação normalmente seria feita através do TurnManager
        // Aqui é apenas uma placeholder para a lógica real
        return isActive;
    }
    
    /**
     * Prepara o jogador para um novo jogo.
     */
    public void reset() {
        this.score = 0;
        this.resources.clear();
        this.ownedPieces.clear();
        this.isActive = true;
    }
    
    /**
     * Serializa o estado do jogador para JSON.
     */
    public String toJson() {
        StringBuilder json = new StringBuilder();
        json.append("{");
        json.append("\"id\":\"").append(id).append("\",");
        json.append("\"name\":\"").append(name).append("\",");
        json.append("\"color\":{")
            .append("\"r\":").append(color.r).append(",")
            .append("\"g\":").append(color.g).append(",")
            .append("\"b\":").append(color.b).append(",")
            .append("\"a\":").append(color.a)
            .append("},");
        json.append("\"type\":\"").append(type.name()).append("\",");
        json.append("\"isActive\":").append(isActive).append(",");
        json.append("\"score\":").append(score).append(",");
        
        // Serializar recursos
        json.append("\"resources\":{");
        boolean firstResource = true;
        for (Map.Entry<String, Integer> entry : resources.entrySet()) {
            if (!firstResource) json.append(",");
            json.append("\"").append(entry.getKey()).append("\":").append(entry.getValue());
            firstResource = false;
        }
        json.append("},");
        
        // Serializar peças
        json.append("\"ownedPieces\":[");
        boolean firstPiece = true;
        for (String pieceId : ownedPieces) {
            if (!firstPiece) json.append(",");
            json.append("\"").append(pieceId).append("\"");
            firstPiece = false;
        }
        json.append("]");
        
        json.append("}");
        return json.toString();
    }
    
    /**
     * Desserializa o estado do jogador a partir de JSON.
     */
    public static Player fromJson(String jsonString) {
        // Implementação simplificada - na prática, usaríamos uma biblioteca JSON
        // como a fornecida pelo LibGDX (JsonReader) ou Gson
        
        // Esta é uma implementação placeholder
        // Em um cenário real, faríamos o parsing completo do JSON
        
        // Para exemplo, vamos retornar um jogador padrão
        return new Player("default_player", "Player", Color.WHITE, PlayerType.HUMAN);
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return id.equals(player.id);
    }
    
    @Override
    public int hashCode() {
        return id.hashCode();
    }
    
    @Override
    public String toString() {
        return name + " (Score: " + score + ")";
    }
}