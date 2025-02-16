package ex22a;

public class Player {
    String name;
    Color color;

    public Player(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public Move think(State state) {
        var move = search(state);
        if (move != null) {
            move.color = this.color;
        }
        return move;
    }

    Move search(State state) {
        return null; // デフォルトでは何も返さない
    }
}
