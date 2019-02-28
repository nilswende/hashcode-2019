package hashcode;

import hashcode.implementations.DescendingTagCount;
import hashcode.implementations.pts.GreatestTagCountDifference;
import hashcode.interfaces.PhotoToSlide;
import hashcode.interfaces.SlideToSlideshow;
import hashcode.interfaces.SlideshowMaker;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        int finalScore = 0;
        final File[] files = new File("res").listFiles();
        for (File file : files) {
            final List<Photo> photos = Input.read(file);


            PhotoToSlide a = new GreatestTagCountDifference();
            SlideToSlideshow b = new DescendingTagCount();
            SlideshowMaker maker = new SlideshowMaker(a, b);
            Slideshow show = maker.make(photos);

            int score = Score.getScore(show.getSlides());
            System.out.println("The score is: " + score);
            finalScore += score;

            final String fileName = file.getName();
            Output.writeOutput(show.getSlides(), "out/" + fileName.substring(0, fileName.indexOf(".")) + ".out.txt");
        }
        System.out.println("The final score is: "+finalScore);
    }

}
