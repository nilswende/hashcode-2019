package hashcode;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by nilsw
 */
public class Slideshow {

    private List<Slide> slides = new ArrayList<>();

    public boolean add (Slide slide) {
        return slides.add(slide);
    }

    public boolean addAll (Collection<? extends Slide> c) {
        return slides.addAll(c);
    }

    public List<Slide> getSlides () {
        return slides;
    }
}
