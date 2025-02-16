package ex21e;

public class Move {
    int removal;
    Color color;

    public Move(int removal, Color color) {
        this.removal = removal;
        this.color = color;
    }

    @Override
    public String toString() {
        return String.format("took %d stone(s)", this.removal);
    }
}