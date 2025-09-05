package ttgdx.creators;

import java.util.List;
import java.util.Map;

import lombok.Data;

// BoardConfig.java - Configuração do tabuleiro para serialização
@Data
public class BoardConfig {
    private int rows;
    private int cols;
    private float tileSize;
    private List<TileConfig> tiles;
    
    // Getters e setters...
    
    public static BoardConfig loadFromJson(String filePath) {
        // Carregar configuração do tabuleiro de arquivo JSON
        return null; // Implementação real
    }
    @Data
    public static class TileConfig {
        private int row;
        private int col;
        private String type;
        private String texturePath;
        private Map<String, Object> properties;
        
        // Getters e setters...
    }
}