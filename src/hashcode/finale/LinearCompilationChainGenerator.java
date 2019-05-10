package hashcode.finale;

import java.util.*;
import java.util.stream.Collectors;

public class LinearCompilationChainGenerator {
    public static void makeListOfServers(List<SourceFile> sourceFiles, List<Server> servers) {
        List<SourceFile> targets = sourceFiles.stream().filter(SourceFile::isTarget).collect(Collectors.toList());

        for (int i = 0; i < targets.size(); i++) {
            List<SourceFile> sequence = LinearCompilationChainGenerator.generateLinearChain(targets.get(i));
            for (SourceFile sourceFile : sequence) {
                if (!servers.get(i % servers.size()).getCompilationOrder().contains(sourceFile)) {
                    servers.get(i % servers.size()).addSourceFile(sourceFile);
                }
            }
        }

        Map<SourceFile, Integer> timeline = makeTimeline(servers);
        for (Server server : servers) {
            int currentTime = 0;
            for(Iterator<SourceFile> it = server.getCompilationOrder().iterator(); it.hasNext(); ) {
                SourceFile file = it.next();

                if (timeline.get(file) < currentTime) {
                    it.remove();
                }

                currentTime += file.getCompilationTime();
            }
        }
    }

    public static Map<SourceFile, Integer> makeTimeline(List<Server> servers) {
        Map<SourceFile, Integer> map = new HashMap<>();
        for (Server server : servers) {
            int currentTime = 0;
            for (SourceFile file : server.getCompilationOrder()) {
                if (!map.containsKey(file)) {
                    map.put(file, Integer.MAX_VALUE);
                }

                int timeAfterReplication = currentTime + file.getCompilationTime() + file.getReplicationTime();
                if (timeAfterReplication < map.get(file)) {
                    map.put(file, timeAfterReplication);
                }

                currentTime += file.getCompilationTime();
            }
        }

        return map;
    }

    public static List<SourceFile> generateLinearChain(SourceFile target) {
        if (!target.hasDependencies()) {
            return Collections.singletonList(target);
        }

        List<SourceFile> list = new ArrayList<>();
        for (SourceFile dependency : target.getDependencies()) {
            for (SourceFile file : generateLinearChain(dependency)) {
                if (!list.contains(file)) {
                    list.add(file);
                }
            }
        }

        list.add(target);
        return list;
    }
}
