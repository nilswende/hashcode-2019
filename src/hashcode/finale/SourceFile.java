package hashcode.finale;

import java.util.ArrayList;
import java.util.List;

public class SourceFile {
    private final String id;
    private final boolean target;
    private final int compilationTime;
    private final int replicationTime;
    private final int deadline;
    private final List<SourceFile> dependencies;

    public SourceFile(String id, boolean target, int compilationTime, int replicationTime, int deadline) {
        this.id = id;
        this.target = target;
        this.compilationTime = compilationTime;
        this.replicationTime = replicationTime;
        this.deadline = deadline;
        this.dependencies = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public boolean isTarget() {
        return target;
    }

    public int getCompilationTime() {
        return compilationTime;
    }

    public int getReplicationTime() {
        return replicationTime;
    }

    public int getDeadline() {
        return deadline;
    }

    public List<SourceFile> getDependencies() {
        return dependencies;
    }
}
