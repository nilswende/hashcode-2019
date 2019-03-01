package hashcode.implementations;

import hashcode.Slide;
import hashcode.Slideshow;
import hashcode.interfaces.SlideToSlideshow;
import hashcode.util.ConcurrencyUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
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
        Map<Integer, List<Slide>> buckets = new ConcurrentHashMap<>(nBuckets);

        final int chunkSize = (int) Math.ceil(slides.size() / (double) nBuckets);
        int currentChunk = 0, i = 0;
        while (slides.size() > 0) {
            final List<Slide> list = buckets.computeIfAbsent(currentChunk, b -> new ArrayList<>(chunkSize));
            list.add(slides.remove(0));
            i++;
            if (i >= chunkSize) {
                i = 0;
                currentChunk++;
            }
        }

        final ExecutorService pool = Executors.newFixedThreadPool(buckets.size());
        // sort lists in buckets
        for (int bucket = 0; bucket < buckets.size(); bucket++) {
            final int a = bucket;
            pool.execute(() -> buckets.put(a, GroupedDescendingTagSorter.sort(buckets.get(a))));
        }
        ConcurrencyUtils.shutdownAndAwaitTermination(pool);

        final List<List<Slide>> bucketsList = new ArrayList<>(buckets.values());
        final List<Slide> starts = bucketsList.stream().map(l -> l.get(0)).collect(Collectors.toList());
        Slideshow show = new Slideshow();
        int nextBucketIndex = 0;
        while (true) {
            List<Slide> list = bucketsList.get(nextBucketIndex);
            show.addAll(list);

            Slide end = list.get(list.size() - 1);
            int bestIndex = GroupedDescendingTagSorter.findBestMatchIndex(end, starts);
            if (bestIndex == -1) break;
            final Slide start = starts.remove(bestIndex);
            for (nextBucketIndex = 0; nextBucketIndex < bucketsList.size(); nextBucketIndex++) {
                List<Slide> slideList = bucketsList.get(nextBucketIndex);
                if (start.equals(slideList.get(0))) {
                    break;
                }
            }
        }

        return show;
    }

}
