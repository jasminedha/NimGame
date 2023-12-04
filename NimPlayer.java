import java.util.Random;

// NimPlayer class having each player in the Nim game
class NimPlayer {
    private String name;
    private boolean reverseUsed;
    private boolean computerAuto;

    // Constructor to initialize a NimPlayer with a name and autoplay 
    public NimPlayer(String name, boolean computerAuto) {
        this.name = name;
        this.computerAuto = computerAuto;
        this.reverseUsed = false;
    }

    // Getter method to get the player's name
    public String getName() {
        return name;
    }

    // Getter method to check if the player has used a reverse move yet
    public boolean hasUsedReverse() {
        return reverseUsed;
    }

    // Method for a player to use a reverse move (reversing the order of play)
    public void useReverse(NimGame game) {
        reverseUsed = true;
        System.out.println(name + " used a reverse move on you!");
        game.reverseOrder();
    }

    // Method to reset the reverse move for a player each time a new game starts
    public void resetReverse() {
        reverseUsed = false;
    }

    // Getter method to check if the player is supposed to autoplay against a computer
    public boolean computerAuto() {
        return computerAuto;
    }

    // Method for autoplay/ computer to make a move (randomly selecting pieces to take)
    public int autoplay(int maxPieces) {
        // Randomly select the number of pieces to take (at least 1)
        return Math.max(1, new Random().nextInt(maxPieces));
    }
}