package hashcode.implementations.pts;

import hashcode.Photo;
import hashcode.Slide;
import hashcode.interfaces.PhotoToSlide;

import java.util.ArrayList;
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
        while (verticals.size() > 1) {
            Photo photo = verticals.get(0);
            Photo second = verticals.get(1);
            int secondIndex = 1;
            int similarity = Integer.MAX_VALUE;
            for (int inner = 2; inner < verticals.size(); inner++) {
                Photo that = verticals.get(inner);
                if (photo.getTagSimilarity(that) < similarity) {
                    similarity = photo.getTagSimilarity(that);
                    second = that;
                    secondIndex = inner;
                }
                if (similarity == 0) {
                    break;
                }
            }
            slides.add(Slide.createVerticalSlide(photo, second));
            verticals.remove(secondIndex);
            verticals.remove(0);
        }

        return slides;
    }
}
