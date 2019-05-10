package hashcode.finale;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class LinearCompilationChainGenerator {
    public static void makeListOfServers(List<SourceFile> sourceFiles, List<Server> servers) {
        List<SourceFile> targets = sourceFiles.stream().filter(SourceFile::isTarget).collect(Collectors.toList());

        for (int i = 0; i < targets.size(); i++) {
            List<SourceFile> sequence = LinearCompilationChainGenerator.generateLinearChain(targets.get(i));
            for (SourceFile sourceFile : sequence) {
                servers.get(i % servers.size()).addSourceFile(sourceFile);
            }
        }
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
