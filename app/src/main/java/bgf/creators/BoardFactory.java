package bgf.creators;

import java.util.Map;

import com.badlogic.gdx.graphics.Texture;

import bgf.gdx.components.Board;
import bgf.gdx.components.Tile;

// BoardFactory.java - Fábrica para criar tabuleiros a partir de configuração
public class BoardFactory {
    public static Board createBoard(BoardConfig config) {
        Board board = new Board(config.getRows(), config.getCols(), config.getTileSize());
        
        for (BoardConfig.TileConfig tileConfig : config.getTiles()) {
            Tile tile = board.getTileAt(tileConfig.getRow(), tileConfig.getCol());
            
            // Configurar o tile baseado na configuração
            if (tile != null) {
                tile.setType(tileConfig.getType());
                tile.setTexture(new Texture(tileConfig.getTexturePath()));
                
                // Aplicar propriedades adicionais
                for (Map.Entry<String, Object> property : tileConfig.getProperties().entrySet()) {
                    tile.setProperty(property.getKey(), property.getValue());
                }
            }
        }
        
        return board;
    }
}