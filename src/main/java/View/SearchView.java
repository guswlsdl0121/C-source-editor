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

public class SearchView extends JFrame {
    public JTextField tf1;
    public JButton bt1;
    public JButton bt2;

    public SearchView() {
        this.setLayout((LayoutManager)null);
        this.tf1 = new JTextField();
        this.bt1 = new JButton();
        this.bt2 = new JButton();
        this.setTitle("찾기");
        this.setSize(387, 135);
        this.setLocation(600, 400);
        this.setResizable(false);
        Container container = this.getContentPane();
        container.setLayout((LayoutManager)null);
        this.tf1.setBounds(10, 30, 200, 30);
        this.bt1.setBounds(260, 15, 110, 30);
        this.bt2.setBounds(260, 60, 110, 30);
        this.bt1.setText("찾기");
        this.bt2.setText("모두 찾기");
        container.add(this.tf1);
        container.add(this.bt1);
        container.add(this.bt2);
        this.bt1.addActionListener(new EditController.MyActionListener());
        this.bt2.addActionListener(new EditController.MyActionListener());
        this.setVisible(true);
    }

}
