package hashcode.implementations;

import hashcode.Slide;
import hashcode.Slideshow;
import hashcode.interfaces.SlideToSlideshow;

import java.util.*;

public class ConcurrentGroupedDescendingTagSorter implements SlideToSlideshow {

    private final int bucketSplitter;

    public ConcurrentGroupedDescendingTagSorter(int bucketSplitter) {
        this.bucketSplitter = bucketSplitter;
    }

    @Override
    public Slideshow make(List<Slide> slides) {
        Map<Integer, List<Slide>> map = new HashMap<>();

        for (int bucket = 0; bucket < 100 / bucketSplitter; bucket++) {
            map.put(bucket, new ArrayList<>());
        }

        System.out.println("sorting in buckets");
        for (Slide slide : slides) {
            int bucket = slide.getTagCount() / bucketSplitter;
            map.get(bucket).add(slide);
        }

        List<Thread> threads = new ArrayList<>(bucketSplitter);
        // sort lists in map
        for (int bucket = 0; bucket < map.keySet().size(); bucket++) {
            final int a = bucket;
            Thread t = new Thread(() -> {
                map.put(a, sort(map.get(a)));

            });
            threads.add(t);
            t.start();
        }

        for (Thread t: threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        Slideshow show = new Slideshow();
        for (int bucket = 0; bucket < map.keySet().size(); bucket++) {
            for (Slide slide : map.get(bucket)) {
                show.add(slide);
            }
        }

        return show;
    }

    private List<Slide> sort(List<Slide> slides) {
        final int initSize = slides.size();
        if (slides.size() <= 2) {
            return slides;
        }

        List<Slide> newList = new ArrayList<>(slides.size());
        newList.add(slides.get(0));
        slides.remove(0);

        while (slides.size() > 0) {
            System.out.println("remaining " + slides.size() + "/" + initSize);
            Slide current = newList.get(newList.size() - 1);

            int bestScore = -1;
            int bestIndex = -1;
            for (int i = 0; i < slides.size(); i++) {
                if (score(current, slides.get(i)) > bestScore) {
                    bestScore = score(current, slides.get(i));
                    bestIndex = i;
                }
            }

            newList.add(slides.get(bestIndex));
            slides.remove(bestIndex);
        }

        return newList;
    }

    private int score(Slide one, Slide two) {
        int test = onlyInOne(two.getTags(), one.getTags());
        int test2 = commonTags(one.getTags(), two.getTags());
        int test3 = onlyInOne(one.getTags(), two.getTags());
        return Math.min(test, Math.min(test2, test3));
    }

    private static int commonTags(Set<String> slideTags, Set<String> lastSlideTags) {
        slideTags = new HashSet<>(slideTags);
        lastSlideTags = new HashSet<>(lastSlideTags);
        int score = slideTags.size();
        slideTags.removeAll(lastSlideTags);
        score -= slideTags.size();
        return score;
    }

    private static int onlyInOne(Set<String> slideTags, Set<String> lastSlideTags) {
        slideTags = new HashSet<>(slideTags);
        lastSlideTags = new HashSet<>(lastSlideTags);

        int score;
        slideTags.removeAll(lastSlideTags);
        score = slideTags.size();
        return score;
    }
}
