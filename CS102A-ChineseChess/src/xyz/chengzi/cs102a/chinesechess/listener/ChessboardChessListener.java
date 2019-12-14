package xyz.chengzi.cs102a.chinesechess.listener;

import xyz.chengzi.cs102a.chinesechess.chess.ChessColor;
import xyz.chengzi.cs102a.chinesechess.chess.ChessComponent;
import xyz.chengzi.cs102a.chinesechess.chess.EmptySlotComponent;
import xyz.chengzi.cs102a.chinesechess.chess.Mark;
import xyz.chengzi.cs102a.chinesechess.chessboard.ChessboardComponent;
import xyz.chengzi.cs102a.chinesechess.chessboard.ChessboardPoint;

import java.util.ArrayList;

public class ChessboardChessListener extends ChessListener {
    private ChessboardComponent chessboardComponent;
    private ChessComponent first;
    private ArrayList<ChessComponent> rememberChess = new ArrayList<>();

    public ChessboardChessListener(ChessboardComponent chessboardComponent) {
        this.chessboardComponent = chessboardComponent;
    }

    @Override
    public void onClick(ChessComponent chessComponent) {
        if (first == null) {
            if (handleFirst(chessComponent)) {
                chessComponent.setSelected(true);

//                for (int i = 0; i < chessboardComponent.getChessboard().length; i++) {
//                    for (int j = 0; j < chessboardComponent.getChessboard()[0].length; j++) {
//                        if(chessComponent.getChessName().equals(chessboardComponent.getChessboard()[i][j].getChessName())){
//                            continue;
//                        }else{
//                            if(chessComponent.canMoveTo(chessboardComponent.getChessboard(),chessboardComponent.getChessboard()[i][j].getChessboardPoint())
//                                    && chessComponent.getChessColor() != chessboardComponent.getChessboard()[i][j].getChessColor()){
//                                if(chessComponent.getChessColor() != chessboardComponent.getChessboard()[i][j].getChessColor()){
//                                    remember(chessboardComponent.getChessboard()[i][j]);
//                                }
//                                chessboardComponent.putChessOnBoard(new Mark(chessboardComponent.getChessboard()[i][j].getChessboardPoint(),
//                                        chessboardComponent.calculatePoint(i,j),ChessColor.NONE));
//                            }
//                        }
//
//                    }
//                }

                first = chessComponent;
                chessboardComponent.repaint();
            }
        } else {
            if (first == chessComponent) { // Double-click to unselect.
                chessComponent.setSelected(false);
                first = null;

//                for (int i = 0; i < chessboardComponent.getChessboard().length; i++) {
//                    for (int j = 0; j < chessboardComponent.getChessboard()[0].length; j++) {
//                        if(chessboardComponent.getChessboard()[i][j].getChessName().equals("Mark")) {
//                            chessboardComponent.putChessOnBoard(new EmptySlotComponent(chessboardComponent.getChessboard()[i][j].getChessboardPoint(),
//                                    chessboardComponent.calculatePoint(i, j)));
//                            for (int k = 0; k < rememberChess.size(); k++) {
//                                chessboardComponent.putChessOnBoard(rememberChess.get(k));
//                            }
//                            rememberChess.clear();
//                        }
//                    }
//                }

                chessboardComponent.repaint();
            } else if (handleSecond(chessComponent)) {

//                for (int i = 0; i < chessboardComponent.getChessboard().length; i++) {
//                    for (int j = 0; j < chessboardComponent.getChessboard()[0].length; j++) {
//                        if(chessboardComponent.getChessboard()[i][j].getChessName().equals("Mark")) {
//                            chessboardComponent.putChessOnBoard(new EmptySlotComponent(chessboardComponent.getChessboard()[i][j].getChessboardPoint(),
//                                    chessboardComponent.calculatePoint(i, j)));
//                            for (int k = 0; k < rememberChess.size(); k++) {
//                                chessboardComponent.putChessOnBoard(rememberChess.get(k));
//                            }
//                            rememberChess.clear();
//                        }
//                    }
//                }
                chessboardComponent.repaint();
                chessboardComponent.swapChessComponents(first, chessComponent);
                chessboardComponent.swapColor();
                first.setSelected(false);
                first = null;
            }
        }
    }

        private boolean handleFirst (ChessComponent chessComponent){
            return chessComponent.getChessColor() == chessboardComponent.getCurrentColor();
        }

        private boolean handleSecond (ChessComponent chessComponent){
            if(chessComponent.getChessName().equals("Mark")){
                return true;
            }else {
                return chessComponent.getChessColor() != chessboardComponent.getCurrentColor() &&
                        first.canMoveTo(chessboardComponent.getChessboard(), chessComponent.getChessboardPoint());
            }
        }

        private void remember(ChessComponent chessComponent){
            rememberChess.add(chessComponent);
        }

    }
