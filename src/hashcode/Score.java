package hashcode;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Score {

    public static int getScore (List<Slide> slides) {
        int score = 0;
        if (!slides.isEmpty()) {
            Slide lastSlide = slides.get(0);
            for (int i = 1; i < slides.size(); i++) {
                Slide slide = slides.get(i);
                score += getScore(lastSlide, slide);
                lastSlide = slide;
            }
        }
        return score;
    }

    public static int getScore (Slide lastSlide, Slide slide) {
        int test = onlyInOne(lastSlide.getTags(), slide.getTags());
        int test2 = commonTags(slide.getTags(), lastSlide.getTags());
        int test3 = onlyInOne(slide.getTags(), lastSlide.getTags());
        return Math.min(test, Math.min(test2, test3));
    }

    private static int commonTags (Set<String> slideTags, Set<String> lastSlideTags) {
        final HashSet<String> tags = new HashSet<>(slideTags);
        int score = tags.size();
        tags.removeAll(lastSlideTags);
        return score - tags.size();
    }

    private static int onlyInOne (Set<String> slideTags, Set<String> lastSlideTags) {
        final HashSet<String> tags = new HashSet<>(slideTags);
        tags.removeAll(lastSlideTags);
        return tags.size();
    }

}
