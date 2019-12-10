package xyz.chengzi.cs102a.chinesechess.listener;

import xyz.chengzi.cs102a.chinesechess.ChessGameFrame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Timekeeper implements ActionListener, ChangeListener {
    JFrame frame = null;
    JProgressBar progressbar;
    JLabel label;
    Timer timer;
    JButton b;



    public Timekeeper() {
        frame = new JFrame("软件安装");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container contentPane = frame.getContentPane();
        label = new JLabel(" ", JLabel.CENTER);    //创建显示进度信息的文本标签
        progressbar = new JProgressBar();    //创建一个进度条
        progressbar.setOrientation(JProgressBar.HORIZONTAL);
        progressbar.setMinimum(0);
        progressbar.setMaximum(100);
        progressbar.setValue(0);
        progressbar.setStringPainted(true);
        progressbar.addChangeListener(this);    //添加事件监听器
        //设置进度条的几何形状
        progressbar.setPreferredSize(new Dimension(300, 20));
        progressbar.setBorderPainted(true);
        progressbar.setBackground(Color.pink);
        //添加启动按钮
        JPanel panel = new JPanel();
        b = new JButton("安装");
        b.setForeground(Color.blue);
        //添加事件监听器
        b.addActionListener(this);
        panel.add(b);
        timer = new Timer(100, this);    //创建一个计时器，计时间隔为100毫秒
        //把组件添加到frame中
        contentPane.add(panel, BorderLayout.NORTH);
        contentPane.add(progressbar, BorderLayout.CENTER);
        contentPane.add(label, BorderLayout.SOUTH);
        frame.pack();
        frame.setVisible(true);
    }


    //实现事件监听器接口中的方法
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == b)
            timer.start();
        if (e.getSource() == timer) {
            int value = progressbar.getValue();
            if (value < 100) {
                progressbar.setValue(++value);
            } else {
                timer.stop();
                frame.dispose();
            }
        }
    }


    public void stateChanged(ChangeEvent e1)    //实现事件监听器接口中的方法
    {
        int value = progressbar.getValue();
        if (e1.getSource() == progressbar) {
            label.setText("目前已完成进度：" + Integer.toString(value) + " %");
            label.setForeground(Color.blue);
        }
    }
}