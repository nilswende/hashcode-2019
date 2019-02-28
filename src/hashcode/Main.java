package hashcode;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        final File[] files = new File("res").listFiles();
        for (File file : files) {
            List<Photo> photos = Input.read(file);


            Slideshow show = randomDistribution(photos);

            // TODO output show

            final String fileName = file.getName();
            Output.writeOutput(show.getSlides(), "out/" + fileName.substring(0, fileName.indexOf(".")) + ".out.txt");
        }
    }

    private static Slideshow randomDistribution(List<Photo> photos) {
        Slideshow show = new Slideshow();
        List<Photo> verticals = photos.stream().filter(Photo::isVertical).collect(Collectors.toList());
        List<Photo> horizontals = photos.stream().filter(Photo::isHorizontal).collect(Collectors.toList());

        Collections.shuffle(verticals);
        Collections.shuffle(horizontals);

        for (int i = 0; i < verticals.size() - 1; i += 2) {
            show.add(Slide.createVerticalSlide(verticals.get(i), verticals.get(i + 1)));
        }

        for (Photo photo : horizontals) {
            show.add(Slide.createHorizontalSlide(photo));
        }

        Collections.shuffle(show.getSlides());
        return show;
    }

}

