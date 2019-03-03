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
        final int commonTags = countCommonTags(lastSlide.getTags(), slide.getTags());
        int onlyInLast = onlyInOne(lastSlide.getTags(), commonTags);
        int onlyInCurrent = onlyInOne(slide.getTags(), commonTags);
        return Math.min(onlyInLast, Math.min(commonTags, onlyInCurrent));
    }

    private static int countCommonTags (final Set<String> lastSlideTags, final Set<String> slideTags) {
        int count = 0;
        for (final String tag : lastSlideTags) if (slideTags.contains(tag)) count++;
        return count;
    }

    private static int onlyInOne (Set<String> slideTags, final int intersection) {
        return slideTags.size() - intersection;
    }

}
