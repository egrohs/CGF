package bgf.gdx.components;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

// DebugOverlay.java - Sobreposição de debug
public class DebugOverlay extends Stage {
    private Label debugLabel;
    private boolean visible;
    
    public DebugOverlay() {
        debugLabel = new Label("", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        debugLabel.setPosition(10, Gdx.graphics.getHeight() - 30);
        addActor(debugLabel);
        
        visible = true;
    }
    
    @Override
    public void draw() {
        if (visible) {
            super.draw();
        }
    }
    
    public void updateDebugInfo(String info) {
        debugLabel.setText(info);
    }
    
    public void toggleVisibility() {
        visible = !visible;
    }
}