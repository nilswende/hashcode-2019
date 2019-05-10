package hashcode.finale;

import hashcode.qualification.Photo;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Input {

    public static List<Photo> read (File f) throws FileNotFoundException {
        final Scanner scanner = new Scanner(f);
        int id = 0;
        List<Photo> photos = new ArrayList<>();
        final int nPhotos = scanner.nextInt();
        for (int j = 0; j < nPhotos; j++) {
            final String orientation = scanner.next();
            final int nTags = scanner.nextInt();
            final Set<String> tags = new HashSet<>();
            for (int i = 0; i < nTags; i++) {
                final String tag = scanner.next();
                tags.add(tag);
            }
            photos.add(new Photo(id++, "H".equals(orientation), tags));
        }
        return photos;
    }

}
