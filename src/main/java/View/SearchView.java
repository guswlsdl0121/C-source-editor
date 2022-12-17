package View;
// 다중 찾기 뷰

import Controller.*;
import javax.swing.*;
import java.awt.*;


public class SearchView extends JFrame
{
    public JTextField tf1;
    public JButton bt1;

    public EditController ed;

    public SearchView()
    {

        setLayout(null);
        tf1 = new JTextField();
        bt1 = new JButton();

        setTitle("단일 찾기");
        setSize(387, 100);
        setLocation(600, 400);
        setResizable(false);

        Container container = getContentPane();
        container.setLayout(null);
        tf1.setBounds(18, 20, 200, 30);
        bt1.setBounds(245, 20, 110, 30);

        bt1.setText("단일 찾기");


        container.add(tf1);
        container.add(bt1);


        bt1.addActionListener(new EditController.MyActionListener());
        setVisible(true);

    }

}

