package xyz.chengzi.cs102a.chinesechess;

import xyz.chengzi.cs102a.chinesechess.chess.ChessColor;
import xyz.chengzi.cs102a.chinesechess.chessboard.ChessboardComponent;

import javax.swing.*;
import java.awt.*;

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
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ChessGameFrame mainFrame = new ChessGameFrame();
            mainFrame.setVisible(true);
        });
    }
}
