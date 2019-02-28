package hashcode.strategy;

import hashcode.Photo;
import hashcode.Slideshow;

import java.util.List;

/**
 * Created by nilsw
 */
public interface Strategy {

    Slideshow of (List<Photo> photos);

}
