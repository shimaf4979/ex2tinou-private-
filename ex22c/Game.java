package ex22c;

import static ex22c.Color.*;
import java.util.*;

public class Game {
    // public static void main(String[] args) {
    // var p0 = new HumanPlayer();
    // var p1 = new AlphaBetaPlayer(new Eval(), 2);
    // var g = new Game(p0, p1);
    // g.play();
    // g.printResult();
    // }
    public static void main(String[] args) {
        var p0 = new MyPlayerV1(new Eval(), 6);
        var p1 = new MyPlayerV1Iterative(new Eval(), 6);

        System.out.println("=== Normal vs Iterative Deepening ===");
        var g = new Game(p0, p1);
        g.play();
        g.printResult();
    }

    State state;
    Map<Color, Player> players;

    public Game(Player black, Player white) {
        this.state = new State();
        black.color = BLACK;
        white.color = WHITE;
        this.players = Map.of(
                BLACK, black,
                WHITE, white,
                NONE, new Player("draw"));
    }

    void play() {
        while (!this.state.isGoal()) {
            var player = this.players.get(this.state.color);
            var move = player.think(this.state.clone());
            this.state = this.state.perform(move);
            System.out.println(this.state);
            System.out.println("---------");
        }
    }

    void printResult() {
        System.out.println("winner: " + this.players.get(this.state.winner()));
    }
}