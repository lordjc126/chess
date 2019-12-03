package xyz.chengzi.cs102a.chinesechess.listener;

import xyz.chengzi.cs102a.chinesechess.chess.ChessComponent;
import xyz.chengzi.cs102a.chinesechess.chessboard.ChessboardComponent;

public class ChessboardChessListener extends ChessListener {
    private ChessboardComponent chessboardComponent;
    private ChessComponent first;

    public ChessboardChessListener(ChessboardComponent chessboardComponent) {
        this.chessboardComponent = chessboardComponent;
    }

    @Override
    public void onClick(ChessComponent chessComponent) {
        if (first == null) {
            if (handleFirst(chessComponent)) {
                chessComponent.setSelected(true);
                first = chessComponent;
                chessboardComponent.repaint();
            }
        } else {
            if (first == chessComponent) { // Double-click to unselect.
                chessComponent.setSelected(false);
                first = null;
                chessboardComponent.repaint();
            } else if (handleSecond(chessComponent)) {
                chessboardComponent.swapChessComponents(first, chessComponent);
                chessboardComponent.swapColor();

                first.setSelected(false);
                first = null;
                chessboardComponent.repaint();
            }
        }
    }

    private boolean handleFirst(ChessComponent chessComponent) {
        return chessComponent.getChessColor() == chessboardComponent.getCurrentColor();
    }

    private boolean handleSecond(ChessComponent chessComponent) {
        return chessComponent.getChessColor() != chessboardComponent.getCurrentColor() &&
                first.canMoveTo(chessboardComponent.getChessboard(), chessComponent.getChessboardPoint());
    }
}
