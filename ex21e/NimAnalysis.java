package ex21e;

import java.util.*;

public class NimAnalysis {
    private Map<Integer, GameResult> memo;
    private Map<Integer, Integer> bestMove;

    public NimAnalysis() {
        this.memo = new HashMap<>();
        this.bestMove = new HashMap<>();
    }

    public void analyze(int maxStones) {
        System.out.println("石取りゲームの完全解析：");
        System.out.println("石の数\t結果\t最善手");
        System.out.println("------------------------");

        for (int stones = 1; stones <= maxStones; stones++) {
            GameResult result = analyzePosition(stones);
            Integer best = bestMove.get(stones);
            String bestStr = best != null ? best.toString() : "-";
            System.out.printf("%d\t%s\t%s個取る\n", stones, result, bestStr);
        }
    }

    private GameResult analyzePosition(int stones) {
        // メモ化されている場合
        if (memo.containsKey(stones)) {
            System.out.printf("石%d個の状態は既に計算済み: %s\n",
                    stones, memo.get(stones));
            return memo.get(stones);
        }

        // 1個の場合
        if (stones == 1) {
            System.out.println("石1個の状態: 必ず取らないといけないので負け");
            memo.put(1, GameResult.LOSE);
            return GameResult.LOSE;
        }

        System.out.printf("\n石%d個の状態を解析中...\n", stones);
        // 可能な手を試す（1～3個）
        for (int take = 1; take <= Math.min(3, stones); take++) {
            int nextStones = stones - take;
            System.out.printf("  %d個取る手を試す -> 残り%d個\n", take, nextStones);
            GameResult opponentResult = analyzePosition(nextStones);
            System.out.printf("  %d個取った後の相手の結果: %s\n", take, opponentResult);

            // 相手が負ける手があれば、その手を選んで勝ち
            if (opponentResult == GameResult.LOSE) {
                System.out.printf("石%d個の状態で%d個取れば勝てる\n", stones, take);
                memo.put(stones, GameResult.WIN);
                bestMove.put(stones, take);
                return GameResult.WIN;
            }
        }

        // 全ての手が相手の勝ちにつながる場合
        System.out.printf("石%d個の状態はどう取っても負け\n", stones);
        memo.put(stones, GameResult.LOSE);
        return GameResult.LOSE;
    }

    public static void main(String[] args) {
        NimAnalysis analyzer = new NimAnalysis();
        analyzer.analyze(20);

        // 戦略の説明を出力
        System.out.println("\n石取りゲームの解析結果の説明：");
        System.out.println("- 最後の石を取ったプレイヤーが負け");
        System.out.println("- 適切な手を選ぶことで、相手に最後の石を取らせることができる場合は先手必勝");
        System.out.println("- それ以外の場合は後手必勝");
    }
}