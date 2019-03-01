package hashcode.implementations.pts;

import hashcode.Photo;
import hashcode.Slide;
import hashcode.interfaces.PhotoToSlide;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by nilsw
 */
public abstract class AbstractPhotoToSlide implements PhotoToSlide {

    @Override
    public final List<Slide> make (List<Photo> photos) {
        final List<Slide> slides = photos.stream().filter(Photo::isHorizontal).map(Slide::createHorizontalSlide).collect(Collectors.toList());

        final List<Photo> verticals = photos.stream().filter(Photo::isVertical).collect(Collectors.toList());
        slides.addAll(makeVerticals(verticals));

        return slides;
    }

    protected abstract List<Slide> makeVerticals (List<Photo> photos);

}
