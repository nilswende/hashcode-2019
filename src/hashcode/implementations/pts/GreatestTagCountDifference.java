package hashcode.implementations.pts;

import hashcode.Photo;
import hashcode.Slide;
import hashcode.interfaces.PhotoToSlide;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by nilsw
 */
public class GreatestTagCountDifference implements PhotoToSlide {

    @Override
    public List<Slide> make (List<Photo> photos) {
        final List<Slide> slides = new ArrayList<>();

        List<Photo> verticals = photos.stream().filter(Photo::isVertical).sorted(Comparator.comparingInt(a -> a.getTags().size())).collect(Collectors.toList());
        final ArrayDeque<Photo> deque = new ArrayDeque<>(verticals);
        for (int i = 0; i < deque.size() / 2; i++) {
            final Slide slide = Slide.createVerticalSlide(deque.pollFirst(), deque.pollLast());
            slides.add(slide);
        }

        final List<Slide> horizontalSlides = photos.stream().filter(Photo::isHorizontal).map(Slide::createHorizontalSlide).collect(Collectors.toList());
        slides.addAll(horizontalSlides);

        return slides;
    }

}
