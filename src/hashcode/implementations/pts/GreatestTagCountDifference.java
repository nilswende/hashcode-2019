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
        List<Photo> verticals = photos.stream().filter(Photo::isVertical).collect(Collectors.toList());
        List<Photo> horizontals = photos.stream().filter(Photo::isHorizontal).collect(Collectors.toList());

        photos.sort(Comparator.comparingInt(a -> a.getTags().size()));
        final ArrayDeque<Photo> deque = new ArrayDeque<>(verticals);
        for (int i = 0; i < deque.size() / 2; i++) {
            final Slide slide = Slide.createVerticalSlide(deque.pollFirst(), deque.pollLast());
            slides.add(slide);
        }
        slides.addAll(horizontals.stream().map(Slide::createHorizontalSlide).collect(Collectors.toList()));
        return slides;
    }

}
