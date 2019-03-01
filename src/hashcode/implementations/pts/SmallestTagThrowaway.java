package hashcode.implementations.pts;

import hashcode.Photo;
import hashcode.Slide;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by nilsw
 */
public class SmallestTagThrowaway extends AbstractPhotoToSlide {

    @Override
    protected List<Slide> makeVerticals (List<Photo> verticals) {
        final List<Slide> slides = new ArrayList<>(verticals.size() / 2);
        verticals.sort(Comparator.comparingInt(Photo::getTagCount));

        while (verticals.size() > 1) {
            Photo photo = verticals.get(0);
            int secondIndex = 1;
            Photo second = verticals.get(secondIndex);
            int similarity = Integer.MAX_VALUE;
            for (int inner = secondIndex; inner < verticals.size(); inner++) {
                Photo that = verticals.get(inner);
                final int tagSimilarity = photo.getTagSimilarity(that);
                if (tagSimilarity < similarity) {
                    similarity = tagSimilarity;
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
