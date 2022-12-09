package Controller;
import View.MainView;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Objects;
import java.util.StringTokenizer;

public class FileController extends JFrame implements ActionListener{
    private final JTextPane pane = MainView.textPane;
    private String SavePathName = null;

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
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                assert inputStream != null;
                inputStream.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

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
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                assert fo != null;
                fo.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //메모장 나가기
        if (Objects.equals(e.getActionCommand(), "ExitFile   Ctrl+Q")) {
            System.exit(0);
        }
        //파일 새로 생성
        else if (Objects.equals(e.getActionCommand(), "NewFile    Ctrl+N") && !pane.getText().equals("")) {
            int option = JOptionPane.showConfirmDialog(null, "현재 내용을 저장 하시겠습니까?", "New", JOptionPane.YES_NO_CANCEL_OPTION);
            //OK를 누르면 SaveFile실행
            if (option == JOptionPane.OK_OPTION) {
                ActionEvent ae = new ActionEvent(new Object(), 0, "SaveFile   Ctrl+S");
                this.actionPerformed(ae);
                pane.setText("");
            }
            //NO를 누르면 메모장 비우기
            else if (option == JOptionPane.NO_OPTION)
                pane.setText("");
        }
        //파일 불러오기
        else if (Objects.equals(e.getActionCommand(), "LoadFile   Ctrl+O")) {
            if (!pane.getText().equals("")) {
                int option = JOptionPane.showConfirmDialog(null, "현재 내용을 저장 하시겠습니까?", "fileLoad", JOptionPane.YES_NO_CANCEL_OPTION);
                //OK누르면 SaveFile실행
                if (option == JOptionPane.OK_OPTION) {
                    ActionEvent ae = new ActionEvent(new Object(), 0, "SaveFile   Ctrl+S");
                    this.actionPerformed(ae);
                }
                else if (option == JOptionPane.CANCEL_OPTION) {
                    return;
                }
            }
            FileDialog fileDialog = new FileDialog(this, "파일 불러오기", FileDialog.LOAD);
            fileDialog.setDirectory(".");
            fileDialog.setVisible(true);
            //불러올 파일 경로 가져오기
            String loadPathName = fileDialog.getFile();
            if (loadPathName != null)
                try {
                    fileLoad(loadPathName);
                }
                catch (BadLocationException e1) {
                    e1.printStackTrace();
                }
        }
        else if (Objects.equals(e.getActionCommand(), "SaveFile   Ctrl+S")) {
            FileDialog fileDialog = new FileDialog(this, "파일 저장하기", FileDialog.SAVE);
            fileDialog.setDirectory(".");
            fileDialog.setVisible(true);
            //저장할 파일 경로 가져오기
            SavePathName = fileDialog.getFile();
            if (SavePathName != null) {
                fileSave(SavePathName);
            }
            new AutoSave(SavePathName);
        }
    }

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
                }
                catch (InterruptedException e) {
                    return;
                }
            }
        }
    }

    public class HotkeyListener extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_S) {
                if (SavePathName == null) {
                    ActionEvent ae = new ActionEvent(new Object(), 0, "SaveFile   Ctrl+S");
                    actionPerformed(ae);
                }
                else
                    fileSave(SavePathName);
            }
            else if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_N) {
                ActionEvent ae = new ActionEvent(new Object(), 0, "NewFile    Ctrl+N");
                actionPerformed(ae);
            }
            else if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_O) {
                ActionEvent ae = new ActionEvent(new Object(), 0, "LoadFile   Ctrl+O");
                actionPerformed(ae);
            }
            else if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_Q) {
                System.exit(0);
            }
        }
    }
}
