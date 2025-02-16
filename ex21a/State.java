package ex21a;

import java.util.*;

class State {
    static Map<String, List<String>> childNodeLists = Map.of(
            "root", List.of("B", "C", "D"),
            "B", List.of("B1", "B2", "B3"),
            "C", List.of("C1", "C2", "C3"),
            "D", List.of("D1", "D2"));

    static Map<String, Float> values = Map.of(
            "B1", -1.0f,
            "B2", -31.0f,
            "B3", -16.0f,
            "C1", -38.0f,
            "C2", -23.0f,
            "C3", -50.0f,
            "D1", -9.0f,
            "D2", 6.0f);

    String current;

    State(String current) {
        this.current = current;
    }

    @Override
    public String toString() {
        return this.current;
    }

    boolean isGoal() {
        return getMoves().isEmpty();
    }

    List<String> getMoves() {
        return State.childNodeLists.getOrDefault(this.current, new ArrayList<>());
    }

    State perform(String move) {
        return new State(move);
    }
}

class Eval {
    float value(State state) {
        return State.values.getOrDefault(state.current, Float.NaN);
    }
}
