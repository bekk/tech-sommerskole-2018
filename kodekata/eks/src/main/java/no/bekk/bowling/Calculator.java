package no.bekk.bowling;

public class Calculator {
    private final int[] scores = new int[22];
    int cursor = 0;

    public void add(int numberOfPins){
        if(numberOfPins > 10) throw new IllegalArgumentException("There aren't that many pins in bowling!");
        if(numberOfPins < 0) throw new IllegalArgumentException("You cannot score less than zero.");
        if(cursor > 21) throw new IllegalStateException("The scoreboard is full");
        scores[cursor++] = numberOfPins;
        if(numberOfPins == 10 && (cursor&1) == 1 && cursor < 20){
            cursor ++;
        }
    }
    public int total()  {
        var total = 0;
        for (int n = 0; n < 20; n++){
            var thisScore = scores[n];
            total += thisScore;
            if((n&1) == 0){
                var strike = thisScore == 10;
                if(strike){
                    total += scores[n+2];
                    if(scores[n+2] < 10 || n > 17) {
                        total += scores[n+3];
                    }else{
                        total += scores[n+4];
                    }
                    n++;
                }
            }else{
                var frameScore = thisScore + scores[n-1];
                if(frameScore == 10 && thisScore != 0){
                    total += scores[n+1];
                }
            }
        }
        return total;
    }
}