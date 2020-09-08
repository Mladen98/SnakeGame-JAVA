package Highscores;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HardHighScores {
    private PrintWriter printWriter;
    private FileWriter fileWriter;

    public HardHighScores() { File(); }

    public void File() {
        try {
            File file = new File("HardHighScores.txt");
            file.setWritable(true);
            file.setReadable(true);
            fileWriter = new FileWriter("HardHighScores.txt", true);
            printWriter = new PrintWriter(fileWriter);
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public void write(String string) {
        try {
            printWriter.println(string + "\n");
            printWriter.flush();
            fileWriter.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sort() {
        try {
            File file = new File("HardHighScores.txt");
            FileReader fileReader = new FileReader("HardHighScores.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            List<String> lines = new ArrayList<String>();
            String line = null;

            while((line = bufferedReader.readLine()) != null) {
                lines.add(line);
            }
            bufferedReader.close();

            Collections.sort(lines, Collections.reverseOrder());

            FileWriter fileWriter = new FileWriter("HardHighScores.txt");
            for(String string : lines) {
                fileWriter.write(string + "\r\n");
            }
            fileWriter.close();
            file.setReadOnly();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}