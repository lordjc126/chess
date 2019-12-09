package xyz.chengzi.cs102a.chinesechess;

import xyz.chengzi.cs102a.chinesechess.chess.ChessColor;
import xyz.chengzi.cs102a.chinesechess.chess.MainFrame;
import xyz.chengzi.cs102a.chinesechess.chessboard.ChessboardComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChessGameFrame extends JFrame {
    public ChessGameFrame() {
        setTitle("Chinese Chess");
        setSize(411, 500);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null);

        JLabel statusLabel = new JLabel("RED TURN");
        Font f = new Font("华文行楷", Font.BOLD, 20);//设置字体大小
        statusLabel.setFont(f);
        statusLabel.setForeground(Color.RED);
        statusLabel.setLocation(0, 400);
        statusLabel.setSize(200, 30);
        ChessboardComponent chessboard = new ChessboardComponent(400, 400, statusLabel);
        add(chessboard);
        add(statusLabel);

        JButton button = new JButton("点开有惊喜哦");
        button.addActionListener((e) -> JOptionPane.showMessageDialog(this, "Fuck You!"));
        button.setLocation(170, 400);
        button.setSize(200, 20);
        add(button);

        JMenuBar bar = new JMenuBar();
        JMenuItem item = new JMenuItem("Undo");
        JMenuItem item2 = new JMenuItem("UndoUndo");
        bar.add(item);
        bar.add(item2);
        setJMenuBar(bar);
        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (chessboard.getMove() - chessboard.getN() >= 2) {
                    chessboard.loadGame(chessboard.getStringList().get(chessboard.getMove() - 2 - chessboard.getN()));
                    chessboard.setN(chessboard.getN() + 1);
                    if (chessboard.getCurrentColor() == ChessColor.RED) {
                        chessboard.setCurrentColor(ChessColor.BLACK);
                        chessboard.getWhoTurn().setText("BLACK TURN");
                        chessboard.getWhoTurn().setForeground(Color.BLACK);
                    } else if (chessboard.getCurrentColor() == ChessColor.BLACK) {
                        chessboard.setCurrentColor(ChessColor.RED);
                        chessboard.getWhoTurn().setText("RED TURN");
                        chessboard.getWhoTurn().setForeground(Color.RED);
                    }
                    chessboard.setStopUndoing(true);
                } else if (chessboard.getMove() - chessboard.getN() == 1) {
                    chessboard.loadGame(chessboard.initial());
                    chessboard.setCurrentColor(ChessColor.RED);
                    chessboard.getWhoTurn().setText("RED TURN");
                    chessboard.getWhoTurn().setForeground(Color.RED);
                    chessboard.setN(chessboard.getN() + 1);
                    chessboard.setStopUndoing(true);
                }
            }
        });

        item2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (chessboard.getN() > 0) {
                    chessboard.loadGame(chessboard.getStringList().get(chessboard.getMove() - chessboard.getN()));
                    chessboard.setN(chessboard.getN() - 1);
                    if (chessboard.getCurrentColor() == ChessColor.RED) {
                        chessboard.setCurrentColor(ChessColor.BLACK);
                        chessboard.getWhoTurn().setText("BLACK TURN");
                        chessboard.getWhoTurn().setForeground(Color.BLACK);
                    } else if (chessboard.getCurrentColor() == ChessColor.BLACK) {
                        chessboard.setCurrentColor(ChessColor.RED);
                        chessboard.getWhoTurn().setText("RED TURN");
                        chessboard.getWhoTurn().setForeground(Color.RED);
                    }
                }
            }
        });

    }

    /*public static void main(String[] args) {
        MainFrame mainFrame = new MainFrame();
        SwingUtilities.invokeLater(() ->
        {
            ChessGameFrame chessFrame = new ChessGameFrame();
            chessFrame.setVisible(true);
        });
    }*/
}
