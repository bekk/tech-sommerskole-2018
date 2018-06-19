package no.bekk.bowling;

import jdk.jshell.spi.ExecutionControl;

import java.util.ArrayList;
import java.util.List;

public class Calculator {
    private final int[] scores = new int[22];
    int cursor = 0;

    public void add(int numberOfPins){
        if(numberOfPins > 10) throw new IllegalArgumentException("There aren't that many pins in bowling!");
        if(numberOfPins < 0) throw new IllegalArgumentException("You cannot score less than zero.");
        scores[cursor++] = numberOfPins;
    }
    public int total()  {
        var total = 0;
        for (int n = 0; n < 20; n++){
            var thisScore = scores[n];
            total += thisScore;
            if(n&1 == 1){

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