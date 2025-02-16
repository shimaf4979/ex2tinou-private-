package ex21e;

enum GameResult {
    WIN, // 勝ち
    LOSE, // 負け
    DRAW; // 引き分け（この問題では発生しない）

    @Override
    public String toString() {
        switch (this) {
            case WIN:
                return "先手必勝";
            case LOSE:
                return "後手必勝";
            case DRAW:
                return "引き分け";
            default:
                return "不明";
        }
    }
}
