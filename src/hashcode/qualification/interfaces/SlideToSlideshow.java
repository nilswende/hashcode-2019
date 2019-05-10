package hashcode.qualification.interfaces;

import hashcode.qualification.Slide;
import hashcode.qualification.Slideshow;

import java.util.List;

public interface SlideToSlideshow {
    Slideshow make(List<Slide> slides);
}
