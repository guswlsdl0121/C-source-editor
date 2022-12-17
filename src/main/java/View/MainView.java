package View;

import Controller.FileController;
import Controller.InputController;

import Controller.EditController;
import Controller.FileController;

import java.awt.*;
import javax.swing.*;


/**
 * 사용자로부터 텍스트를 입력받는 메모장 main class
 *
 * @author 안현진
 * @version 1.0
 * @see JFrame
 */
public class MainView extends JFrame {
    public static JTextPane textPane = new JTextPane();
    private final InputController ip = new InputController(textPane);


    private final JLabel la = new JLabel("");
    private final JLabel la2 = new JLabel("");
    private final StatusThread st = new StatusThread();

    public MainView() {
        setLayout(null);
        JFrame jFrame = new JFrame();

        jFrame.setTitle("C 소스 편집기");
        jFrame.setSize(1080, 720);
        jFrame.setLocation(100, 50);
        jFrame.setDefaultCloseOperation(jFrame.EXIT_ON_CLOSE);
        Container c = jFrame.getContentPane();
        c.setLayout(null);
        JScrollPane scrollPane = new JScrollPane(textPane);
        scrollPane.setSize(800, 500);
        scrollPane.setLocation(10, 10);
        c.add(scrollPane);
        la.setSize(860, 20);
        la.setLocation(10, 525);
        c.add(la);

        la2.setSize(860, 20);
        la2.setLocation(10, 625);
        c.add(la2);

        JMenuBar menuBar = new JMenuBar();
        jFrame.setJMenuBar(menuBar);

        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);

        JMenu editMenu = new JMenu("Edit");
        menuBar.add(editMenu);

        jFrame.setVisible(true);

        JMenuItem newItem = new JMenuItem("NewFile    Ctrl+N");
        fileMenu.add(newItem);

        JMenuItem loadItem = new JMenuItem("LoadFile   Ctrl+O");
        fileMenu.add(loadItem);

        JMenuItem saveItem = new JMenuItem("SaveFile   Ctrl+S");
        fileMenu.add(saveItem);

        JMenuItem exitItem = new JMenuItem("ExitFile     Ctrl+Q");
        fileMenu.add(exitItem);

        //파일 메뉴 액션 리스너 등록
        FileController fc = new FileController();
        newItem.addActionListener(fc);
        loadItem.addActionListener(fc);
        saveItem.addActionListener(fc);
        exitItem.addActionListener(fc);

        //파일 메뉴 단축키 리스너 등록
        textPane.addKeyListener(fc.new FileHotkeyListener());
//        textPane.addKeyListener(ic.new ThreadStopListener());

        //에딧메뉴 단축키 리스너 등록
        textPane.addKeyListener(new EditController.MykeyListener());
        textPane.addMouseListener(new EditController.MyMouseEvent());
        EditController ed = new EditController();


//        JMenuItem Edit_Menu_search_Item = new JMenuItem("Search   Ctrl+F");
//        editMenu.add(Edit_Menu_search_Item);
//        Edit_Menu_search_Item.addActionListener(new SearchViewEv());
//
//        JMenuItem Edit_Menu_ALLsearch_Item = new JMenuItem("searchALL   Ctrl+G");
//        editMenu.add(Edit_Menu_ALLsearch_Item);
//        Edit_Menu_ALLsearch_Item.addActionListener(new SearchAllViewEv());
//
//        JMenuItem Edit_Menu_change_Item = new JMenuItem("Change   Ctrl+R");
//        editMenu.add(Edit_Menu_change_Item);
//        Edit_Menu_change_Item.addActionListener(new ChangeViewEv());
//
//        JMenuItem Edit_Menu_ALLchange_Item = new JMenuItem("ChangeALl   Ctrl+T");
//        editMenu.add(Edit_Menu_ALLchange_Item);
//        Edit_Menu_ALLchange_Item.addActionListener(new ChangeAllViewEv());

        JMenuItem Edit_Menu_search_Item = new JMenuItem("Search          Ctrl+F");
        editMenu.add(Edit_Menu_search_Item);

        JMenuItem Edit_Menu_ALLsearch_Item = new JMenuItem("SearchALL   Ctrl+G");
        editMenu.add(Edit_Menu_ALLsearch_Item);

        JMenuItem Edit_Menu_change_Item = new JMenuItem("Change        Ctrl+R");
        editMenu.add(Edit_Menu_change_Item);

        JMenuItem Edit_Menu_ALLchange_Item = new JMenuItem("ChangeALL   Ctrl+T");
        editMenu.add(Edit_Menu_ALLchange_Item);

        Edit_Menu_search_Item.addActionListener(ed);
        Edit_Menu_ALLsearch_Item.addActionListener(ed);
        Edit_Menu_change_Item.addActionListener(ed);
        Edit_Menu_ALLchange_Item.addActionListener(ed);

        StatusThread st = new StatusThread();
        st.start();

    }

//    class SearchViewEv implements ActionListener {
//        public void actionPerformed(ActionEvent e) {
//            SearchView searchView = new SearchView();
//        }
//    }
//    class SearchAllViewEv implements ActionListener {
//        public void actionPerformed(ActionEvent e) {
//            SearchAllView searchAllView = new SearchAllView();
//
//        }
//    }
//    class ChangeViewEv implements ActionListener {
//        public void actionPerformed(ActionEvent e) {
//            ChangeView changeView = new ChangeView();
//        }
//    }
//    class ChangeAllViewEv implements ActionListener {
//        public void actionPerformed(ActionEvent e) {
//            ChangeAllView changeAllView = new ChangeAllView();
//        }
//    }
    /**
     * 행과 열을 출력하는 쓰레드
     *
     * @author 안현진
     * @version 1.0
     * @see Thread
     */
    class StatusThread extends Thread {
        public void run() {
            while (true) {
                char[] c = textPane.getText().replace("\r\n", "\n").toCharArray();
                int row = 1;
                int col = 0;
                for (int i = 0; i < textPane.getCaretPosition(); i++) {
                    if (c[i] == '\n') {
                        row++;
                        col = i;
                    }
                }
                la.setText("행 :" + row );
                la2.setText("열 :" + (textPane.getCaretPosition() - col));
                try {
                    Thread.sleep(150);
                } catch (InterruptedException e) {
                    return;
                }
            }
        }
    }
}



