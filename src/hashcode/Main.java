package hashcode;

import hashcode.implementations.DescendingTagCount;
import hashcode.implementations.pts.SmallestTagThrowaway;
import hashcode.interfaces.PhotoToSlide;
import hashcode.interfaces.SlideToSlideshow;
import hashcode.interfaces.SlideshowMaker;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public class Main {

    public static void main (String[] args) throws FileNotFoundException {
        int finalScore = 0;
        final File[] files = new File("res").listFiles();
        assert files != null;
        for (File file : files) {
            final List<Photo> photos = Input.read(file);
            final String filename = file.getName().substring(0, file.getName().indexOf("."));

            PhotoToSlide a = new SmallestTagThrowaway();
            SlideToSlideshow b = new DescendingTagCount();
            SlideshowMaker maker = new SlideshowMaker(a, b);
            Slideshow show = maker.make(photos);

            int score = Score.getScore(show.getSlides());
            System.out.println(filename + ": The score is: " + score);
            finalScore += score;

            Output.writeOutput(show.getSlides(), "out/" + filename + ".out.txt");
        }
        System.out.println("The final score is: " + finalScore);
    }

}
