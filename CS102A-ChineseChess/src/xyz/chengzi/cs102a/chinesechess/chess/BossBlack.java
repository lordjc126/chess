package xyz.chengzi.cs102a.chinesechess.chess;

import xyz.chengzi.cs102a.chinesechess.chessboard.ChessboardPoint;

import java.awt.*;

public class BossBlack extends ChessComponent{
    public BossBlack(ChessboardPoint chessboardPoint, Point location, ChessColor color) {
        super(chessboardPoint, location, color);
        ChessName = "G";
        super.setChessId();
    }

    @Override
    public boolean canMoveTo(ChessComponent[][] chessboard, ChessboardPoint destination) {
        ChessboardPoint source = getChessboardPoint();
        if(getChessColor()==ChessColor.BLACK && destination.getX()<=2 && destination.getY()>=3 && destination.getY()<=5){
            if(source.getX()==destination.getX() && source.getY()==destination.getY()+1){
                return true;
            }
            if(source.getX()==destination.getX() && source.getY()==destination.getY()-1){
                return true;
            }
            if(source.getX()==destination.getX()+1 && source.getY()==destination.getY()){
                return true;
            }
            if(source.getX()==destination.getX()-1 && source.getY()==destination.getY()){
                return true;
            }
        }
        if(chessboard[destination.getX()][destination.getY()].getChessboardPoint().getId().equals("g") &&
                source.getY()==destination.getY()){
            int col = source.getY();
            for (int row = Math.min(source.getX(), destination.getX()) + 1;
                 row < Math.max(source.getX(), destination.getX()); row++) {
                if (!(chessboard[row][col] instanceof EmptySlotComponent)) {
                    return false;
                }
            }
            return true;
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
        g.drawString("将", 15, 25); // FIXME: Use library to find the correct offset.
        if (isSelected()) { // Highlights the chess if selected.
            g.setColor(Color.RED);
            g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
        }
    }
}
