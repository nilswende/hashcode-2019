package hashcode;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by nilsw
 */
public class Photo {

    private final int id;
    private final boolean horizontal;
    private final Set<String> tags;

    public Photo (int id, boolean horizontal, Set<String> tags) {
        this.id = id;
        this.horizontal = horizontal;
        this.tags = tags;
    }

    public int getId () {
        return id;
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

    public int getTagCount () {
        return getTags().size();
    }

    public int getTagSimilarity (Photo that) {
        final HashSet<String> thisTags = new HashSet<>(tags);
        thisTags.retainAll(that.tags);
        return thisTags.size();
    }

    public String toString(){
        return String.valueOf(id);
    }

}
