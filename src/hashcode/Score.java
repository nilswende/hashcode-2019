package hashcode;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Score {
    public static int getScore(List<Slide> slides) {
        int score = 0;
        Slide lastSlide = null;

        for (Slide slide : slides) {
            if (lastSlide == null) {
                lastSlide = slide;
            } else {
                Set<String> slideTags = new HashSet<>(slide.getTags());
                Set<String> lastSlideTags = new HashSet<>(lastSlide.getTags());
                int test = onlyInOne(lastSlideTags, slideTags);
                slideTags = new HashSet<>(slide.getTags());
                lastSlideTags = new HashSet<>(lastSlide.getTags());
                int test2 = commonTags(slideTags, lastSlideTags);
                slideTags = new HashSet<>(slide.getTags());
                lastSlideTags = new HashSet<>(lastSlide.getTags());
                int test3 = onlyInOne(slideTags, lastSlideTags);
                score += Math.min(test, Math.min(test2, test3));
                lastSlide = slide;
            }
        }

        return score;
    }

    private static int commonTags(Set<String> slideTags, Set<String> lastSlideTags) {
        int score = slideTags.size();
        slideTags.removeAll(lastSlideTags);
        score -= slideTags.size();
        return score;
    }

    private static int onlyInOne(Set<String> slideTags, Set<String> lastSlideTags) {
        int score;
        slideTags.removeAll(lastSlideTags);
        score = slideTags.size();
        return score;
    }
}
