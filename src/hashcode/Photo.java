package hashcode;

import java.util.Set;

/**
 * Created by nilsw
 */
public class Photo {

    private final boolean horizontal;
    private final Set<String> tags;

    public Photo (boolean horizontal, Set<String> tags) {
        this.horizontal = horizontal;
        this.tags = tags;
    }

    public boolean isHorizontal () {
        return horizontal;
    }

    public boolean isVertical () {
        return !horizontal;
    }

    public Set<String> getTags () {
        return tags;
    }

}
