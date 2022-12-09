//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package View;

import Controller.EditController;
import java.awt.Container;
import java.awt.LayoutManager;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class EditView extends JFrame {
    public JTextField tf1;
    public JTextField tf2;
    public JButton bt1;
    public JButton bt2;
    public JButton bt3;
    public JButton bt4;

    public EditView() {
        this.setLayout((LayoutManager)null);
        this.tf1 = new JTextField();
        this.tf2 = new JTextField();
        this.bt1 = new JButton();
        this.bt2 = new JButton();
        this.bt3 = new JButton();
        this.bt4 = new JButton();
        this.setTitle("바꾸기");
        this.setSize(387, 135);
        this.setLocation(600, 400);
        this.setResizable(false);
        Container container = this.getContentPane();
        container.setLayout((LayoutManager)null);
        this.tf1.setBounds(10, 15, 150, 30);
        this.tf2.setBounds(10, 60, 150, 30);
        this.bt1.setBounds(260, 15, 110, 30);
        this.bt2.setBounds(260, 60, 110, 30);
        this.bt3.setBounds(165, 15, 90, 30);
        this.bt4.setBounds(165, 60, 90, 30);
        this.bt1.setText("모두 선택");
        this.bt2.setText("모두 바꾸기");
        this.bt3.setText("단일 선택");
        this.bt4.setText("바꾸기");
        container.add(this.tf1);
        container.add(this.tf2);
        container.add(this.bt1);
        container.add(this.bt2);
        container.add(this.bt3);
        container.add(this.bt4);
        this.bt1.addActionListener(new EditController.MyActionListener());
        this.bt2.addActionListener(new EditController.MyActionListener());
        this.bt3.addActionListener(new EditController.MyActionListener());
        this.bt4.addActionListener(new EditController.MyActionListener());
        this.setVisible(true);
    }
}
