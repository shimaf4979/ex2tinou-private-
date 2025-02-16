package ex22b;

import java.util.Scanner;

public class HumanPlayer extends Player {
    private Scanner scanner;

    public HumanPlayer() {
        super("Human");
        scanner = new Scanner(System.in);
    }

    @Override
    Move search(State state) {
        System.out.println("Enter row (0-2) and column (0-2) separated by space:");
        int row = scanner.nextInt();
        int col = scanner.nextInt();

        while (row < 0 || row >= State.SIZE || col < 0 || col >= State.SIZE ||
                state.board[row * State.SIZE + col] != Color.NONE) {
            System.out.println("Invalid move. Try again.");
            System.out.println("Enter row (0-2) and column (0-2) separated by space:");
            row = scanner.nextInt();
            col = scanner.nextInt();
        }

        return new Move(row * State.SIZE + col, this.color);
    }
}