import java.util.Random;
import java.util.Scanner;

// Main NimGame class showing the Nim game 
class NimGame {
    private int pileSize;
    private NimPlayer player1;
    private NimPlayer player2;
    private int currentPlayer;
    private boolean playAgain;

    // Constructor to initialize the game 
    public NimGame() {
        playAgain = true;
    }

    // Method to start the Nim game
    public void startGame() {
        while (playAgain) {
            initializeGame();
            play();
            replay();
        }
        System.out.println("Thank you for playing!");
    }

    // Method to initialize the game , including setting up players and pile size
    private void initializeGame() {
        pileSize = randomPileSize();

        // Ask the user if they want to play against a computer or with another person
        Scanner scanner = new Scanner(System.in);
        System.out.print("Do you want to play against a computer? (yes/no): ");
        String Computer = scanner.next().toLowerCase();

        // Organize players based on their answer
        if (Computer.equals("yes")) {
            player1 = new NimPlayer(getPlayerName(1), false); // Human player
            player2 = new NimPlayer("Computer", true);  // Computer player
        } else {
            player1 = new NimPlayer(getPlayerName(1), false); // Player 1
            player2 = new NimPlayer(getPlayerName(2), false); // Player 2
        }

        currentPlayer = new Random().nextInt(2) + 1; // Randomly choose player 1 or 2
        System.out.println("New game with pile size: " + pileSize);
        System.out.println(player1.getName() + " vs. " + player2.getName());
    }

    // Method to get the name for a player based on player number
    private String getPlayerName(int playerNumber) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the name for Player " + playerNumber + ": ");
        return scanner.nextLine();
    }

    // Method to get a random pile size between 10 and 50
    private int randomPileSize() {
        return new Random().nextInt(41) + 10;
    }

    // Method to play the Nim game
    private void play() {
        while (pileSize > 0) {
            gameStatus();
            int pieces;

            // Check if the current player is set to play against computer
            if (getCurrentPlayer().computerAuto()) {
                pieces = getCurrentPlayer().autoplay(Math.min(pileSize, pileSize / 2));
            } else {
                // Ask the user if they want to use a reverse move or make a normal move
                if (!getCurrentPlayer().hasUsedReverse()) {
                    System.out.print("Do you want to use your reverse move? (yes/no): ");
                    String useReverse = new Scanner(System.in).next().toLowerCase();
                    if (useReverse.equals("yes")) {
                        getCurrentPlayer().useReverse(this);
                        continue; // Skip normal turn if a reverse move is used
                    }
                }

                pieces = input();
            }

            // Update the pile size and switch to the next player
            pileSize -= pieces;
            switchPlayer();
        }
        winner();
    }

    // Method to display the where in the game they are, including the pile size and current player's turn
    private void gameStatus() {
        System.out.println("Current pile size: " + pileSize);
        System.out.println("It's " + getCurrentPlayer().getName() + "'s turn");
    }

    // Getter method to get the current player
    private NimPlayer getCurrentPlayer() {
        return (currentPlayer == 1) ? player1 : player2;
    }

    // Method to get the number of pieces the current player wants to remove from the pile
    private int input() {
        Scanner scanner = new Scanner(System.in);

        int maxPieces = Math.min(pileSize, pileSize / 2);
        int pieces;
        maxPieces = Math.max(maxPieces, 1);

        while (true) {
            System.out.print("Pieces to take (1-" + maxPieces + "): ");
            pieces = scanner.nextInt();
            if (pieces >= 1 && pieces <= maxPieces) {
                break;
            } else {
                System.out.println("WRONG: Enter a number between 1 and " + maxPieces + ".");
            }
        }

        return pieces;
    }

    // Method to switch players
    private void switchPlayer() {
        currentPlayer = (currentPlayer == 1) ? 2 : 1;
    }

    // Method to show the winner of the game
    private void winner() {
        System.out.println("The last player to take a piece, " + getCurrentPlayer().getName() + ", lost LOL!");
    }

    // Method to ask the user if they want to play the game again/ replay
    private void replay() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Do you want to play again? (yes/no): ");
        String response = scanner.next().toLowerCase();
        playAgain = response.equals("yes");

        // Reset reverse move for the next game
        player1.resetReverse();
        player2.resetReverse();
    }

    // Method to reverse the order players
    public void reverseOrder() {
        System.out.println("Order of play reversed!");
        // Switch the players
        NimPlayer temp = player1;
        player1 = player2;
        player2 = temp;
    }
}