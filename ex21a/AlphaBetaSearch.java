package ex21a;

class AlphaBetaSearch {
    public static void main(String[] args) {
        var player = new AlphaBetaSearch(new Eval(), 2);
        var value = player.search(new State("root"));
        System.out.println("最終的な評価値: " + value);
    }

    Eval eval;
    int depthLimit;

    AlphaBetaSearch(Eval eval, int depthLimit) {
        this.eval = eval;
        this.depthLimit = depthLimit;
    }

    float search(State state) {
        return maxSearch(state, Float.NEGATIVE_INFINITY, Float.POSITIVE_INFINITY, 0);
    }

    float maxSearch(State state, float alpha, float beta, int depth) {
        if (isTerminal(state, depth)) {
            var value = this.eval.value(state);
            System.out.println("終端ノード " + state + " の値: " + value);
            return value;
        }

        var v = Float.NEGATIVE_INFINITY;
        System.out.println("MAXノード " + state + " の探索開始 α=" + alpha + " β=" + beta);

        for (var move : state.getMoves()) {
            var next = state.perform(move);
            var v0 = minSearch(next, alpha, beta, depth + 1);
            v = Math.max(v, v0);
            if (v >= beta) {
                System.out.println("βカット発生: " + state + " の残りの子ノードは探索しない");
                break;
            }
            alpha = Math.max(alpha, v);
        }
        System.out.println("MAXノード " + state + " の評価値: " + v);
        return v;
    }

    float minSearch(State state, float alpha, float beta, int depth) {
        if (isTerminal(state, depth)) {
            var value = this.eval.value(state);
            System.out.println("終端ノード " + state + " の値: " + value);
            return value;
        }

        var v = Float.POSITIVE_INFINITY;
        System.out.println("MINノード " + state + " の探索開始 α=" + alpha + " β=" + beta);

        for (var move : state.getMoves()) {
            var next = state.perform(move);
            var v0 = maxSearch(next, alpha, beta, depth + 1);
            v = Math.min(v, v0);
            if (v <= alpha) {
                System.out.println("αカット発生: " + state + " の残りの子ノードは探索しない");
                break;
            }
            beta = Math.min(beta, v);
        }
        System.out.println("MINノード " + state + " の評価値: " + v);
        return v;
    }

    boolean isTerminal(State state, int depth) {
        return state.isGoal() || depth >= this.depthLimit;
    }
}