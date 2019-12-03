package xyz.chengzi.cs102a.chinesechess.chess;

import xyz.chengzi.cs102a.chinesechess.chessboard.ChessboardPoint;

import java.awt.*;

public class EmptySlotComponent extends ChessComponent {
    public EmptySlotComponent(ChessboardPoint chessboardPoint, Point location) {
        super(chessboardPoint, location, ChessColor.NONE);
        ChessName = "em";
    }

    @Override
    public boolean canMoveTo(ChessComponent[][] chessboard, ChessboardPoint destination) {
        return false;
    }
}
