package hashcode.interfaces;

import hashcode.Slide;
import hashcode.Slideshow;

import java.util.List;

public interface SlideToSlideshow {
    Slideshow make(List<Slide> slides);
}
