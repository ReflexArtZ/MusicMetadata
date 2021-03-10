package music_metadata.gui;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.NotSupportedException;
import com.mpatric.mp3agic.UnsupportedTagException;
import music_metadata.MusicFile;
import music_metadata.exceptions.NoId3v2TagException;
import music_metadata.utils.Debug;

import javax.swing.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

public class MusicMetadata {
    private JPanel mainPanel;
    private JPanel panel1;
    private JPanel panel2;
    private JTextField titleTxtField;
    private JTextField artistTxtField;
    private JTextField albumTxtField;
    private JTextField yearTxtField;
    private JTextField genreTxtField;
    private JTextField commentTxtField;
    private JComboBox energyComboBox;
    private JCheckBox remixCheckBox;
    private JCheckBox acapellaCheckBox;
    private JCheckBox stemCheckBox;
    private JButton cancelButton;
    private JButton refreshButton;
    private JButton saveButton;
    private JLabel fileNameLabel;
    private JLabel bpmLabel;
    private JLabel keyLabel;
    private JPanel panelSelect;
    private JButton chooseFileButton;
    private JScrollPane scrollPane;
    private JFileChooser fileChooser;

    private File currentFile;

    private MusicFile musicFile;

    public MusicMetadata() {
        JFrame frame = new JFrame("MusicMetadata");
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        chooseFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fileChooser = new JFileChooser();
                int returnVal = fileChooser.showOpenDialog(mainPanel);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    currentFile = fileChooser.getSelectedFile();
                    //This is where a real application would open the file.
                    Debug.print(MusicMetadata.class, "Opened File: " + currentFile.getName());
                    try {
                        musicFile = new MusicFile(currentFile.toString());
                        refresh();
                    } catch (InvalidDataException e1) {
                        e1.printStackTrace();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    } catch (UnsupportedTagException e1) {
                        e1.printStackTrace();
                    } catch (NoId3v2TagException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refresh();
            }
        });
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                update();
            }
        });
        /*cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });*/
        cancelButton.addActionListener(actionEvent -> {

        });
        refreshButton.addActionListener(e -> {
            System.out.println("a");
            JDialog dialog = new JDialog(new CancelDialog(this), true);
            dialog.setVisible(true);
        });
    }

    private void refresh() {
        if (musicFile != null) {
            try {
                musicFile.refresh();
            } catch (NoId3v2TagException e) {
                e.printStackTrace();
            }
            titleTxtField.setText(musicFile.getTitle());
            energyComboBox.setSelectedIndex(musicFile.getEnergy());
            artistTxtField.setText(musicFile.getArtist());
            albumTxtField.setText(musicFile.getAlbum());
            yearTxtField.setText(musicFile.getYear());
            genreTxtField.setText(musicFile.getGenre());
            commentTxtField.setText(musicFile.getComment());
        }
    }

    private void update() {
        if (musicFile != null) {
            musicFile.setTitle(titleTxtField.getText());
            musicFile.setEnergy(energyComboBox.getSelectedIndex());
            musicFile.setArtist(artistTxtField.getText());
            musicFile.setAlbum(albumTxtField.getText());
            musicFile.setYear(yearTxtField.getText());
            musicFile.setGenre(genreTxtField.getText());
            musicFile.setComment(commentTxtField.getText());
            try {
                musicFile.update();
            } catch (NoId3v2TagException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NotSupportedException e) {
                e.printStackTrace();
            }
            try {
                musicFile.refresh();
            } catch (NoId3v2TagException e) {
                e.printStackTrace();
            }
        }
    }

    public void setTitleTxtField(String txt) {
        titleTxtField.setText(txt);
    }
}
