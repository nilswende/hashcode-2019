package hashcode.qualification.interfaces;

import hashcode.qualification.Photo;
import hashcode.qualification.Slide;

import java.util.List;

/**
 * Created by nilsw
 */
public interface PhotoToSlide {
    List<Slide> make(List<Photo> photos);
}
