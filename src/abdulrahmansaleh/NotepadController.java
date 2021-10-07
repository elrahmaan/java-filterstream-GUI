package abdulrahmansaleh;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;

/**
 *
 * @author Abdul Rahman Saleh
 */
public class NotepadController {

    private Notepad view;
    private List<Integer> list = new ArrayList<>();

    public NotepadController(Notepad view) {
        this.view = view;
        this.view.getBtnBacaFile().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                baca();
            }
        });
        this.view.getBtnSimpanFile().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                simpan();
            }
        });
    }

    //proses baca isi file
    private void baca() {
        JFileChooser loadFile = view.getFileChooser();
        StyledDocument doc = view.getTextPane().getStyledDocument();
        if (JFileChooser.APPROVE_OPTION == loadFile.showOpenDialog(view)) {
            BufferedInputStream reader = null;
            try {
                reader = new BufferedInputStream(new FileInputStream(loadFile.getSelectedFile()));
                doc.insertString(0, "", null);
                int temp = 0;
                List<Integer> list = new ArrayList<>();
                while ((temp = reader.read()) != -1) {
                    list.add(temp);
                }
                if (!list.isEmpty()) {
                    byte[] dt = new byte[list.size()];
                    int i = 0;
                    for (Integer integer : list) {
                        dt[i] = integer.byteValue();
                        i++;
                    }
                    doc.insertString(doc.getLength(), new String(dt), null);
                    JOptionPane.showMessageDialog(view, "File berhasil dibaca.", "Informasi", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(NotepadController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException | BadLocationException ex) {
                Logger.getLogger(NotepadController.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException ex) {
                        Logger.getLogger(NotepadController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }

    //proses simpan/buat file
    private void simpan() {
        JFileChooser loadFile = view.getFileChooser();
        if (JFileChooser.APPROVE_OPTION == loadFile.showSaveDialog(view)) {
            BufferedOutputStream writer = null;
            try {
                String contents = view.getTextPane().getText();
                if (contents != null && !contents.isEmpty()) {
                    writer = new BufferedOutputStream(new FileOutputStream(loadFile.getSelectedFile()));
                    writer.write(contents.getBytes());
                    JOptionPane.showMessageDialog(view, "File berhasil ditulis.", "Informasi", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(NotepadController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(NotepadController.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (writer != null) {
                    try {
                        writer.flush();
                        writer.close();
                        view.getTextPane().setText("");
                    } catch (IOException ex) {
                        Logger.getLogger(NotepadController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }
}
