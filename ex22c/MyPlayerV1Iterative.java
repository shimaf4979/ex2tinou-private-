package ex22c;

import static java.lang.Float.*;

public class MyPlayerV1Iterative extends Player {
    Eval eval;
    int depthLimit;
    Move move;
    int visitedNodes;

    public MyPlayerV1Iterative(Eval eval, int depthLimit) {
        super("MyPlayerV1Iterative");
        this.eval = eval;
        this.depthLimit = depthLimit;
    }

    @Override
    Move search(State state) {
        this.visitedNodes = 0;
        if (this.color == Color.WHITE) {
            state = flipState(state);
        }

        // 反復深化探索
        for (int depth = 1; depth <= this.depthLimit; depth++) {
            maxSearch(state, NEGATIVE_INFINITY, POSITIVE_INFINITY, depth);
        }

        if (this.color == Color.WHITE) {
            this.move = this.move.flipped();
        }

        System.out.println("Visited nodes (Iterative): " + this.visitedNodes);
        return this.move;
    }

    float maxSearch(State state, float alpha, float beta, int depthLimit) {
        this.visitedNodes++;

        if (isTerminal(state, depthLimit)) {
            return this.eval.value(state);
        }

        float v = NEGATIVE_INFINITY;
        for (var move : state.getMoves()) {
            var next = state.perform(move);
            var v0 = minSearch(next, alpha, beta, depthLimit - 1);
            if (depthLimit == this.depthLimit && v0 > v) {
                this.move = move;
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

        float v = POSITIVE_INFINITY;
        for (var move : state.getMoves()) {
            var next = state.perform(move);
            var v0 = maxSearch(next, alpha, beta, depthLimit - 1);
            if (v0 <= alpha) {
                return v0;
            }
            v = Math.min(v, v0);
            beta = Math.min(beta, v);
        }
        return v;
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
}