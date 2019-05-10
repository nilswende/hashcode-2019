package hashcode;

import hashcode.finale.Server;
import hashcode.finale.SourceFile;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Output {

    public static <T> void writeOutput(List<T> objs, String filename) {
        File file = new File(filename);
        file.getParentFile().mkdirs();

        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(file)))) {
            bw.write(objs.size() + "\n");
            final String output = objs.stream().map(Object::toString).collect(Collectors.joining("\n"));
            bw.write(output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeServerOutput(List<Server> servers, String filename) {
        File file = new File(filename);
        file.getParentFile().mkdirs();

        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(file)))) {
            int compilationSteps = servers.stream().mapToInt(Server::compilationSteps).sum();
            bw.write(compilationSteps + "\n");

            List<CompileStep> steps = new ArrayList<>(compilationSteps);
            for (Server server : servers) {
                int time = 0;
                for(SourceFile sourceFile : server.getCompilationOrder()) {
                    steps.add(new CompileStep(server.getId(), time, sourceFile.getId()));
                    time += sourceFile.getCompilationTime();
                }
            }
            steps.sort(Comparator.comparingInt(value -> value.start));

            final String output = steps.stream()
                    .map(step -> step.file + " " + step.server)
                    .collect(Collectors.joining("\n"));
            bw.write(output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class CompileStep {
        int server;
        int start;
        String file;

        public CompileStep(int server, int start, String file) {
            this.server = server;
            this.start = start;
            this.file = file;
        }
    }

}
