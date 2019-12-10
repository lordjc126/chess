package xyz.chengzi.cs102a.chinesechess.chess;

import com.sun.awt.AWTUtilities;
import org.jb2011.lnf.beautyeye.*;
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
        setSize(430, 1000);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainPanel = new JPanel();
        mainPanel.setOpaque(false);

        //------------------------------------------------------------------------------

        button1 = new JButton("开始游戏");
        button2 = new JButton("游戏设置");
        button3 = new JButton("退出游戏");
        Dimension preferredSize = new Dimension(300, 80);
        button1.setPreferredSize(preferredSize);
        button2.setPreferredSize(preferredSize);
        button3.setPreferredSize(preferredSize);
        Font f = new Font("华文行楷", Font.BOLD, 25);
        button1.setFont(f);
        button2.setFont(f);
        button3.setFont(f);
        button1.setLocation(0, 0);
        button2.setLocation(0, 0);
        button3.setLocation(0, 0);
        button1.addActionListener(this);
        button3.addActionListener(this);

        //-----------------------------------------------------------------------------设置按钮

        mainPanel.add(button1);
        mainPanel.add(button2);
        mainPanel.add(button3);
        mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 100, 150));
        setBak();
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

//----------------------------------------------------------------------------------------------

    private void setBak(){

        ((JPanel)this.getContentPane()).setOpaque(false);
        ImageIcon img = new ImageIcon("./11814432_144606674188_2 - 副本.jpg");
        JLabel background = new JLabel(img);
        this.getLayeredPane().add(background, new Integer(Integer.MIN_VALUE));
        background.setBounds(0, 0, img.getIconWidth(), img.getIconHeight());
    }




    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {

        try
        {
            org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();


        }
        catch(Exception e)
        {
            //TODO exception
        }





        SwingUtilities.invokeLater(() ->
        {
            MainFrame mainFrame= new MainFrame();
        });
    }
}

    

 
