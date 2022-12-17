package Controller;
import View.MainView;

import javax.swing.*;
import javax.swing.text.BadLocationException;
//import javax.swing.undo.UndoManager;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Objects;
import java.util.StringTokenizer;

/**
 * 파일 처리를 위한 Controller 클래스
 *
 * @author 안현진
 * @version 1.0
 * @see JFrame
 * @see ActionListener
 */
public class FileController extends JFrame implements ActionListener {
    private final JTextPane pane = MainView.textPane;
    private String SavePathName = null;


    /**
     *  파일 불러오기에 관한 메소드
     * @param path 파일 경로
     * @author 안현진
     */
    public void fileLoad(String path) throws BadLocationException {
        InputStreamReader reader = null;
        FileInputStream inputStream = null;
        BufferedReader bufferedReader = null;


        try {
            inputStream = new FileInputStream(path);
            reader = new InputStreamReader(inputStream);
            bufferedReader = new BufferedReader(reader);
            pane.setText("");
            String str = null;
            String data = "";

            while ((str = bufferedReader.readLine()) != null)
                data = data.concat(str + "\n");
            MainView.textPane.setText(data);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                assert inputStream != null;
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *  파일 저장하기에 관한 메소드
     * @param path 파일 경로
     * @author 안현진
     */
    public void fileSave(String path) {
        FileOutputStream fo = null;
        try {
            fo = new FileOutputStream(path);
            String str = pane.getText();
            StringTokenizer fs = new StringTokenizer(str, "\n");
            while (fs.hasMoreTokens()) {
                fo.write(fs.nextToken().getBytes());
                fo.write(("\r\n").getBytes());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                assert fo != null;
                fo.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *  FileController에서의 actionPerformed 인터페이스 구현 부분
     * @author 안현진
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (Objects.equals(e.getActionCommand(), "ExitFile     Ctrl+Q")) {
            System.out.println("exit");
            System.exit(0);
        }
        if (Objects.equals(e.getActionCommand(), "NewFile    Ctrl+N") && !pane.getText().equals("")) {
            int option = JOptionPane.showConfirmDialog(null, "현재 내용을 저장 하시겠습니까?", "New", JOptionPane.YES_NO_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                ActionEvent ae = new ActionEvent(new Object(), 0, "SaveFile   Ctrl+S");
                this.actionPerformed(ae);
                pane.setText("");
            }
            else if (option == JOptionPane.NO_OPTION)
                pane.setText("");
        }
        if (Objects.equals(e.getActionCommand(), "LoadFile   Ctrl+O")) {
            if (!pane.getText().equals("")) {
                int option = JOptionPane.showConfirmDialog(null, "현재 내용을 저장 하시겠습니까?", "fileLoad", JOptionPane.YES_NO_CANCEL_OPTION);
                //OK누르면 SaveFile실행
                if (option == JOptionPane.OK_OPTION) {
                    ActionEvent ae = new ActionEvent(new Object(), 0, "SaveFile   Ctrl+S");
                    this.actionPerformed(ae);
                } else if (option == JOptionPane.CANCEL_OPTION) {
                    return;
                }
            }
            FileDialog fileDialog = new FileDialog(this, "파일 불러오기", FileDialog.LOAD);
            fileDialog.setDirectory(".");
            fileDialog.setVisible(true);

            String loadPathName = fileDialog.getFile();
            if (loadPathName != null)
                try {
                    fileLoad(loadPathName);
                } catch (BadLocationException e1) {
                    e1.printStackTrace();
                }
        }
        if (Objects.equals(e.getActionCommand(), "SaveFile   Ctrl+S")) {
            FileDialog fileDialog = new FileDialog(this, "파일 저장하기", FileDialog.SAVE);
            fileDialog.setDirectory(".");
            fileDialog.setVisible(true);
            SavePathName = fileDialog.getFile();

            if (SavePathName != null) {
                fileSave(SavePathName);
            }
            new AutoSave(SavePathName);
        }
    }

    /**
     *  파일 자동 저장에 관한 쓰레드
     * @author 안현진
     */
    class AutoSave implements Runnable {
        String autopath;

        AutoSave(String path) {
            autopath = path;
            new Thread(this).start();
        }

        public void run() {
            while (true) {
                try {
                    if (SavePathName == null) {
                        continue;
                    }
                    Thread.sleep(10000);
                    fileSave(autopath);
                } catch (InterruptedException e) {
                    return;
                }
            }
        }
    }

    /**
     *  파일 처리 관련 단축키를 등록한 클래스
     * @author 안현진
     */
    public class FileHotkeyListener extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_S) {
                if (SavePathName == null) {
                    ActionEvent ae = new ActionEvent(new Object(), 0, "SaveFile   Ctrl+S");
                    actionPerformed(ae);
                } else
                    fileSave(SavePathName);
            }
            if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_N) {
                ActionEvent ae = new ActionEvent(new Object(), 0, "NewFile    Ctrl+N");
                actionPerformed(ae);
            }
            if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_O) {
                ActionEvent ae = new ActionEvent(new Object(), 0, "LoadFile   Ctrl+O");
                actionPerformed(ae);
            }
            if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_Q) {
                System.exit(0);
            }
        }
    }
}
