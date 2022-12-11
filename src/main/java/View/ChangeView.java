package View;
// 다중 찾기 뷰

import Controller.EditController;

import javax.swing.*;
import java.awt.*;


public class ChangeView extends JFrame
{
    public JTextField tf1;
    public JButton bt1;

    public ChangeView()
    {

        setLayout(null);
        tf1 = new JTextField();
        bt1 = new JButton();

        setTitle("단일 바꾸기");
        setSize(387, 135);
        setLocation(600, 400);
        setResizable(false);

        Container container = getContentPane();
        container.setLayout(null);
        tf1.setBounds(10, 30, 200, 30);
        bt1.setBounds(260, 30, 110, 30);

        bt1.setText("단일 바꾸기");


        container.add(tf1);
        container.add(bt1);

        bt1.addActionListener(new EditController.MyActionListener());

        setVisible(true);

    }

}

