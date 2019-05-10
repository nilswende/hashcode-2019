package hashcode.qualification;


import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by nilsw
 */
public class RandomDistribution implements Strategy {

    @Override
    public Slideshow of (List<Photo> photos) {
        Slideshow show = new Slideshow();
        List<Photo> verticals = photos.stream().filter(Photo::isVertical).collect(Collectors.toList());
        List<Photo> horizontals = photos.stream().filter(Photo::isHorizontal).collect(Collectors.toList());

        Collections.shuffle(verticals);
        Collections.shuffle(horizontals);

        for (int i = 0; i < verticals.size() - 1; i++) {
            show.add(Slide.createVerticalSlide(verticals.get(i), verticals.get(i + 1)));
        }

        for (Photo photo : horizontals) {
            show.add(Slide.createHorizontalSlide(photo));
        }

        Collections.shuffle(show.getSlides());
        return show;
    }

}
