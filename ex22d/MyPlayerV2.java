package ex22d;

import static java.lang.Float.*;
import java.util.*;

public class MyPlayerV2 extends Player {
    Eval eval;
    int depthLimit;
    Move move;
    int visitedNodes;

    public MyPlayerV2(Eval eval, int depthLimit) {
        super("MyPlayerV2");
        this.eval = eval;
        this.depthLimit = depthLimit;
    }

    @Override
    Move search(State state) {
        this.visitedNodes = 0;
        if (this.color == Color.WHITE) {
            state = flipState(state);
        }

        maxSearch(state, NEGATIVE_INFINITY, POSITIVE_INFINITY, this.depthLimit);

        if (this.color == Color.WHITE) {
            this.move = this.move.flipped();
        }

        System.out.println("Visited nodes: " + this.visitedNodes);
        return this.move;
    }

    float maxSearch(State state, float alpha, float beta, int depthLimit) {
        this.visitedNodes++;

        if (isTerminal(state, depthLimit)) {
            return this.eval.value(state);
        }

        var v = NEGATIVE_INFINITY;
        var moves = orderMoves(state, true); // true for maximizing player

        for (var moveWithValue : moves) {
            var next = state.perform(moveWithValue.move);
            var v0 = minSearch(next, alpha, beta, depthLimit - 1);
            if (depthLimit == this.depthLimit && v0 > v) {
                this.move = moveWithValue.move;
            }
            if (v0 >= beta) {
                return v0;
            }
            v = Math.max(v, v0);
            alpha = Math.max(alpha, v);
        }
        return v;
    }

    float minSearch(State state, float alpha, float beta, int depthLimit) {
        this.visitedNodes++;

        if (isTerminal(state, depthLimit)) {
            return this.eval.value(state);
        }

        var v = POSITIVE_INFINITY;
        var moves = orderMoves(state, false); // false for minimizing player

        for (var moveWithValue : moves) {
            var next = state.perform(moveWithValue.move);
            var v0 = maxSearch(next, alpha, beta, depthLimit - 1);
            if (v0 <= alpha) {
                return v0;
            }
            v = Math.min(v, v0);
            beta = Math.min(beta, v);
        }
        return v;
    }

    private List<MoveWithValue> orderMoves(State state, boolean isMaximizing) {
        var movesWithValues = new ArrayList<MoveWithValue>();

        for (var move : state.getMoves()) {
            var next = state.perform(move);
            var value = this.eval.value(next);
            if (!isMaximizing) {
                value = -value; // Negate value for minimizing player
            }
            movesWithValues.add(new MoveWithValue(move, value));
        }

        // Sort moves by value in descending order
        Collections.sort(movesWithValues, (a, b) -> Float.compare(b.value, a.value));

        return movesWithValues;
    }

    private State flipState(State state) {
        var flipped = state.clone();
        for (int i = 0; i < State.LENGTH; i++) {
            if (flipped.board[i] != Color.NONE) {
                flipped.board[i] = flipped.board[i].flipped();
            }
        }
        flipped.color = flipped.color.flipped();
        flipped.count();
        return flipped;
    }

    boolean isTerminal(State state, int depthLimit) {
        return state.isGoal() || depthLimit <= 0;
    }

    // Helper class to store a move with its evaluation value
    private static class MoveWithValue {
        Move move;
        float value;

        MoveWithValue(Move move, float value) {
            this.move = move;
            this.value = value;
        }
    }
}