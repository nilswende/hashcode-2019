package hashcode.implementations;

import hashcode.Slide;
import hashcode.Slideshow;
import hashcode.interfaces.SlideToSlideshow;
import hashcode.util.ConcurrencyUtils;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * Teilt die Slides in gleich große Stücke auf, sortiert sie getrennt und fügt sie dann bestmöglich zusammen.
 */
public class MergeSorter implements SlideToSlideshow {

    private final int nBuckets;

    public MergeSorter (int nBuckets) {
        this.nBuckets = nBuckets;
    }

    @Override
    public Slideshow make (List<Slide> slides) {
        if (slides.size() < 5 || nBuckets < 2) {
            Slideshow show = new Slideshow();
            show.addAll(GroupedDescendingTagSorter.sort(slides));
            return show;
        }
        final Map<Integer, List<Slide>> buckets = new HashMap<>(nBuckets);

        // split slides into equally large buckets
        final int bucketSize = (int) Math.ceil(slides.size() / (double) nBuckets);
        int currentBucket = 0, i = 0;
        while (slides.size() > 0) {
            final List<Slide> list = buckets.computeIfAbsent(currentBucket, b -> new ArrayList<>(bucketSize));
            list.add(slides.remove(0));
            i++;
            if (i >= bucketSize) {
                i = 0;
                currentBucket++;
            }
        }

        // sort buckets individually
        final ExecutorService pool = Executors.newFixedThreadPool(buckets.size());
        buckets.values().forEach(
                (v) -> pool.execute(() -> sortInPlace(v))
        );
        ConcurrencyUtils.shutdownAndAwaitTermination(pool);

        // concat buckets optimally
        final List<List<Slide>> bucketsList = buckets.values().stream()
                .sorted(Comparator.comparingInt(l -> l.get(0).getTagCount()))
                .collect(Collectors.toList());
        final List<Slide> starts = bucketsList.stream().skip(1).map(l -> l.get(0)).collect(Collectors.toList());
        final Slideshow show = new Slideshow();
        int nextBucketIndex = 0;
        while (true) {
            final List<Slide> list = bucketsList.get(nextBucketIndex);
            show.addAll(list);

            final Slide end = list.get(list.size() - 1);
            final int bestIndex = GroupedDescendingTagSorter.findBestMatchIndex(end, starts);
            if (bestIndex == -1) break;
            final Slide start = starts.remove(bestIndex);
            for (nextBucketIndex = 0; nextBucketIndex < bucketsList.size(); nextBucketIndex++) {
                final List<Slide> slideList = bucketsList.get(nextBucketIndex);
                if (start.equals(slideList.get(0))) {
                    break;
                }
            }
        }

        return show;
    }

    private void sortInPlace (List<Slide> slides) {
        final List<Slide> sorted = GroupedDescendingTagSorter.sort(slides);
        slides.clear();
        slides.addAll(sorted);
    }

}
