package hashcode.qualification.implementations;

import hashcode.qualification.Slide;
import hashcode.qualification.Slideshow;
import hashcode.qualification.util.ConcurrencyUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConcurrentGroupedDescendingTagSorter extends GroupedDescendingTagSorter {

    public ConcurrentGroupedDescendingTagSorter (int bucketSplitter) {
        super(bucketSplitter);
    }

    @Override
    public Slideshow make (List<Slide> slides) {
        Map<Integer, List<Slide>> buckets = new ConcurrentHashMap<>();

        for (int bucket = 0; bucket < 100 / getBucketSplitter(); bucket++) {
            buckets.put(bucket, new ArrayList<>());
        }

        System.out.println("sorting in buckets");
        for (Slide slide : slides) {
            int bucket = slide.getTagCount() / getBucketSplitter();
            buckets.get(bucket).add(slide);
        }

        final ExecutorService pool = Executors.newFixedThreadPool(buckets.size());
        // sort lists in buckets
        for (int bucket = 0; bucket < buckets.size(); bucket++) {
            final int a = bucket;
            pool.execute(() -> buckets.put(a, sort(buckets.get(a))));
        }
        ConcurrencyUtils.shutdownAndAwaitTermination(pool);

        Slideshow show = new Slideshow();
        for (int bucket = 0; bucket < buckets.size(); bucket++) {
            for (Slide slide : buckets.get(bucket)) {
                show.add(slide);
            }
        }

        return show;
    }

}
