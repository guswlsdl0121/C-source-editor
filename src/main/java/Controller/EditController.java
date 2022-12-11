//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package Controller;

import View.EditView;
import View.MainView;
import View.SearchView;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.StringTokenizer;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;

public class EditController extends JFrame implements ActionListener {
    private static SearchView SearchView;
    private static EditView EditView;

    public EditController() {
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Search   Ctrl+F")) {
            SearchView = new SearchView();
        } else if (e.getActionCommand().equals("searchALL   Ctrl+G")) {
            SearchView = new SearchView();
        } else if (e.getActionCommand().equals("Change   Ctrl+R")) {
            EditView = new EditView();
        } else if (e.getActionCommand().equals("ChangeALl   Ctrl+T")) {
            EditView = new EditView();
        }

    }

    public static class MyActionListener implements ActionListener {
        private final JTextPane textPane;
        private final Highlighter text_highlight;
        private static int offset = 0;
        private static int last_offset = 0;
        private static String find_text;
        private static int count = 0;
        private static int word_count = 0;
        private static String conversion_word = "";
        private static String viewText;

        public MyActionListener() {
            this.textPane = MainView.textPane;
            this.text_highlight = this.textPane.getHighlighter();
        }

        public void actionPerformed(ActionEvent e) {
            int fi = 0;
            JButton currentButton = (JButton)e.getSource();
            if (!currentButton.getText().equals("찾기") && !currentButton.getText().equals("단일 선택")) {
                if (!currentButton.getText().equals("모두 찾기") && !currentButton.getText().equals("모두 선택")) {
                    if (currentButton.getText().equals("바꾸기")) {
                        if (word_count != 0) {
                            conversion_word = EditController.EditView.tf2.getText();
                            if (count == 1) {
                                this.textPane.select(last_offset - find_text.length(), last_offset);
                                this.textPane.replaceSelection(conversion_word);

                                try {
                                    this.text_highlight.addHighlight(last_offset - find_text.length(), last_offset - find_text.length() + conversion_word.length(), DefaultHighlighter.DefaultPainter);
                                } catch (BadLocationException var13) {
                                    var13.printStackTrace();
                                }

                                count = 0;
                            } else {
                                this.textPane.select(offset - find_text.length(), offset);
                                this.textPane.replaceSelection(conversion_word);

                                try {
                                    this.text_highlight.addHighlight(offset - find_text.length(), offset - find_text.length() + conversion_word.length(), DefaultHighlighter.DefaultPainter);
                                } catch (BadLocationException var12) {
                                    var12.printStackTrace();
                                }
                            }
                        }

                        word_count = 0;
                    } else if (currentButton.getText().equals("모두 바꾸기")) {
                        int cpos = 0;
                        conversion_word = EditController.EditView.tf2.getText();
                        String cur = this.textPane.getText();
                        String after = cur.replaceAll(find_text, conversion_word);
                        this.textPane.selectAll();
                        this.textPane.replaceSelection(after);
                        int max2 = after.lastIndexOf(conversion_word);

                        for(int k = 0; k < after.length(); ++k) {
                            cpos = after.indexOf(conversion_word, cpos);

                            try {
                                this.text_highlight.addHighlight(cpos, cpos + conversion_word.length(), DefaultHighlighter.DefaultPainter);
                            } catch (BadLocationException var11) {
                            }

                            cpos += conversion_word.length();
                            if (cpos >= max2) {
                                break;
                            }
                        }
                    }
                } else {
                    this.text_highlight.removeAllHighlights();
                    viewText = this.textPane.getText().replace("\r\n", "\n");
                    if (currentButton.getText().equals("모두 찾기")) {
                        find_text = EditController.SearchView.tf1.getText();
                    } else if (currentButton.getText().equals("모두 선택")) {
                        find_text = EditController.EditView.tf1.getText();
                    }

                    new StringTokenizer(viewText, " \t\r\n");
                    if (viewText.contains(find_text)) {
                        int max = viewText.lastIndexOf(find_text);
                        int k = 0;

                        while(true) {
                            fi = viewText.indexOf(find_text, fi);

                            try {
                                this.text_highlight.addHighlight(fi, fi + find_text.length(), DefaultHighlighter.DefaultPainter);
                            } catch (BadLocationException var14) {
                                var14.printStackTrace();
                            }

                            fi += find_text.length();
                            if (fi >= max) {
                                break;
                            }

                            ++k;
                        }
                    } else {
                        JOptionPane.showMessageDialog((Component)null, "더 이상  찾는 단어가 없습니다.", "message", 2);
                    }
                }
            } else {
                this.text_highlight.removeAllHighlights();
                viewText = this.textPane.getText().replace("\r\n", "\n");
                if (currentButton.getText().equals("찾기")) {
                    find_text = EditController.SearchView.tf1.getText();
                } else if (currentButton.getText().equals("단일 선택")) {
                    find_text = EditController.EditView.tf1.getText();
                }

                if (viewText.contains(find_text)) {
                    word_count = 1;
                    offset = viewText.indexOf(find_text, offset);
                    count = 0;

                    try {
                        this.text_highlight.addHighlight(offset, offset + find_text.length(), DefaultHighlighter.DefaultPainter);
                    } catch (BadLocationException var15) {
                        var15.printStackTrace();
                    }

                    offset += find_text.length();
                    if (offset > viewText.lastIndexOf(find_text)) {
                        last_offset = offset;
                        count = 1;
                        offset = 0;
                    }
                } else {
                    word_count = 0;
                    JOptionPane.showMessageDialog((Component)null, "더 이상  찾는 단어가 없습니다.", "message", 2);
                }
            }

        }
    }

    static class MykeyListener extends KeyAdapter {
        MykeyListener() {
        }

        public void keyPressed(KeyEvent e) {
            if (e.isControlDown() && e.getKeyCode() == 70) {
                EditController.SearchView = new SearchView();
            } else if (e.isControlDown() && e.getKeyCode() == 71) {
                EditController.SearchView = new SearchView();
            } else if (e.isControlDown() && e.getKeyCode() == 82) {
                EditController.EditView = new EditView();
            } else if (e.isControlDown() && e.getKeyCode() == 84) {
                EditController.EditView = new EditView();
            }

        }
    }
}