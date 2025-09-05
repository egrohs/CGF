package ttgdx.libgdx.components;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;

// DiceWidget.java - Componente de dados
public class DiceWidget extends Group {
    private Image diceImage;
    private int currentValue;
    private Array<Texture> diceFaces;

    public DiceWidget(float size) {
        setSize(size, size);
        loadDiceFaces();
        setRandomValue();
    }

    private void loadDiceFaces() {
        diceFaces = new Array<>(6);
        for (int i = 1; i <= 6; i++) {
            diceFaces.add(new Texture("dice_" + i + ".png"));
        }
    }

    public int roll() {
        // Animação de rolagem
        Action rollAction = Actions.sequence(
                Actions.repeat(10, Actions.run(() -> {
                    setRandomValue();
                })),
                Actions.run(() -> {
                    setRandomValue(); // Valor final
                }));

        addAction(rollAction);
        return currentValue;
    }

    private void setRandomValue() {
        currentValue = MathUtils.random(1, 6);
        Texture texture = diceFaces.get(currentValue - 1);
        TextureRegion textureRegion = new TextureRegion(texture);
        Drawable drawable = new TextureRegionDrawable(textureRegion);
        diceImage = new Image(drawable);
    }

    public int getValue() {
        return currentValue;
    }
}