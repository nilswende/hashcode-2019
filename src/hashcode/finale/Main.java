package hashcode.finale;

import hashcode.Output;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public class Main {

    private static List<Server> servers;
    private static List<SourceFile> sourceFiles;

    public static void main(String[] args) throws FileNotFoundException {
        final File[] files = new File("res/finale").listFiles();
        assert files != null;
        for (File file : files) {
            // Input.read(file);
            final String filename = file.getName().substring(0, file.getName().indexOf("."));

            // magic

//            int score = Score.getScore(show.getSlides());
//            System.out.println(filename + ": The score is: " + score);
//            finalScore += score;

            Output.writeServerOutput(servers, "out/finale/" + filename + ".out.txt");
        }
    }

    public static List<Server> getServers () {
        return servers;
    }

    public static void setServers (List<Server> servers) {
        Main.servers = servers;
    }

    public static List<SourceFile> getSourceFiles () {
        return sourceFiles;
    }

    public static void setSourceFiles (List<SourceFile> sourceFiles) {
        Main.sourceFiles = sourceFiles;
    }
}
