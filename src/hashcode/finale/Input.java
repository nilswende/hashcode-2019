package hashcode.finale;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Input {

    public static void read (File f) throws FileNotFoundException {
        final Scanner scanner = new Scanner(f);
        int nFiles = 0;
        int nTargets = 0;
        int nServer = 0;

        nFiles = scanner.nextInt();
        nTargets = scanner.nextInt();
        nServer = scanner.nextInt();

        Map<String, SourceFile> files = new HashMap<>();

        for (int i = 0; i < nFiles; i++) {
            String name = scanner.next();
            int time = scanner.nextInt();
            int replicate = scanner.nextInt();

            int nDependencies = scanner.nextInt();
            List<SourceFile> dependencies = new ArrayList<>();
            for (int j = 0; j < nDependencies; j++) {
                dependencies.add(files.get(scanner.next()));
            }
            SourceFile newFile = new SourceFile(name, time, replicate, dependencies);
            files.put(name, newFile);
            for (SourceFile file : dependencies) {
                file.addRequiredBy(newFile);
            }
        }
        //-------End SourceFiles/Start Targets------------//
        for (int k = 0; k < nTargets; k++) {
            String name = scanner.next();
            files.get(name).setIsTarget(true);
            files.get(name).setDeadline(scanner.nextInt());
            files.get(name).setScore(scanner.nextInt());
        }

        List<Server> servers = new ArrayList<>();
        for(int l = 0;l<nServer;l++){
            servers.add(new Server(l));
        }
        Main.setServers(servers);
        Main.setSourceFiles(files);
    }

}
