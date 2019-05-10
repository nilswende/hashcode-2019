package hashcode.qualification.strategy;

import hashcode.qualification.Photo;
import hashcode.qualification.Slideshow;

import java.util.List;

/**
 * Created by nilsw
 */
public interface Strategy {

    Slideshow of (List<Photo> photos);

}
