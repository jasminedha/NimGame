import java.util.Random;

// NimPlayer class representing each player in the Nim game
class NimPlayer {
    private String name;
    private boolean specialMoveUsed;
    private boolean isAutoplay;

    // Constructor to initialize a NimPlayer with a name and autoplay status
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
        // Simple autoplay logic: Randomly select the number of pieces to take (at least 1)
        return Math.max(1, new Random().nextInt(maxPieces));
    }
}