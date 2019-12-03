package xyz.chengzi.cs102a.chinesechess.chessboard;

import xyz.chengzi.cs102a.chinesechess.chess.*;
import xyz.chengzi.cs102a.chinesechess.listener.ChessListener;
import xyz.chengzi.cs102a.chinesechess.listener.ChessboardChessListener;

import javax.swing.*;
import java.awt.*;

public class ChessboardComponent extends JComponent {
    private ChessListener chessListener = new ChessboardChessListener(this);
    private ChessComponent[][] chessboard = new ChessComponent[10][9];
    private ChessColor currentColor = ChessColor.RED;
    private String ROB = "";

    public String getROB() {
        if(currentColor == ChessColor.RED){
            return "RED TURN";
        }
        return "BLACK TURN";
    }

    public ChessboardComponent(int width, int height) {
        setLayout(null); // Use absolute layout.
        setSize(width, height);

        ChessComponent.registerListener(chessListener);
        for (int i = 0; i < chessboard.length; i++) {
            for (int j = 0; j < chessboard[i].length; j++) {
                putChessOnBoard(new EmptySlotComponent(new ChessboardPoint(i, j), calculatePoint(i, j)));
            }
        }

        // FIXME: Initialize chessboard for testing only.
        initTestBoardC(0, 0, ChessColor.BLACK);
        initTestBoardC(0, 8, ChessColor.BLACK);
        initTestBoardHorseBlack(0, 1, ChessColor.BLACK);
        initTestBoardHorseBlack(0, 7, ChessColor.BLACK);
        initTestBoardEleBlack(0, 2, ChessColor.BLACK);
        initTestBoardEleBlack(0, 6, ChessColor.BLACK);
        initTestBoardShiRBlack(0, 3, ChessColor.BLACK);
        initTestBoardShiRBlack(0, 5, ChessColor.BLACK);
        initTestBoardBossBlack(0, 4, ChessColor.BLACK);
        initTestBoardPaoBlack(2, 7, ChessColor.BLACK);
        initTestBoardPaoBlack(2, 1, ChessColor.BLACK);
        initTestBoardSoldierBlack(3, 0, ChessColor.BLACK);
        initTestBoardSoldierBlack(3, 2, ChessColor.BLACK);
        initTestBoardSoldierBlack(3, 4, ChessColor.BLACK);
        initTestBoardSoldierBlack(3, 6, ChessColor.BLACK);
        initTestBoardSoldierBlack(3, 8, ChessColor.BLACK);
        initTestBoardc(9, 0, ChessColor.RED);
        initTestBoardc(9, 8, ChessColor.RED);
        initTestBoardHorseRed(9, 1, ChessColor.RED);
        initTestBoardHorseRed(9, 7, ChessColor.RED);
        initTestBoardEleRed(9, 2, ChessColor.RED);
        initTestBoardEleRed(9, 6, ChessColor.RED);
        initTestBoardShiRed(9, 3, ChessColor.RED);
        initTestBoardShiRed(9, 5, ChessColor.RED);
        initTestBoardPaoRed(7, 1, ChessColor.RED);
        initTestBoardPaoRed(7, 7, ChessColor.RED);
        initTestBoardBossRed(9, 4, ChessColor.RED);
        initTestBoardSoldierRed(6, 0, ChessColor.RED);
        initTestBoardSoldierRed(6, 2, ChessColor.RED);
        initTestBoardSoldierRed(6, 4, ChessColor.RED);
        initTestBoardSoldierRed(6, 6, ChessColor.RED);
        initTestBoardSoldierRed(6, 8, ChessColor.RED);
    }

    public ChessComponent[][] getChessboard() {
        return chessboard;
    }

    public ChessColor getCurrentColor() {
        return currentColor;
    }

    public void putChessOnBoard(ChessComponent chessComponent) {
        int row = chessComponent.getChessboardPoint().getX(), col = chessComponent.getChessboardPoint().getY();
        if (chessboard[row][col] != null) {
            remove(chessboard[row][col]);
        }
        add(chessboard[row][col] = chessComponent);
    }

    public void swapChessComponents(ChessComponent chess1, ChessComponent chess2) {
        // Note that chess1 has higher priority, 'destroys' chess2 if exists.
        if (!(chess2 instanceof EmptySlotComponent)) {
            remove(chess2);
            add(chess2 = new EmptySlotComponent(chess2.getChessboardPoint(), chess2.getLocation()));
        }
        chess1.swapLocation(chess2);
        int row1 = chess1.getChessboardPoint().getX(), col1 = chess1.getChessboardPoint().getY();
        chessboard[row1][col1] = chess1;
        int row2 = chess2.getChessboardPoint().getX(), col2 = chess2.getChessboardPoint().getY();
        chessboard[row2][col2] = chess2;
    }

    public void swapColor() {
        currentColor = currentColor == ChessColor.RED ? ChessColor.BLACK : ChessColor.RED;
    }

    private void initTestBoardC(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new ChariotBlack(new ChessboardPoint(row, col),
                calculatePoint(row, col), color);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    private void initTestBoardc(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new ChariotRed(new ChessboardPoint(row, col),
                calculatePoint(row, col), color);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    private void initTestBoardHorseBlack(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new HorseBlack(new ChessboardPoint(row, col),
                calculatePoint(row, col), color);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    private void initTestBoardHorseRed(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new HorseRed(new ChessboardPoint(row, col),
                calculatePoint(row, col), color);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    private void initTestBoardEleRed(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new EleChessComponentRed(new ChessboardPoint(row, col),
                calculatePoint(row, col), color);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    private void initTestBoardEleBlack(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new EleChessComponentBlack(new ChessboardPoint(row, col),
                calculatePoint(row, col), color);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    private void initTestBoardShiRed(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new ShiChessComponentRed(new ChessboardPoint(row, col),
                calculatePoint(row, col), color);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    private void initTestBoardShiRBlack(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new ShiChessComponentBlack(new ChessboardPoint(row, col),
                calculatePoint(row, col), color);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    private void initTestBoardBossRed(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new BossRed(new ChessboardPoint(row, col),
                calculatePoint(row, col), color);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    private void initTestBoardBossBlack(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new BossBlack(new ChessboardPoint(row, col),
                calculatePoint(row, col), color);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    private void initTestBoardPaoRed(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new PaoRed(new ChessboardPoint(row, col),
                calculatePoint(row, col), color);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    private void initTestBoardPaoBlack(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new PaoBlack(new ChessboardPoint(row, col),
                calculatePoint(row, col), color);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    private void initTestBoardSoldierRed(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new SoldierRed(new ChessboardPoint(row, col),
                calculatePoint(row, col), color);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    private void initTestBoardSoldierBlack(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new SoldierBlack(new ChessboardPoint(row, col),
                calculatePoint(row, col), color);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        paintBoardLine(g, 0, 0, 9, 0);
        paintBoardLine(g, 0, 8, 9, 8);
        paintHalfBoard(g, 0);
        paintHalfBoard(g, 5);
        paintKingSquare(g, 1, 4);
        paintKingSquare(g, 8, 4);
    }

    private void paintHalfBoard(Graphics g, int startRow) {
        for (int row = startRow; row < startRow + 5; row++) {
            paintBoardLine(g, row, 0, row, 8);
        }
        for (int col = 0; col < 9; col++) {
            paintBoardLine(g, startRow, col, startRow + 4, col);
        }
    }

    private void paintKingSquare(Graphics g, int centerRow, int centerCol) {
        paintBoardLine(g, centerRow - 1, centerCol - 1, centerRow + 1, centerCol + 1);
        paintBoardLine(g, centerRow - 1, centerCol + 1, centerRow + 1, centerCol - 1);
    }

    private void paintBoardLine(Graphics g, int rowFrom, int colFrom, int rowTo, int colTo) {
        int offsetX = ChessComponent.CHESS_SIZE.width / 2, offsetY = ChessComponent.CHESS_SIZE.height / 2;
        Point p1 = calculatePoint(rowFrom, colFrom), p2 = calculatePoint(rowTo, colTo);
        g.drawLine(p1.x + offsetX, p1.y + offsetY, p2.x + offsetX, p2.y + offsetY);
    }

    private Point calculatePoint(int row, int col) {
        return new Point(col * getWidth() / 9, row * getHeight() / 10);
    }
}
