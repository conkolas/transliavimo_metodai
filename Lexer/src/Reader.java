import java.io.*;

public class Reader {
    private final InputStream is;
    private final InputStreamReader reader;
    
    public Reader(String fileName) throws FileNotFoundException{
        // Absolute path to source directory
        String filePath = new File("").getAbsolutePath() + "\\src\\" + fileName;

        // Setting file input stream and input reader
        is = new FileInputStream(filePath);
        reader = new InputStreamReader(is);
    }

    // Returns char in ASCII number
    public int getCharNumber() throws IOException{
        int data = reader.read();
        return data;
    }
    
    public void closeReader() throws IOException{
        is.close();
        reader.close();
    }
}
