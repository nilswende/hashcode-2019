package hashcode.qualification.implementations.pts;

import hashcode.qualification.Photo;
import hashcode.qualification.Slide;
import hashcode.qualification.interfaces.PhotoToSlide;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by nilsw
 */
public class GreatestTagCountDifference implements PhotoToSlide {

    @Override
    public List<Slide> make (List<Photo> photos) {
        final List<Slide> slides = new ArrayList<>();

        final Deque<Photo> deque = photos.stream().filter(Photo::isVertical).sorted(Comparator.comparingInt(Photo::getTagCount)).collect(Collectors.toCollection(ArrayDeque::new));
        for (int i = 0; i < deque.size() / 2; i++) {
            final Slide slide = Slide.createVerticalSlide(deque.pollFirst(), deque.pollLast());
            slides.add(slide);
        }

        final List<Slide> horizontalSlides = photos.stream().filter(Photo::isHorizontal).map(Slide::createHorizontalSlide).collect(Collectors.toList());
        slides.addAll(horizontalSlides);

        return slides;
    }

}
