package hashcode.qualification.implementations.pts;

import hashcode.qualification.Photo;
import hashcode.qualification.Slide;

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
            Photo photo = verticals.remove(0);
            int secondIndex = 0;
            int similarity = Integer.MAX_VALUE;
            for (int current = secondIndex; current < verticals.size(); current++) {
                Photo that = verticals.get(current);
                final int tagSimilarity = photo.getTagSimilarity(that);
                if (tagSimilarity < similarity) {
                    similarity = tagSimilarity;
                    secondIndex = current;
                }
                if (similarity == 0) {
                    break;
                }
            }
            final Photo second = verticals.remove(secondIndex);
            slides.add(Slide.createVerticalSlide(photo, second));
        }

        return slides;
    }

}
