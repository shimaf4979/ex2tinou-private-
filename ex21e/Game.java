package ex21e;

import static ex21e.Color.*;
import java.util.*;

public class Game {
    public static void main(String[] args) {
        var analyzer = new NimAnalysis();
        analyzer.analyze(20);
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