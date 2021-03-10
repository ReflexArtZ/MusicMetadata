package music_metadata;

import com.mpatric.mp3agic.*;
import music_metadata.exceptions.NoId3v2TagException;
import music_metadata.utils.Debug;

import java.io.IOException;

public class MusicFile {
    private Mp3File file;
    private String path;    // path with filename (absolute path)

    private String title;
    private int energy;     // rating of the track from 0-5
    private String artist;
    private String album;
    private String year;
    private String genre;
    private int bpm;
    private String key;
    private String comment;

    public MusicFile(String path) throws InvalidDataException, IOException, UnsupportedTagException, NoId3v2TagException {
        this.file = new Mp3File(path);
        this.path = path;
        gatherInformations();
    }

    private void gatherInformations() throws NoId3v2TagException {
        if (!file.hasId3v2Tag()) throw new NoId3v2TagException(file.getFilename());
        ID3v2 id3v2 = file.getId3v2Tag();
        setTitle(id3v2.getTitle());
        setEnergy(id3v2.getWmpRating());
        setArtist(id3v2.getArtist());
        setAlbum(id3v2.getAlbum());
        setYear(id3v2.getYear());
        setGenre(id3v2.getGenreDescription());
        setBpm(id3v2.getBPM());
        setKey(id3v2.getKey());
        setComment(id3v2.getComment());
        Debug.print(MusicFile.class, "Gathered informations!"+"\n"+toString());
    }

    public void update() throws NoId3v2TagException, IOException, NotSupportedException {
        if (!file.hasId3v2Tag()) throw new NoId3v2TagException(file.getFilename());
        ID3v2 id3v2 = file.getId3v2Tag();
        id3v2.setTitle(title);
        id3v2.setWmpRating(energy);
        id3v2.setArtist(artist);
        id3v2.setAlbum(album);
        id3v2.setYear(year);
        id3v2.setGenreDescription(genre);
        id3v2.setKey(key);
        id3v2.setComment(comment);
        file.save(path);
        Debug.print(MusicFile.class, "Updated informations!"+"\n"+toString());
    }
    public void refresh() throws NoId3v2TagException {
        gatherInformations();
    }

    public Mp3File getFile() {
        return file;
    }

    public String getTitle() {
        return title;
    }

    public int getEnergy() {
        return energy;
    }

    public String getArtist() {
        return artist;
    }

    public String getAlbum() {
        return album;
    }

    public String getYear() {
        return year;
    }

    public String getGenre() {
        return genre;
    }

    public int getBpm() {
        return bpm;
    }

    public String getKey() {
        return key;
    }

    public String getComment() {
        return comment;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setFile(Mp3File file) {
        this.file = file;
    }

    public void setBpm(int bpm) {
        this.bpm = bpm;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "MusicFile{" +
                "file=" + file +
                ", title='" + title + '\'' +
                ", energy=" + energy +
                ", artist='" + artist + '\'' +
                ", album='" + album + '\'' +
                ", year='" + year + '\'' +
                ", genre='" + genre + '\'' +
                ", bpm=" + bpm +
                ", key='" + key + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
