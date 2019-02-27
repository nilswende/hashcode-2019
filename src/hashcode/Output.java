package hashcode;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

public class Output {

    public static <T> void writeOutput (List<T> objs, String filename) {
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(new File(filename))))) {
            final String output = objs.stream().map(Object::toString).collect(Collectors.joining("\n"));
            bw.write(output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
