package ex21b;

import java.util.Random;

public class RandomPlayer extends Player {
    public RandomPlayer() {
        super("Random");
    }

    @Override
    Move search(State state) {
        var moves = state.getMoves();
        if (moves.isEmpty()) {
            throw new IllegalStateException("No available moves for the current state.");
        }
        int index = new Random().nextInt(moves.size());
        return moves.get(index);
    }
}
