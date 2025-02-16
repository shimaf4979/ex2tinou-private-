package ex21b;

public class Eval {

    public float value(State state) {
        var s = state.winner().getSign();

        return state.isGoal() ? Float.POSITIVE_INFINITY * s / state.numStones : 0;
    }
}
