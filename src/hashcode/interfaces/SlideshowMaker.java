package hashcode.interfaces;

import hashcode.Photo;
import hashcode.Slideshow;

import java.util.List;

public class SlideshowMaker {
    private final PhotoToSlide pts;
    private final SlideToSlideshow sts;

    public SlideshowMaker(PhotoToSlide pts, SlideToSlideshow sts) {
        this.pts = pts;
        this.sts = sts;
    }

    public Slideshow make(List<Photo> photos) {
        return sts.make(pts.make(photos));
    }
}
