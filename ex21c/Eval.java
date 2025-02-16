package ex21c;

public class Eval {
    public float value(State state) {
        var s = state.winner().getSign();
        return state.isGoal() ? Float.POSITIVE_INFINITY * s : 0;
    }
}