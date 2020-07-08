import es.datastructur.synthesizer.GuitarString;

import java.lang.reflect.Array;

public class GuitarHero {
    private static int stringCounts = 37;
    static String keyboard = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";

    public static void main(String[] args) {
        /* create 37 guitar strings, for concert 110HZ and 880HZ */
//        GuitarString[] strings = (GuitarString[]) new Object[stringCounts];
        GuitarString[] strings = new GuitarString[stringCounts];
        for (int i = 0; i < stringCounts; i++){
            double frequency = 440.0 * Math.pow(2, ((i-24)/12));
            strings[i] = new GuitarString(frequency);
        }

        while (true) {
            /* check if the user has typed a key; if so, process it */
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                if (! (keyboard.indexOf(key) == -1)){
                    strings[keyboard.indexOf(key)].pluck();
                }
            }

            /* compute the superposition of samples */
            double sample = 0;
            for (int i = 0; i < stringCounts; i++){
                sample = sample + strings[i].sample();
            }

            /* play the sample on standard audio */
            StdAudio.play(sample);

            /* advance the simulation of each guitar string by one step */
            for (int i = 0; i < stringCounts; i++){
                strings[i].tic();
            }
        }
    }
}

