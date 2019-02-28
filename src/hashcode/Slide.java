package hashcode;

import java.util.*;

/**
 * Created by nilsw
 */
public class Slide {

    private final List<Photo> photos;

    private Slide (List<Photo> photos) {
        this.photos = photos;
    }

    public static Slide createHorizontalSlide (Photo photo) {
        return new Slide(Collections.singletonList(photo));
    }

    public static Slide createVerticalSlide (Photo photo1, Photo photo2) {
        return new Slide(Arrays.asList(photo1, photo2));
    }

    public Set<String> getTags () {
        final HashSet<String> strings = new HashSet<>();
        for (Photo photo : photos) {
            strings.addAll(photo.getTags());
        }
        return strings;
    }

    public int getTagCount() {
        return getTags().size();
    }

    public String toString () {
        String output = "";
        output += photos.get(0).toString();

        if (photos.size() > 1) {
            output += " " + photos.get(1).toString();
        }
        return output;
    }
}
