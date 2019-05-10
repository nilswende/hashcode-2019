package hashcode.qualification;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by nilsw
 */
public class Slide {

    private final List<Photo> photos;
    private final Set<String> tags;

    private Slide (Photo photo) {
        this.photos = Collections.singletonList(photo);
        tags = photo.getTags();
    }

    private Slide (Photo photo1, Photo photo2) {
        this.photos = Arrays.asList(photo1, photo2);
        tags = new HashSet<>();
        for (Photo photo : photos) {
            tags.addAll(photo.getTags());
        }
    }

    public static Slide createHorizontalSlide (Photo photo) {
        return new Slide(photo);
    }

    public static Slide createVerticalSlide (Photo photo1, Photo photo2) {
        return new Slide(photo1, photo2);
    }

    public Set<String> getTags () {
        return tags;
    }

    public int getTagCount () {
        return tags.size();
    }

    public String toString () {
        return photos.stream().map(Photo::toString).collect(Collectors.joining(" "));
    }

}
