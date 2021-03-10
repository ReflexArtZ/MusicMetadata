package music_metadata.exceptions;

public class NoId3v2TagException extends Exception {
    public NoId3v2TagException(String fileName) {
        super("The file "+fileName+" got no ID3v2 tag!");
    }
}
