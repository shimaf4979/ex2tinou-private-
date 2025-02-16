package ex21c;

import static ex21c.Color.*;
import java.util.*;

public class Game {
    public static void main(String[] args) {
        for (int numStones = 1; numStones <= 20; numStones++) {
            var p0 = new MyAlphaBetaPlayer(new Eval(), 10);
            var p1 = new MyAlphaBetaPlayer(new Eval(), 10);
            Game g = new Game(numStones, p0, p1);
            g.play();
            g.printResult();
        }
    }

    State state;
    Map<Color, Player> players;

    public Game(int numStones, Player black, Player white) {
        black.color = BLACK;
        white.color = WHITE;
        this.state = new State(numStones);
        this.players = Map.of(BLACK, black, WHITE, white);
    }

    void play() {
        System.out.printf("==== %d stone(s) ====\n", state.numStones);
        while (!this.state.isGoal()) {
            var player = this.players.get(this.state.color);
            var move = player.think(this.state.clone());
            var next = this.state.perform(move);
            System.out.printf("%s -> %s | %s %s.\n", state, next, player, move);
            this.state = next;
        }
    }

    void printResult() {
        System.out.println("Winner: " + this.players.get(this.state.winner()));
        System.out.println();
    }
}