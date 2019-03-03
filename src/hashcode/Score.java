package hashcode;

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
        return slideTags.size() - countDifference(slideTags, lastSlideTags);
    }

    private static int onlyInOne (Set<String> slideTags, Set<String> lastSlideTags) {
        return countDifference(slideTags, lastSlideTags);
    }

    private static int countDifference (final Set<String> slideTags, final Set<String> lastSlideTags) {
        return slideTags.stream().filter(s -> !lastSlideTags.contains(s)).mapToInt(t -> 1).sum();
    }

}
