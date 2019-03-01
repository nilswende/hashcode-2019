package hashcode.implementations;

import hashcode.Slide;
import hashcode.Slideshow;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

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
        shutdownAndAwaitTermination(pool);

        Slideshow show = new Slideshow();
        for (int bucket = 0; bucket < buckets.size(); bucket++) {
            for (Slide slide : buckets.get(bucket)) {
                show.add(slide);
            }
        }

        return show;
    }

    private void shutdownAndAwaitTermination (ExecutorService pool) {
        pool.shutdown(); // Disable new tasks from being submitted
        try {
            // Wait a while for existing tasks to terminate
            if (!pool.awaitTermination(1, TimeUnit.DAYS)) {
                pool.shutdownNow(); // Cancel currently executing tasks
                // Wait a while for tasks to respond to being cancelled
                if (!pool.awaitTermination(60, TimeUnit.SECONDS))
                    System.err.println("Pool did not terminate");
            }
        } catch (InterruptedException ie) {
            // (Re-)Cancel if current thread also interrupted
            pool.shutdownNow();
            // Preserve interrupt status
            Thread.currentThread().interrupt();
        }
    }

}
