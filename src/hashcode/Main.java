package hashcode;

import hashcode.strategy.RandomDistribution;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        final File[] files = new File("res").listFiles();
        for (File file : files) {
            final List<Photo> photos = Input.read(file);

            final Slideshow show = new RandomDistribution().of(photos);

            final String fileName = file.getName();
            Output.writeOutput(show.getSlides(), "out/" + fileName.substring(0, fileName.indexOf(".")) + ".out.txt");
        }
    }

}
