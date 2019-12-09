package xyz.chengzi.cs102a.chinesechess.chess;

import xyz.chengzi.cs102a.chinesechess.ChessGameFrame;

import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame implements ActionListener {

    private JPanel mainPanel;
    private JButton button1;
    private JButton button2;
    private JButton button3;


    public MainFrame() {
        setTitle("Java Project: Chinese Chess");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainPanel = new JPanel();

        //------------------------------------------------------------------------------
        button1 = new JButton("开始游戏");
        button2 = new JButton("游戏设置");
        button3 = new JButton("退出游戏");
        Dimension preferredSize = new Dimension(160, 80);
        button1.setPreferredSize(preferredSize);
        button2.setPreferredSize(preferredSize);
        button3.setPreferredSize(preferredSize);
        Font f = new Font("华文行楷", Font.BOLD, 25);
        button1.setFont(f);
        button2.setFont(f);
        button3.setFont(f);
        button1.setLocation(370, 100);
        button2.setLocation(370, 100);
        button3.setLocation(370, 100);
        button1.addActionListener(this);
        button3.addActionListener(this);

        //-----------------------------------------------------------------------------设置按钮

        mainPanel.add(button1);
        mainPanel.add(button2);
        mainPanel.add(button3);
        mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 100, 100));


        ImageIcon backgroundPicture = new ImageIcon("./untitled.png");
        JLabel background = new JLabel(backgroundPicture);
        background.setBounds(0, 0, backgroundPicture.getIconWidth(), backgroundPicture.getIconHeight());
        mainPanel.add(background);
        this.add(mainPanel);
        this.setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==button1)
        {
            this.dispose();//点击按钮时frame1销毁,new一个frame2
            ChessGameFrame chessFrame = new ChessGameFrame();
            chessFrame.setVisible(true);

        }
        else if(e.getSource()==button3){
            this.dispose();
        }
    }



    public static void main(String[] args) {
        SwingUtilities.invokeLater(() ->
        {
            MainFrame mainFrame= new MainFrame();
        });
    }
}

    

 
