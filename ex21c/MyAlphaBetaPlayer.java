package ex21c;

import static java.lang.Float.*;

public class MyAlphaBetaPlayer extends Player {
    Eval eval;
    int depthLimit;
    Move move;
    int visitedNodes;
    int cutNodes;

    public MyAlphaBetaPlayer(Eval eval, int depthLimit) {
        super("AlphaBeta" + depthLimit);
        this.eval = eval;
        this.depthLimit = depthLimit;
        this.visitedNodes = 0;
        this.cutNodes = 0;
    }

    Move search(State state) {
        this.visitedNodes = 0;
        this.cutNodes = 0;
        this.move = new Move(Math.min(3, state.numStones), state.color);
        if (this.color == Color.BLACK) {
            maxSearch(state, NEGATIVE_INFINITY, POSITIVE_INFINITY, 0);
        } else {
            minSearch(state, NEGATIVE_INFINITY, POSITIVE_INFINITY, 0);
        }
        System.out.println("訪問ノード数: " + this.visitedNodes);
        System.out.println("枝刈り回数: " + this.cutNodes);
        return this.move;
    }

    float maxSearch(State state, float alpha, float beta, int depth) {
        visitedNodes++;
        if (isTerminal(state, depth)) {
            return this.eval.value(state);
        }

        var v = NEGATIVE_INFINITY;
        for (var move : state.getMoves()) {
            var next = state.perform(move);
            var v0 = minSearch(next, alpha, beta, depth + 1);
            if (depth == 0 && v0 > v) {
                this.move = move;
            }
            v = Math.max(v, v0);
            alpha = Math.max(alpha, v);
            if (beta <= alpha) {
                System.out.println("βカット発生: " + state + " の残りの子ノードは探索しない");
                cutNodes++;
                break;
            }
        }
        return v;
    }

    float minSearch(State state, float alpha, float beta, int depth) {
        visitedNodes++;
        if (isTerminal(state, depth)) {
            return this.eval.value(state);
        }

        var v = POSITIVE_INFINITY;
        for (var move : state.getMoves()) {
            var next = state.perform(move);
            var v0 = maxSearch(next, alpha, beta, depth + 1);
            if (depth == 0 && v0 < v) {
                this.move = move;
            }
            v = Math.min(v, v0);
            beta = Math.min(beta, v);
            if (beta <= alpha) {
                System.out.println("αカット発生: " + state + " の残りの子ノードは探索しない");
                cutNodes++;
                break;
            }
        }
        return v;
    }

    boolean isTerminal(State state, int depth) {
        return state.isGoal() || depth >= this.depthLimit;
    }
}