package no.bekk.sommerskole.logic;

import java.util.Random;

public class ImageSelector {
    public static String getRandomBeerImage(int seed) {
        Random generator = new Random(seed);
        int result = generator.nextInt(5);
        return "/images/generic_beer_" + result + ".jpg";
    }
}
