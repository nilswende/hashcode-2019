package hashcode;

import hashcode.finale.Server;

import java.io.*;
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
            final String output = servers.stream().map(Object::toString).collect(Collectors.joining("\n"));
            bw.write(output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
