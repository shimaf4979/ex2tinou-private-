package ex22a;

import static java.lang.Float.*;

public class AlphaBetaPlayer extends Player {
    Eval eval;
    int depthLimit;
    Move move;

    public AlphaBetaPlayer(Eval eval, int depthLimit) {
        super("AlphaBeta" + depthLimit);
        this.eval = eval;
        this.depthLimit = depthLimit;
    }

    @Override
    Move search(State state) {
        if (this.color == Color.BLACK) {
            maxSearch(state, NEGATIVE_INFINITY, POSITIVE_INFINITY, this.depthLimit);
        } else {
            minSearch(state, NEGATIVE_INFINITY, POSITIVE_INFINITY, this.depthLimit);
        }
        return this.move;
    }

    float maxSearch(State state, float alpha, float beta, int depthLimit) {
        if (isTerminal(state, depthLimit)) {
            return this.eval.value(state);
        }

        var v = NEGATIVE_INFINITY;
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
        if (isTerminal(state, depthLimit)) {
            return this.eval.value(state);
        }

        var v = POSITIVE_INFINITY;
        for (var move : state.getMoves()) {
            var next = state.perform(move);
            var v0 = maxSearch(next, alpha, beta, depthLimit - 1);
            if (depthLimit == this.depthLimit && v0 < v) {
                this.move = move;
            }
            if (v0 <= alpha) {
                return v0;
            }
            v = Math.min(v, v0);
            beta = Math.min(beta, v);
        }
        return v;
    }

    boolean isTerminal(State state, int depthLimit) {
        return state.isGoal() || depthLimit <= 0;
    }
}
