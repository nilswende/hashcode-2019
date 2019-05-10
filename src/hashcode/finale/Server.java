package hashcode.finale;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Server {
    private final int id;
    private final List<SourceFile> compilationOrder;

    public Server(int id) {
        this.id = id;
        this.compilationOrder = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void addSourceFile(SourceFile file) {
        this.compilationOrder.add(file);
    }

    public List<SourceFile> getCompilationOrder() {
        return compilationOrder;
    }

    public int compilationSteps() {
        return this.compilationOrder.size();
    }

    @Override
    public String toString() {
        return compilationOrder.stream().map(file -> this.id + " " + file.getId()).collect(Collectors.joining("\n"));
    }
}

