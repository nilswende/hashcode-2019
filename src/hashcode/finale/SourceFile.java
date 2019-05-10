package hashcode.finale;

import java.util.ArrayList;
import java.util.List;

public class SourceFile {
    private String id;
    private boolean target;
    private int compilationTime;
    private int replicationTime;
    private int deadline;
    private int score;
    private List<SourceFile> dependencies;
    private List<SourceFile> isRequiredBy;


    public SourceFile (String id, int compilationTime, int replicationTime, List<SourceFile> dependencies) {
        this.compilationTime = compilationTime;
        this.replicationTime = replicationTime;
        this.id = id;
        this.dependencies = dependencies;
        this.isRequiredBy = new ArrayList<>();
    }

    public int getFullTime () {
        return compilationTime + replicationTime;
    }

    public String getId () {
        return id;
    }

    public boolean isTarget () {
        return target;
    }

    public int getCompilationTime () {
        return compilationTime;
    }

    public int getReplicationTime () {
        return replicationTime;
    }

    public int getDeadline () {
        return deadline;
    }

    public int getScore () {return score;}

    public List<SourceFile> getIsRequiredBy () {
        return isRequiredBy;
    }

    public void setIsTarget (boolean target) {
        this.target = target;
    }
    public void addRequiredBy(SourceFile file){
        isRequiredBy.add(file);
    }

    public void setScore (int score) {
        this.score = score;
    }

    public void setDeadline (int deadline) {
        this.deadline = deadline;
    }

    public List<SourceFile> getDependencies () {
        return dependencies;
    }

    public boolean hasDependencies() {
        return !dependencies.isEmpty();
    }

    @Override
    public String toString() {
        return id;
    }
}
