package hashcode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nilsw
 */
public class Slideshow {

    private List<Slide> slides = new ArrayList<>();

    public boolean add (Slide slide) {
        return slides.add(slide);
    }

    public List<Slide> getSlides () {
        return slides;
    }
}
