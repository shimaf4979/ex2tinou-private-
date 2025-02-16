package ex22c;

import java.util.Random;

public class RandomPlayer extends Player {

    public RandomPlayer() {
        super("Random");
    }

    @Override
    Move search(State state) {
        var moves = state.getMoves();
        if (moves.isEmpty()) {
            return null; // 取れる手がない場合
        }
        int index = new Random().nextInt(moves.size());
        return moves.get(index);
    }
}
