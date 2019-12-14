package xyz.chengzi.cs102a.chinesechess.chess;

import xyz.chengzi.cs102a.chinesechess.chessboard.ChessboardPoint;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;

public class Mark extends ChessComponent {


    public Mark(ChessboardPoint chessboardPoint, Point location, ChessColor chessColor) {
        super(chessboardPoint, location, chessColor.NONE);
        ChessName = "Mark";
        super.setChessId();
    }

    @Override
    public boolean canMoveTo(ChessComponent[][] chessboard, ChessboardPoint destination) {
        return false;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.green);
        g.fillOval(0, 0, getWidth() - 1, getHeight() - 1);
        g.setColor(Color.green);
        g.drawOval(2, 2, getWidth() - 5, getHeight() - 5);

    }
}
