package ex22d;

public class Eval {
    public float value(State state) {
        return -1025 * state.b3 +
                511 * state.a3 -
                63 * state.b2 +
                31 * state.a2 -
                15 * state.b1 +
                7 * state.a1;
    }
}
