package hashcode.finale;

import java.util.ArrayList;
import java.util.List;

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

    public List<SourceFile> getCompilationOrder() {
        return compilationOrder;
    }
}

