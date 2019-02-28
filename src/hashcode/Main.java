package hashcode;

import hashcode.interfaces.PhotoToSlide;
import hashcode.interfaces.SlideToSlideshow;
import hashcode.interfaces.SlideshowMaker;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        final File[] files = new File("res").listFiles();
        for (File file : files) {
            final List<Photo> photos = Input.read(file);


            PhotoToSlide a = null;
            SlideToSlideshow b = null;
            SlideshowMaker maker = new SlideshowMaker(a, b);
            Slideshow show = maker.make(photos);

            final String fileName = file.getName();
            Output.writeOutput(show.getSlides(), "out/" + fileName.substring(0, fileName.indexOf(".")) + ".out.txt");
        }
    }

}
