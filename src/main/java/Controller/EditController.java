
package Controller;

import View.*;

import java.awt.event.*;
import java.util.StringTokenizer;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;



/**
 * EditMenu 와 SearchMenu 의 처리를 위한 컨트롤러 class
 *
 * @author 김현진
 * @version 1.0
 * @see JFrame
 * @see ActionListener
 */
public class EditController extends JFrame implements ActionListener {
    private static SearchView SearchView;
    private static ChangeView ChangeView;
    private static ChangeAllView ChangeAllView;

    private static SearchAllView SearchAllView;



    /**
     * Edit-Menu 버튼을 눌렀을 때 생기는 이벤트 처리
     * @author 김현진
     * @see ActionEvent
     */
    public void actionPerformed(ActionEvent e)
    {
        if (e.getActionCommand().equals("Search   Ctrl+F"))
        {
            //edit=new Edit_View();
            SearchView = new SearchView();
        }
        else if (e.getActionCommand().equals("SearchALL   Ctrl+G"))
        {
            SearchAllView =new SearchAllView();
        }
        else if (e.getActionCommand().equals("Change   Ctrl+R"))
        {
            ChangeView = new ChangeView();
        }
        else if (e.getActionCommand().equals("ChangeALL   Ctrl+T"))
        {
            ChangeAllView = new ChangeAllView();
        }
        else if(e.getActionCommand().equals("remove highlight"))
        {
            MyActionListener.text_highlight.removeAllHighlights();
        }
    }

    /**
     * 하이라이트의 마우스 클릭 처리만을 위한 클래스
     * @author 김현진
     * @implNote only for highlight
     */
    public static class MyMouseEvent implements MouseListener
    {
        //마우스가 해당 컴포넌트를 클릭했을때.
        public void mouseClicked(MouseEvent e) throws NullPointerException
        {
            if(MyActionListener.text_highlight!= null)
            {
                MyActionListener.text_highlight.removeAllHighlights();
            }
            }
        public void mouseEntered(MouseEvent e) { }

        public void mouseExited(MouseEvent e) { }

        public void mousePressed(MouseEvent e) { }

        public void mouseReleased(MouseEvent e) { }
    }



    /**
     * 단축키 입력 이벤트를 처리하는 클래스
     * @implNote  ex) VK_F => ctrl F를 뜻합니당
     * @author 김현진
     * @version 1.1
     * @see java.awt.event.KeyAdapter
     * @see java.awt.event.KeyEvent
     */
    public static class MykeyListener extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_F) {
                SearchView = new SearchView();
            } else if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_G) {
                SearchAllView = new SearchAllView();
            } else if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_R) {
                ChangeView = new ChangeView();
            } else if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_T) {
                ChangeAllView = new ChangeAllView();
            }

        }
    }


    /**
     * searchView, editView 내부 버튼 액션에 해당하는 클래스
     * @implNote  issue - 밑에 서술하는 변수들에 static을 빼면 바꾸기 동작이 안 됨
     * @version 1.1
     * @author 김현진
     */
    public static class MyActionListener implements ActionListener
    {
        private final JTextPane textPane;
        public static Highlighter text_highlight;
        private static int offset = 0;
        private static int last_offset = 0;
        private static String find_text;
        private static int count = 0;
        private static int word_count = 0;
        private static String conversion_word = "";
        private static String viewText;

        public MyActionListener()
        {
            this.textPane = MainView.textPane;
            this.text_highlight = this.textPane.getHighlighter();
        }

        /**
         * 오버라이딩한 메소드. 그냥 눈으로 흘깃 보세요..
         * @param e the event to be processed
         */
        @Override
        public void actionPerformed(ActionEvent e)
        {
            int fi = 0;
            int max;
            JButton currentButton = (JButton)e.getSource();

            if(currentButton.getText().equals("찾기"))
            {
                text_highlight.removeAllHighlights();
                viewText = textPane.getText().replace("\r\n", "\n");
                find_text = SearchView.tf1.getText();

                if(viewText.contains(find_text))
                {
                    word_count = 1;
                    offset = viewText.indexOf(find_text, offset);
                    count = 0;
                    try
                    {
                        text_highlight.addHighlight(offset, offset + find_text.length(), DefaultHighlighter.DefaultPainter);
                    }
                    catch (BadLocationException CanNotSearch)
                    {
                        CanNotSearch.printStackTrace();
                    }
                    offset = offset + find_text.length();

                    if(offset > viewText.lastIndexOf(find_text))
                    {
                        last_offset = offset;
                        count = 1;
                        offset = 0;
                    }
                }
                else
                {
                    word_count=0;
                    JOptionPane.showMessageDialog(null, "더 이상  찾는 단어가 없습니다.", "message", JOptionPane.WARNING_MESSAGE);
                }
            }
            else if(currentButton.getText().equals("다중 찾기"))
            {
                text_highlight.removeAllHighlights();
                viewText = textPane.getText().replace("\r\n", "\n");
                find_text = SearchAllView.tf1.getText();


                StringTokenizer st = new StringTokenizer(viewText, "\u0020\t\r\n");
                if(viewText.contains(find_text))
                {
                    max = viewText.lastIndexOf(find_text);
                    for(int k=0; k < max; k++)
                    {
                        fi = viewText.indexOf(find_text, fi);
                        try
                        {
                            text_highlight.addHighlight(fi, fi+find_text.length(), DefaultHighlighter.DefaultPainter);
                        }
                        catch (BadLocationException CanNotSearch)
                        {
                            CanNotSearch.printStackTrace();
                        }
                        fi = fi + find_text.length();
                        if(fi >= max + 1) break;
                    }
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "더 이상  찾는 단어가 없습니다.", "message", JOptionPane.WARNING_MESSAGE);
                }
            }
            else if(currentButton.getText().equals("단일 바꾸기"))
            {
                if(word_count != 0)
                {
                    conversion_word = ChangeView.tf1.getText();
                    if(count == 1)
                    {
                        textPane.select(last_offset-find_text.length(), last_offset);
                        textPane.replaceSelection(conversion_word);
                        try {
                            text_highlight.addHighlight(last_offset - find_text.length(), last_offset - find_text.length() + conversion_word.length(), DefaultHighlighter.DefaultPainter);
                        } catch (BadLocationException e1) {
                            e1.printStackTrace();
                        }
                        count=0;
                    }
                    else
                    {
                        textPane.select(offset-find_text.length(), offset);
                        textPane.replaceSelection(conversion_word);
                        try {
                            text_highlight.addHighlight(offset-find_text.length(), offset-find_text.length()+ conversion_word.length(), DefaultHighlighter.DefaultPainter);
                        } catch (BadLocationException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
                word_count=0;
            }
            else if(currentButton.getText().equals("다중 바꾸기"))
            {
                int cpos=0;
                String cur, after;
                conversion_word = ChangeAllView.tf1.getText();
                cur = textPane.getText();
                after = cur.replaceAll(find_text, conversion_word);
                textPane.selectAll();
                textPane.replaceSelection(after);
                int max2;
                max2 = after.lastIndexOf(conversion_word);
                for(int k=0; k<after.length(); k++)
                {
                    cpos = after.indexOf(conversion_word, cpos);
                    try {
                        text_highlight.addHighlight(cpos, cpos+ conversion_word.length(), DefaultHighlighter.DefaultPainter);
                    } catch (BadLocationException ble) {
                    }
                    cpos = cpos+ conversion_word.length();
                    if(cpos >= max2 + 1)
                        break;
                }
            }
        }
    }




}
