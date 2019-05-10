package hashcode.finale;

import java.util.*;
import java.util.stream.Collectors;

public class ByServer {

    // end time of replication : files replicated
    private TreeMap<Integer, List<SourceFile>> replicated = new TreeMap<>();

    public ByServer () {
        replicated.put(0, new ArrayList<>());
    }

    public List<Server> make (List<SourceFile> files) {
        final List<SourceFile> targets = files.stream().filter(SourceFile::isTarget).collect(Collectors.toList());
        pushFilesToServers(targets);
        for (Server server : Main.getServers()) {
            Collections.reverse(server.getCompilationOrder());
        }
        return null;
    }

    private void pushFilesToServers (List<SourceFile> requiringFiles) {
        for (SourceFile file : requiringFiles) {
            final Server server = fastestServer(file, replicated);
            server.addSourceFile(file);
            final List<SourceFile> replFiles = replicated.
                    computeIfAbsent(server.nextFreeTime() + file.getReplicationTime(), a -> new ArrayList<>());
            replFiles.add(file);
            pushFilesToServers(file.getDependencies());
        }
    }

    public Server fastestServer (SourceFile file, TreeMap<Integer, List<SourceFile>> replicated) {
        Server best = null;
        int bestTime = Integer.MAX_VALUE;
        for (Server server : Main.getServers()) {
            final Map.Entry<Integer, Integer> lastEntry = server.getCompileTime().lastEntry();
            final SortedMap<Integer, List<SourceFile>> tailMap = replicated.tailMap(server.nextFreeTime());
            final List<SourceFile> missingDependencies = new ArrayList<>(file.getDependencies());
            missingDependencies.removeAll(server.getCompilationOrder());
            missingDependencies.removeAll(tailMap.values().stream().flatMap(Collection::stream).collect(Collectors.toList()));
            if (missingDependencies.isEmpty()) {
                final int i = lastEntry.getKey() + lastEntry.getValue() + file.getCompilationTime();
                if (i < bestTime) {
                    best = server;
                    bestTime = i;
                }
            } else {
                pushFilesToServers(missingDependencies);
            }
        }
        return best;
    }

}
