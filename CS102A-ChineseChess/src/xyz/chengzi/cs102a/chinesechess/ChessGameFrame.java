package xyz.chengzi.cs102a.chinesechess;

import xyz.chengzi.cs102a.chinesechess.chess.ChessColor;
import xyz.chengzi.cs102a.chinesechess.chessboard.ChessboardComponent;

import javax.swing.*;

public class ChessGameFrame extends JFrame {
    public ChessGameFrame() {
        setTitle("2019 CS102A Project");
        setSize(411, 450);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null);

        ChessboardComponent chessboard = new ChessboardComponent(400, 400);
        add(chessboard);

        JLabel statusLabel = new JLabel(chessboard.getROB());
        statusLabel.setLocation(0, 400);
        statusLabel.setSize(200, 30);
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
