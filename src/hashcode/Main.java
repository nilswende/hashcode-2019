package hashcode;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class Main {

    public static void main (String[] args) throws FileNotFoundException {
        final File[] files = new File("res").listFiles();
        for (File file : files) {
            Input.read(file);

            // TODO


            final String fileName = file.getName();
            // Output.writeOutput(objs, "out/" + fileName.substring(0, fileName.indexOf(".")) + ".out.txt");
        }
    }

}
