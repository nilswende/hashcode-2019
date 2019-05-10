package hashcode.finale;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class Server {
    private final int id;
    private final List<SourceFile> compilationOrder;
    private TreeMap<Integer, Integer> compileTime = new TreeMap<>();

    public Server (int id) {
        this.id = id;
        this.compilationOrder = new ArrayList<>();
        compileTime.put(0, 0);
    }

    public int nextFreeTime () {
        return compileTime.lastEntry().getKey();
    }

    public int getId () {
        return id;
    }

    public void addSourceFile (SourceFile file) {
        this.compilationOrder.add(file);
        compileTime.put(compileTime.lastKey(), file.getCompilationTime());
        compileTime.put(file.getCompilationTime(), 0);
    }

    public List<SourceFile> getCompilationOrder () {
        return compilationOrder;
    }

    public int compilationSteps () {
        return this.compilationOrder.size();
    }

    public TreeMap<Integer, Integer> getCompileTime () {
        return compileTime;
    }

    @Override
    public String toString () {
        return compilationOrder.stream().map(file -> file.getId() + " " + this.id).collect(Collectors.joining("\n"));
    }
}

