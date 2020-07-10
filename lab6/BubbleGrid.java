public class BubbleGrid {
    static int [][] bubbles;
    public BubbleGrid(int [][] inputs) {
        bubbles = inputs;
        int bubbleCounts = 0;
        for (int i = 0; i < inputs.length; i++) {
            for (int j = 0; j < inputs[i].length; j++) {
                if (inputs[i][j] == 1) {
                    bubbleCounts += 1;
                    bubbles[i][j] = bubbleCounts;
                }
            }
        }
    }
    public static int[] popBubbles(int[][] darts){
        int [] fallBubbles = new int [darts.length];
        for (int i = 0; i < darts.length; i++) {
            fallBubbles[i] = bubbles[darts[i][0]][darts[i][1]];
        }
        return(fallBubbles);
    }
}
