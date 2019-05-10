package hashcode.finale;

import hashcode.Output;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        final File[] files = new File("res/finale").listFiles();
        assert files != null;
        for (File file : files) {
            // Input.read(file);
            final String filename = file.getName().substring(0, file.getName().indexOf("."));

            // magic

//            int score = Score.getScore(show.getSlides());
//            System.out.println(filename + ": The score is: " + score);
//            finalScore += score;

            ArrayList<Server> servers = new ArrayList<>();
            Output.writeServerOutput(servers, "out/finale/" + filename + ".out.txt");
        }
    }

}
