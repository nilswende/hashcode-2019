package hashcode.qualification.implementations;

import hashcode.qualification.Score;
import hashcode.qualification.Slide;
import hashcode.qualification.Slideshow;
import hashcode.qualification.interfaces.SlideToSlideshow;

import java.util.*;

public class GroupedDescendingTagSorter implements SlideToSlideshow {

    /**
     * Hängt von der Verteilung der Tags innerhalb einer Datei ab.
     */
    private final int bucketSplitter;

    public GroupedDescendingTagSorter (int bucketSplitter) {
        this.bucketSplitter = bucketSplitter;
    }

    @Override
    public Slideshow make (List<Slide> slides) {
        Map<Integer, List<Slide>> map = new HashMap<>();

        for (int bucket = 0; bucket < 100 / bucketSplitter; bucket++) {
            map.put(bucket, new ArrayList<>());
        }

        System.out.println("sorting in buckets");
        for (Slide slide : slides) {
            int bucket = slide.getTagCount() / bucketSplitter;
            map.get(bucket).add(slide);
        }

        // sort lists in map
        for (int bucket = 0; bucket < map.keySet().size(); bucket++) {
            map.put(bucket, sort(map.get(bucket)));
        }


        Slideshow show = new Slideshow();
        for (int bucket = 0; bucket < map.keySet().size(); bucket++) {
            for (Slide slide : map.get(bucket)) {
                show.add(slide);
            }
        }

        return show;
    }

    public static List<Slide> sort (List<Slide> slides) {
        final int initSize = slides.size();
        if (slides.size() <= 2) {
            return new ArrayList<>(slides);
        }

        slides.sort(Comparator.comparingInt(Slide::getTagCount));
        List<Slide> newList = new ArrayList<>(slides.size());
        newList.add(slides.remove(0));

        while (slides.size() > 0) {
            System.out.println("remaining " + slides.size() + "/" + initSize);
            Slide current = newList.get(newList.size() - 1);

            int bestIndex = findBestMatchIndex(current, slides);

            newList.add(slides.remove(bestIndex));
        }

        return newList;
    }

    public static int findBestMatchIndex (Slide current, List<Slide> slides) {
        final int maxScore = Score.getMaxScore(current);
        int bestScore = -1;
        int bestIndex = -1;
        for (int i = 0; i < slides.size(); i++) {
            final int score = Score.getScore(current, slides.get(i));
            if (score > bestScore) {
                bestScore = score;
                bestIndex = i;
                if (bestScore == maxScore) break;
            }
        }
        return bestIndex;
    }

    public int getBucketSplitter () {
        return bucketSplitter;
    }

}
