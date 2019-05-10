package hashcode.finale;

import hashcode.Output;
import hashcode.qualification.Photo;
import hashcode.qualification.Score;
import hashcode.qualification.Slideshow;
import hashcode.qualification.implementations.MergeSorter;
import hashcode.qualification.implementations.pts.SmallestTagThrowaway;
import hashcode.qualification.interfaces.PhotoToSlide;
import hashcode.qualification.interfaces.SlideToSlideshow;
import hashcode.qualification.interfaces.SlideshowMaker;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public class Main {

    public static void main (String[] args) throws FileNotFoundException {
        int finalScore = 0;
        final File[] files = new File("res").listFiles();
        assert files != null;
        for (File file : files) {
            // Input.read(file);
            final String filename = file.getName().substring(0, file.getName().indexOf("."));

            // magic

//            int score = Score.getScore(show.getSlides());
//            System.out.println(filename + ": The score is: " + score);
//            finalScore += score;

            Output.writeOutput(null, "out/" + filename + ".out.txt");
        }
        System.out.println("The final score is: " + finalScore);
    }

}
