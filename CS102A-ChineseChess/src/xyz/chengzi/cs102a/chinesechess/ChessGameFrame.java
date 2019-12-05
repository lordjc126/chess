package xyz.chengzi.cs102a.chinesechess;

import xyz.chengzi.cs102a.chinesechess.chess.ChessColor;
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
        statusLabel.setForeground(Color.RED);
        statusLabel.setLocation(0, 400);
        statusLabel.setSize(200, 30);
        ChessboardComponent chessboard = new ChessboardComponent(400, 400,statusLabel);
        add(chessboard);

        add(statusLabel);

        JButton button = new JButton("...");
        button.addActionListener((e) -> JOptionPane.showMessageDialog(this, "Hello, world!"));
        button.setLocation(370, 400);
        button.setSize(20, 10);
        add(button);

        JMenuBar bar = new JMenuBar();
        JMenuItem item = new JMenuItem("Undo");
        bar.add(item);
        setJMenuBar(bar);
        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(chessboard.getMove()-chessboard.getN() >= 2){
                    chessboard.loadGame(chessboard.getStringList().get(chessboard.getMove()-2-chessboard.getN()));
                    chessboard.setN(chessboard.getN()+1);
                    if(chessboard.getCurrentColor()==ChessColor.RED){
                        chessboard.setCurrentColor(ChessColor.BLACK);
                        chessboard.getWhoTurn().setText("BLACK TURN");
                        chessboard.getWhoTurn().setForeground(Color.BLACK);
                    }else if(chessboard.getCurrentColor()==ChessColor.BLACK){
                        chessboard.setCurrentColor(ChessColor.RED);
                        chessboard.getWhoTurn().setText("RED TURN");
                        chessboard.getWhoTurn().setForeground(Color.RED);
                    }
                }else if(chessboard.getMove()-chessboard.getN() == 1){
                    chessboard.loadGame(chessboard.initial());
                    chessboard.setCurrentColor(ChessColor.RED);
                    chessboard.getWhoTurn().setText("RED TURN");
                    chessboard.getWhoTurn().setForeground(Color.RED);
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ChessGameFrame mainFrame = new ChessGameFrame();
            mainFrame.setVisible(true);
        });
    }
}
