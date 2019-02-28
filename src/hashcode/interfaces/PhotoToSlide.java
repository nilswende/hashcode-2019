package hashcode.interfaces;

import hashcode.Photo;
import hashcode.Slide;

import java.util.List;

/**
 * Created by nilsw
 */
public interface PhotoToSlide {
    List<Slide> make(List<Photo> photos);
}
