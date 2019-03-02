package hashcode.implementations;

import hashcode.Score;
import hashcode.Slide;
import hashcode.Slideshow;
import hashcode.interfaces.SlideToSlideshow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        List<Slide> newList = new ArrayList<>(slides.size());
        newList.add(slides.remove(0));

        while (slides.size() > 0) {
            System.out.println("remaining " + slides.size() + "/" + initSize);
            Slide current = newList.get(newList.size() - 1);

            int bestIndex = findBestMatchIndex(current, slides);

            newList.add(slides.get(bestIndex));
            slides.remove(bestIndex);
        }

        return newList;
    }

    public static int findBestMatchIndex (Slide current, List<Slide> slides) {
        int bestScore = -1;
        int bestIndex = -1;
        for (int i = 0; i < slides.size(); i++) {
            final int score = Score.getScore(current, slides.get(i));
            if (score > bestScore) {
                bestScore = score;
                bestIndex = i;
            }
        }
        return bestIndex;
    }

    public int getBucketSplitter () {
        return bucketSplitter;
    }

}
