package hashcode.qualification.implementations;

import hashcode.qualification.Slide;
import hashcode.qualification.Slideshow;
import hashcode.qualification.interfaces.SlideToSlideshow;

import java.util.Comparator;
import java.util.List;

public class DescendingTagCount implements SlideToSlideshow {
    @Override
    public Slideshow make(List<Slide> slides) {
        slides.sort(Comparator.comparing(Slide::getTagCount));

        Slideshow show = new Slideshow();
        for (Slide slide : slides) {
            show.add(slide);
        }
        return show;
    }
}
