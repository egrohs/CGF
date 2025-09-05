package ttgdx.rules;

public interface GameRule {
    boolean validate(GameAction action, GameState state);
}