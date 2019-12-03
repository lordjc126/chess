package xyz.chengzi.cs102a.chinesechess.chess;

import xyz.chengzi.cs102a.chinesechess.chessboard.ChessboardPoint;

import java.awt.*;

public class EleChessComponentBlack extends ChessComponent {
    public EleChessComponentBlack(ChessboardPoint chessboardPoint, Point location, ChessColor color) {
        super(chessboardPoint, location, color);
        ChessName = "E";
        super.setChessId();
    }

    @Override
    public boolean canMoveTo(ChessComponent[][] chessboard, ChessboardPoint destination) {
    ChessboardPoint source = getChessboardPoint();

        if(getChessColor()==ChessColor.BLACK && destination.getX() <= 4) {
        if (source.getX() == destination.getX() + 2 && source.getY() == destination.getY() + 2) {
            if (chessboard[source.getX() - 1][source.getY() - 1] instanceof EmptySlotComponent) {
                return true;
            }
        } else if (source.getX() == destination.getX() + 2 && source.getY() == destination.getY() - 2) {
            if (chessboard[source.getX() - 1][source.getY() + 1] instanceof EmptySlotComponent) {
                return true;
            }
        } else if (source.getX() == destination.getX() - 2 && source.getY() == destination.getY() - 2) {
            if (chessboard[source.getX() + 1][source.getY() + 1] instanceof EmptySlotComponent) {
                return true;
            }
        } else if (source.getX() == destination.getX() - 2 && source.getY() == destination.getY() + 2) {
            if (chessboard[source.getX() + 1][source.getY() - 1] instanceof EmptySlotComponent) {
                return true;
            }
        }
    }

        return false;
}

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(CHESS_COLOR);
        g.fillOval(0, 0, getWidth() - 1, getHeight() - 1);
        g.setColor(getChessColor().getColor());
        g.drawOval(2, 2, getWidth() - 5, getHeight() - 5);
        g.setColor(Color.BLACK);
        g.drawString("象", 15, 25); // FIXME: Use library to find the correct offset.
        if (isSelected()) { // Highlights the chess if selected.
            g.setColor(Color.RED);
            g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
        }
    }
}
