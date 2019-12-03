package xyz.chengzi.cs102a.chinesechess.chess;

import xyz.chengzi.cs102a.chinesechess.chessboard.ChessboardPoint;

import java.awt.*;

public class SoldierRed extends ChessComponent{
    public SoldierRed(ChessboardPoint chessboardPoint, Point location, ChessColor color) {
        super(chessboardPoint, location, color);
        ChessName = "s";
        super.setChessId();
    }

    @Override
    public boolean canMoveTo(ChessComponent[][] chessboard, ChessboardPoint destination) {
        ChessboardPoint source = getChessboardPoint();
        if(source.getX() > 4 && source.getX()==destination.getX()+1 && source.getY()==destination.getY()){
        return true;
    }else if(source.getX() <= 4){
        if(source.getX() == destination.getX()+1 && source.getY()==destination.getY()){
            return true;
        }
        if(source.getX() == destination.getX() && source.getY()==destination.getY()+1){
            return true;
        }
        if(source.getX() == destination.getX() && source.getY()==destination.getY()-1){
            return true;
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
        g.drawString("兵", 15, 25); // FIXME: Use library to find the correct offset.
        if (isSelected()) { // Highlights the chess if selected.
            g.setColor(Color.RED);
            g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
        }
    }
}
