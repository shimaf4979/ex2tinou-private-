package ex22c;

public enum Color {
    BLACK(1, "o"),
    WHITE(-1, "X"),
    NONE(0, " ");

    private int sign;
    private String symbol;

    private Color(int sign, String symbol) {
        this.sign = sign;
        this.symbol = symbol;
    }

    public int getSign() {
        return this.sign;
    }

    public Color flipped() {
        switch (this) {
            case BLACK:
                return WHITE;
            case WHITE:
                return BLACK;
            default:
                return NONE;
        }
    }

    @Override
    public String toString() {
        return this.symbol;
    }
}
