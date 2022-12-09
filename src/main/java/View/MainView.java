package View;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.*;
import javax.swing.*;
import javax.swing.undo.UndoManager;




public class MainView extends JFrame {
    private JFrame fce = new JFrame();
    public static JTextPane textPane = new JTextPane();

    private Highlighter h = textPane.getHighlighter();

    private JLabel la = new JLabel("");
    private JLabel la2 = new JLabel("");
    private StatusThread st = new StatusThread();

    public MainView() {
        setLayout(null);
        fce.setTitle("C 소스 편집기");
        fce.setSize(1080, 720);
        fce.setLocation(100, 50);
        fce.setDefaultCloseOperation(fce.EXIT_ON_CLOSE);
        Container c = fce.getContentPane();
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
        fce.setJMenuBar(menuBar);

        JMenu menu1 = new JMenu("File");
        menuBar.add(menu1);

        JMenu Edit_Menu = new JMenu("Edit");
        menuBar.add(Edit_Menu);

        fce.setVisible(true);

        JMenuItem menu1Item = new JMenuItem("NewFile   Ctrl+N");
        menu1.add(menu1Item);

        JMenuItem menu2Item = new JMenuItem("LoadFile   Ctrl+O");
        menu1.add(menu2Item);

        JMenuItem menu3Item = new JMenuItem("SaveFile   Ctrl+S");
        menu1.add(menu3Item);

        JMenuItem menu4Item = new JMenuItem("ExitFile   Ctrl+Q");
        menu1.add(menu4Item);

        JMenuItem Edit_Menu_search_Item = new JMenuItem("Search   Ctrl+F");
        Edit_Menu.add(Edit_Menu_search_Item);
        Edit_Menu_search_Item.addActionListener(new SearchViewEv());

        JMenuItem Edit_Menu_ALLsearch_Item = new JMenuItem("searchALL   Ctrl+G");
        Edit_Menu.add(Edit_Menu_ALLsearch_Item);
        Edit_Menu_ALLsearch_Item.addActionListener(new SearchAllViewEv());

        JMenuItem Edit_Menu_change_Item = new JMenuItem("Change   Ctrl+R");
        Edit_Menu.add(Edit_Menu_change_Item);
        Edit_Menu_change_Item.addActionListener(new ChangeViewEv());

        JMenuItem Edit_Menu_ALLchange_Item = new JMenuItem("ChangeALl   Ctrl+T");
        Edit_Menu.add(Edit_Menu_ALLchange_Item);
        Edit_Menu_ALLchange_Item.addActionListener(new ChangeAllViewEv());

        st.start();

    }
    class SearchViewEv implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            SearchView searchView = new SearchView();
        }
    }
    class SearchAllViewEv implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            SearchAllView searchAllView = new SearchAllView();

        }
    }
    class ChangeViewEv implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            ChangeView changeView = new ChangeView();
        }
    }
    class ChangeAllViewEv implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            ChangeAllView changeAllView = new ChangeAllView();
        }
    }

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



