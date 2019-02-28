package hashcode.implementations.pts;

import hashcode.Photo;
import hashcode.Slide;
import hashcode.interfaces.PhotoToSlide;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by nilsw
 */
public class SmallestTagThrowaway implements PhotoToSlide {
    @Override
    public List<Slide> make (List<Photo> photos) {
        final List<Slide> slides = photos.stream().filter(Photo::isHorizontal).map(Slide::createHorizontalSlide).collect(Collectors.toList());

        List<Photo> verticals = photos.stream().filter(Photo::isVertical).collect(Collectors.toList());
        for (int i = 0; i < verticals.size(); i++) {
            Photo photo = verticals.get(i);
            Photo second = null;
            int similarity = Integer.MAX_VALUE;
            for (int i1 = 0; i1 < verticals.size() && i1 != i; i1++) {
                Photo that = verticals.get(i1);
                if (photo.getTagSimilarity(that) < similarity) {
                    second = that;
                }
            }
            slides.add(Slide.createVerticalSlide(photo, second));
        }

        return slides;
    }
}
