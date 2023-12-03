import java.util.Random;
import java.util.Scanner;

// Player class representing each player in the Nim game
class NimPlayer {
    private String name;
    private boolean specialMoveUsed;
    private boolean isAutoplay;

    // Constructor to initialize player with a name and autoplay status
    public NimPlayer(String name, boolean isAutoplay) {
        this.name = name;
        this.isAutoplay = isAutoplay;
        this.specialMoveUsed = false;
    }

    // Getter method to retrieve the player's name
    public String getName() {
        return name;
    }

    // Getter method to check if the player has used a special move
    public boolean hasUsedSpecialMove() {
        return specialMoveUsed;
    }

    // Method for a player to use a special move (in this case, reversing the order of play)
    public void useSpecialMove(NimGame game) {
        specialMoveUsed = true;
        System.out.println(name + " used a special move: Reverse!");
        game.reverseOrder();
    }

    // Method to reset the special move usage for a player
    public void resetSpecialMove() {
        specialMoveUsed = false;
    }

    // Getter method to check if the player is set to autoplay
    public boolean isAutoplay() {
        return isAutoplay;
    }

    // Method for autoplay logic to make a move (in this case, randomly selecting pieces to take)
    public int autoplayMove(int maxPieces) {
        // Simple autoplay logic: Randomly select the number of pieces to take
        return new Random().nextInt(maxPieces) + 1;
    }
}

// Main NimGame class representing the Nim game itself
class NimGame {
    private int pileSize;
    private NimPlayer player1;
    private NimPlayer player2;
    private int currentPlayer;
    private boolean playAgain;

    // Constructor to initialize the game state
    public NimGame() {
        playAgain = true;
    }

    // Method to start the Nim game
    public void startGame() {
        while (playAgain) {
            initializeGame();
            play();
            askForPlayAgain();
        }
        System.out.println("Thanks for playing!");
    }

    // Method to initialize the game state, including setting up players and pile size
    private void initializeGame() {
        pileSize = generateRandomPileSize();

        // Ask the user if they want to play against a computer or with another person
        Scanner scanner = new Scanner(System.in);
        System.out.print("Do you want to play against a computer? (yes/no): ");
        String vsComputer = scanner.next().toLowerCase();

        // Set up players based on user input
        if (vsComputer.equals("yes")) {
            player1 = new NimPlayer(getPlayerName(1), false); // Human player
            player2 = new NimPlayer("Computer", true);  // Computer player
        } else {
            player1 = new NimPlayer(getPlayerName(1), false); // Player 1
            player2 = new NimPlayer(getPlayerName(2), false); // Player 2
        }

        currentPlayer = new Random().nextInt(2) + 1; // Randomly choose player 1 or 2
        System.out.println("Starting a new game with pile size: " + pileSize);
        System.out.println(player1.getName() + " vs. " + player2.getName());
    }

    // Method to get the name for a player based on player number
    private String getPlayerName(int playerNumber) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the name for Player " + playerNumber + ": ");
        return scanner.nextLine();
    }

    // Method to generate a random pile size between 10 and 50
    private int generateRandomPileSize() {
        return new Random().nextInt(41) + 10;
    }

    // Method to play the Nim game
    private void play() {
        while (pileSize > 0) {
            displayGameStatus();
            int piecesToTake;

            // Check if the current player is set to autoplay
            if (getCurrentPlayer().isAutoplay()) {
                piecesToTake = getCurrentPlayer().autoplayMove(Math.min(pileSize, pileSize / 2));
            } else {
                // Ask the user if they want to use a special move or make a regular move
                if (!getCurrentPlayer().hasUsedSpecialMove()) {
                    System.out.print("Do you want to use your special move? (yes/no): ");
                    String useSpecialMove = new Scanner(System.in).next().toLowerCase();
                    if (useSpecialMove.equals("yes")) {
                        getCurrentPlayer().useSpecialMove(this);
                        continue; // Skip regular turn if a special move is used
                    }
                }

                piecesToTake = getPlayerInput();
            }

            // Update the pile size and switch to the next player
            pileSize -= piecesToTake;
            switchPlayer();
        }
        declareWinner();
    }

    // Method to display the current game status, including the pile size and current player's turn
    private void displayGameStatus() {
        System.out.println("Current pile size: " + pileSize);
        System.out.println("It's " + getCurrentPlayer().getName() + "'s turn");
    }

    // Getter method to retrieve the current player
    private NimPlayer getCurrentPlayer() {
        return (currentPlayer == 1) ? player1 : player2;
    }

    // Method to get the number of pieces the current player wants to take from the pile
    private int getPlayerInput() {
        Scanner scanner = new Scanner(System.in);

        int maxPieces = Math.min(pileSize, pileSize / 2);
        int piecesToTake;
        maxPieces = Math.max(maxPieces, 1);

        while (true) {
            System.out.print("Enter the number of pieces to take (1-" + maxPieces + "): ");
            piecesToTake = scanner.nextInt();
            if (piecesToTake >= 1 && piecesToTake <= maxPieces) {
                break;
            } else {
                System.out.println("Invalid input. Please enter a number between 1 and " + maxPieces + ".");
            }
        }

        return piecesToTake;
    }

    // Method to switch to the next player
    private void switchPlayer() {
        currentPlayer = (currentPlayer == 1) ? 2 : 1;
    }

    // Method to declare the winner of the Nim game
    private void declareWinner() {
        System.out.println("The last player to take a piece, " + getCurrentPlayer().getName() + ", loses!");
    }

    // Method to ask the user if they want to play the game again
    private void askForPlayAgain() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Do you want to play again? (yes/no): ");
        String response = scanner.next().toLowerCase();
        playAgain = response.equals("yes");

        // Reset special move usage for the next game
        player1.resetSpecialMove();
        player2.resetSpecialMove();
    }

    // Method to reverse the order of play
    public void reverseOrder() {
        System.out.println("Order of play reversed!");
        // Swap the players
        NimPlayer temp = player1;
        player1 = player2;
        player2 = temp;
    }
}