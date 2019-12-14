package xyz.chengzi.cs102a.chinesechess.chessboard;

import xyz.chengzi.cs102a.chinesechess.chess.*;
import xyz.chengzi.cs102a.chinesechess.listener.ChessListener;
import xyz.chengzi.cs102a.chinesechess.listener.ChessboardChessListener;
import xyz.chengzi.cs102a.chinesechess.listener.GameSound;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

public class ChessboardComponent extends JComponent implements Runnable{
    private ChessListener chessListener = new ChessboardChessListener(this);
    private ChessComponent[][] chessboard = new ChessComponent[10][9];
    private ChessColor currentColor = ChessColor.RED;
    private JLabel whoTurn;
    private ArrayList<String> stringList = new ArrayList<String>();
    private int move = 0;//动了多少步
    private int n;
    private boolean stopUndoing = false;


//-------------------------------------------------------------------------------------------------getter and setter

    public void setCurrentColor(ChessColor currentColor) {
        this.currentColor = currentColor;
    }

    public void setN(int n) {
        this.n = n;
    }

    public int getMove() {
        return move;
    }

    public int getN() {
        return n;
    }

    public ChessComponent[][] getChessboard() {
        return chessboard;
    }

    public ChessColor getCurrentColor() {
        return currentColor;
    }

    public JLabel getWhoTurn() {
        return whoTurn;
    }

    public ArrayList<String> getStringList() {
        return stringList;
    }

    public final String initial() {
        return "CHEAGAEHC..........N.....N.S.S.S.S.S..................s.s.s.s.s.n.....n..........cheagaehc";
    }

    public void setStopUndoing(boolean stopUndoing) {
        this.stopUndoing = stopUndoing;
    }

    public void setMove(int move) {
        this.move = move;
    }

    public void clearStringList() {
        stringList.clear();
    }

    //-----------------------------------------------------------------------------------------------------constructor

    public ChessboardComponent(int width, int height, JLabel l) throws UnknownHostException {
        setLayout(null); // Use absolute layout.
        setSize(width, height);
        whoTurn = l;

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

    public void setChessboardSize(int x, int y) {
        this.setSize(x, y);
        System.out.println(getWidth() + " " + getHeight());
    }

    //----------------------------------------------------------------------------------------------------------initialize

    public void putChessOnBoard(ChessComponent chessComponent) {
        int row = chessComponent.getChessboardPoint().getX(), col = chessComponent.getChessboardPoint().getY();
        if (chessboard[row][col] != null) {
            remove(chessboard[row][col]);
        }
        add(chessboard[row][col] = chessComponent);
    }

    //----------------------------------------------------------------------------------------------------------swap

    public void swapChessComponents(ChessComponent chess1, ChessComponent chess2) {
        GameSound Sound = new GameSound();
        ChessComponent chess3 = chess2;
        // Note that chess1 has higher priority, 'destroys' chess2 if exists.
        Point pointI = calculatePoint(chess1.getChessboardPoint().getX(), chess1.getChessboardPoint().getY());
        Point pointF = calculatePoint(chess2.getChessboardPoint().getX(), chess2.getChessboardPoint().getY());
        java.util.Timer timer = new Timer();

        boolean judge = chess1.getChessName().equals("h") || chess1.getChessName().equals("H") ||
                chess1.getChessName().equals("e") || chess1.getChessName().equals("E") ||
                chess1.getChessName().equals("a") || chess1.getChessName().equals("A");

        if(judge) {
            TimerTask timerTask1 = new TimerTask() {
                int xF = (int) pointF.getX();
                int yF = (int) pointF.getY();
                int xI = (int) pointI.getX();
                int yI = (int) pointI.getY();
                int dx = Math.abs(xF - xI) / 5;
                int remainx = Math.abs(Math.abs(xF - xI) - 5 * dx);
                int dy = Math.abs(yF - yI) / 5;
                int remainy = Math.abs(Math.abs(yF - yI) - 5 * dy);

                public void run() {
                    Sound.start();
                if (Math.abs(xF - xI) != remainx && Math.abs(yF - yI) != remainy) {
                        if (xF >= xI && yF >= yI) {
                            xI += dx;
                            yI += dy;
                            chess1.setLocation(xI, yI);
                            repaint();
                            System.out.println(xI + " " + yI);
                        } else if (xF < xI && yF > yI) {
                            xI -= dx;
                            yI += dy;
                            chess1.setLocation(xI, yI);
                            repaint();
                            System.out.println(xI + " " + yI);
                        } else if (xF > xI && yF < yI) {
                            yI -= dy;
                            xI += dx;
                            chess1.setLocation(xI, yI);
                            repaint();
                            System.out.println(xI + " " + yI);
                        } else if (xF < xI && yF < yI) {
                            yI -= dy;
                            xI -= dx;
                            chess1.setLocation(xI, yI);
                            repaint();
                            System.out.println(xI + " " + yI);
                        }
                    } else {
                        loadGame(stringList.get(move-1));
                        cancel();
                    }
                }
            };
            timer.scheduleAtFixedRate(timerTask1, 0, 30);
        }else {
            TimerTask timerTask2 = new TimerTask() {
                int xF = (int) pointF.getX();
                int yF = (int) pointF.getY();
                int xI = (int) pointI.getX();
                int yI = (int) pointI.getY();

                public void run() {
                    if (Math.abs(xF - xI) == 0 && yI > yF) {
                        yI--;
                        chess1.setLocation(xI, yI);
                        repaint();
                        System.out.println(xI + " " + yI);
                    } else if (Math.abs(xF - xI) == 0 && yI < yF) {
                        yI++;
                        chess1.setLocation(xI, yI);
                        repaint();
                        System.out.println(xI + " " + yI);
                    } else if (Math.abs(yF - yI) == 0 && xI < xF) {
                        xI++;
                        chess1.setLocation(xI, yI);
                        repaint();
                        System.out.println(xI + " " + yI);
                    } else if (Math.abs(yF - yI) == 0 && xI > xF) {
                        xI--;
                        chess1.setLocation(xI, yI);
                        repaint();
                        System.out.println(xI + " " + yI);
                    } else {
                        cancel();

                    }
                }
            };
            timer.scheduleAtFixedRate(timerTask2, 0, 1);
        }

        //-----------------------------------------------------------------------------------------------------------------------------------Action

        if (!(chess2 instanceof EmptySlotComponent)) {
            remove(chess2);
            add(chess2 = new EmptySlotComponent(chess2.getChessboardPoint(), chess2.getLocation()));
        }
        chess1.swapLocation(chess2);
        int row1 = chess1.getChessboardPoint().getX(), col1 = chess1.getChessboardPoint().getY();
        chessboard[row1][col1] = chess1;
        int row2 = chess2.getChessboardPoint().getX(), col2 = chess2.getChessboardPoint().getY();
        chessboard[row2][col2] = chess2;

        if (chess3.getChessName().equals("g")) {
            Object[] options = {"再来一局", "退出"};
            int result = JOptionPane.showOptionDialog(this, "BLACK WIN!", "WIN", JOptionPane.YES_OPTION,
                    JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
            if (result == 0) {
                loadGame(initial());
                currentColor = ChessColor.BLACK;
                stringList.clear();
                move = 0;
                n = 0;
            } else if (result == 1) {
                System.exit(0);
            }
        }
        if (chess3.getChessName().equals("G")) {
            Object[] options = {"再来一局", "退出"};
            int result = JOptionPane.showOptionDialog(this, "RED WIN!", "WIN", JOptionPane.YES_OPTION,
                    JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
            if (result == 0) {
                loadGame(initial());
                currentColor = ChessColor.BLACK;
                stringList.clear();
                move = 0;
                n = 0;
            } else if (result == 1) {
                System.exit(0);
            }
        }
        if (currentColor == ChessColor.RED) {
            whoTurn.setText("BLACK TURN");
            whoTurn.setForeground(Color.BLACK);
        } else {
            whoTurn.setText("RED TURN");
            whoTurn.setForeground(Color.RED);
        }
        if (stopUndoing) {
            for (int i = move - 1; i >= move - n; i--) {
                stringList.remove(i);
            }
            move = move - n;
            n = 0;
            stopUndoing = false;
        }
        judgeCheckmate();
        System.out.println(toString());
        System.out.println(move);
        System.out.println(n);
        System.out.println(stringList.size());
        try {
            send();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void swapColor() {
        currentColor = currentColor == ChessColor.RED ? ChessColor.BLACK : ChessColor.RED;
    }

    //--------------------------------------------------------------------------------------------------initialize

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

    //--------------------------------------------------------------------------------------------------paint issue

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

    //--------------------------------------------------------------------------------------------------changePoint

    public Point calculatePoint(int row, int col) {
        return new Point(col * ((getWidth() - (ChessComponent.CHESS_SIZE.width)) / 8), row * ((getHeight() - (ChessComponent.CHESS_SIZE.width)) / 9));
    }

    //--------------------------------------------------------------------------------------------------loadGame

    public void loadGame(String s) {
        char[][] arr1 = toCharArray(s);
        if (arr1.length != 11 || arr1[0].length != 9) {
            System.out.println("Invalid Dimension");
        } else {
            char[][] arr = new char[10][9];

            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 9; j++) {
                    arr[i][j] = arr1[i][j];
                }
            }

            for (int i = 6; i < 11; i++) {
                for (int j = 0; j < 9; j++) {
                    arr[i - 1][j] = arr1[i][j];
                }
            }
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 9; j++) {
                    putChessOnBoard(new EmptySlotComponent(new ChessboardPoint(i, j), calculatePoint(i, j)));
                }
            }

            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 9; j++) {
                    switch (arr[i][j]) {
                        case 'C':
                            initTestBoardC(i, j, ChessColor.BLACK);
                            break;
                        case 'c':
                            initTestBoardc(i, j, ChessColor.RED);
                            break;
                        case 'G':
                            initTestBoardBossBlack(i, j, ChessColor.BLACK);
                            break;
                        case 'g':
                            initTestBoardBossRed(i, j, ChessColor.RED);
                            break;
                        case 'A':
                            initTestBoardShiRBlack(i, j, ChessColor.BLACK);
                            break;
                        case 'a':
                            initTestBoardShiRed(i, j, ChessColor.RED);
                            break;
                        case 'E':
                            initTestBoardEleBlack(i, j, ChessColor.BLACK);
                            break;
                        case 'e':
                            initTestBoardEleRed(i, j, ChessColor.RED);
                            break;
                        case 'H':
                            initTestBoardHorseBlack(i, j, ChessColor.BLACK);
                            break;
                        case 'h':
                            initTestBoardHorseRed(i, j, ChessColor.RED);
                            break;
                        case 'N':
                            initTestBoardPaoBlack(i, j, ChessColor.BLACK);
                            break;
                        case 'n':
                            initTestBoardPaoRed(i, j, ChessColor.RED);
                            break;
                        case 'S':
                            initTestBoardSoldierBlack(i, j, ChessColor.BLACK);
                            break;
                        case 's':
                            initTestBoardSoldierRed(i, j, ChessColor.RED);
                            break;
                        case '.':
                            new EmptySlotComponent(new ChessboardPoint(i, j), calculatePoint(i, j));
                            break;
                    }
                }
            }
            repaint();
        }
    }

    //-----------------------------------------------------------------------------------------------to method

    @Override
    public String toString() {
        String s = "";
        for (int i = 0; i < this.chessboard.length; i++) {
            for (int j = 0; j < this.chessboard[0].length; j++) {
                s += this.chessboard[i][j].getChessName();
            }
        }
        stringList.add(s);
        move++;
        return s;
    }

    public char[][] toCharArray(String s) {
        char[][] result = new char[11][9];
        char[] origin = s.toCharArray();
        int k = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 9; j++) {
                result[i][j] = origin[k];
                k++;
            }
        }
        for (int i = 0; i < 9; i++) {
            result[5][i] = '-';
        }
        for (int i = 6; i < 11; i++) {
            for (int j = 0; j < 9; j++) {
                result[i][j] = origin[k];
                k++;
            }
        }
        return result;
    }


    //-----------------------------------------------------------------------------------------------------judgeCases

    public int judgeCase1(String s) {
        char[] c = s.toCharArray();
        boolean ifSpaceMissing = true;
        int[] compare = {2, 2, 2, 2, 1, 2, 5};
        int[] red = new int[7];
        int[] black = new int[7];
        boolean ifLessThan = true;

        for (int i = 0; i < c.length; i++) {
            if (c[i] == '.') {
                ifSpaceMissing = false;
            } else {
                switch (c[i]) {
                    case 'C':
                        black[0]++;
                        break;
                    case 'H':
                        black[1]++;
                        break;
                    case 'E':
                        black[2]++;
                        break;
                    case 'A':
                        black[3]++;
                        break;
                    case 'G':
                        black[4]++;
                        break;
                    case 'N':
                        black[5]++;
                        break;
                    case 'S':
                        black[6]++;
                        break;
                    case 'c':
                        red[0]++;
                        break;
                    case 'h':
                        red[1]++;
                        break;
                    case 'e':
                        red[2]++;
                        break;
                    case 'a':
                        red[3]++;
                        break;
                    case 'g':
                        red[4]++;
                        break;
                    case 'n':
                        red[5]++;
                        break;
                    case 's':
                        red[6]++;
                        break;
                }
            }
        }

        for (int i = 0; i < 7; i++) {
            if(red[i] > compare[i] || black[i] > compare[i]){
                ifLessThan = false;
            }
        }

        if (ifSpaceMissing) {
            return 1;
        } else if (!ifLessThan) {
            return 2;
        } else if (s.length() != 90) {
            return 3;
        }
        return 0;
    }

    public int judgeCase2(char[] c) {
        int[] n = new int[c.length];
        for (int i = 0; i < c.length; i++) {
            if (Character.isDigit(c[i])) {
                n[i] = Integer.parseInt(String.valueOf(c[i]));
            } else {
                n[i] = 0;
            }
        }

        if (!(n[2] > 0 && n[2] < 11 && n[0] > 0 && n[0] < 10 &&
                n[4] > 0 && n[4] < 10 && n[6] > 0 && n[6] < 11)) {
            return 1;
        }

        if (currentColor == ChessColor.RED) {
            if (chessboard[10 - n[2]][9 - n[0]] instanceof EmptySlotComponent) {
                return 2;
            } else if (chessboard[10 - n[6]][9 - n[4]].getChessColor() == ChessColor.RED) {
                return 3;
            } else if (!(chessboard[10 - n[2]][9 - n[0]].canMoveTo(chessboard, new ChessboardPoint(10 - n[6], 9 - n[4])))) {
                return 4;
            }
        } else if (currentColor == ChessColor.BLACK) {
            if (chessboard[n[2] - 1][n[0] - 1] instanceof EmptySlotComponent) {
                return 2;
            } else if (chessboard[n[6] - 1][n[4] - 1].getChessColor() == ChessColor.BLACK) {
                return 3;
            } else if (!(chessboard[n[2] - 1][n[0] - 1].canMoveTo(chessboard, new ChessboardPoint(n[6] - 1, n[4] - 1)))) {
                return 4;
            }
        }


        return 0;

    }

    public void judgeCheckmate() {

        ChessComponent redg = null;
        ChessComponent blackg = null;

        for (int i = 0; i < chessboard.length; i++) {
            for (int j = 0; j < chessboard[0].length; j++) {
                if (chessboard[i][j].getChessName().equals("g")) {
                    redg = chessboard[i][j];
                } else if (chessboard[i][j].getChessName().equals("G")) {
                    blackg = chessboard[i][j];
                }
            }
        }

        for (int i = 0; i < chessboard.length; i++) {
            for (int j = 0; j < chessboard[0].length; j++) {
                if (chessboard[i][j].canMoveTo(chessboard, redg.getChessboardPoint()) && chessboard[i][j].getChessColor() == ChessColor.BLACK
                        || chessboard[i][j].canMoveTo(chessboard, blackg.getChessboardPoint()) && chessboard[i][j].getChessColor() == ChessColor.RED) {
                    JOptionPane.showMessageDialog(this, "Checkmate!", "warning",
                            JOptionPane.PLAIN_MESSAGE);
                    break;
                }
            }
        }

    }

    //-------------------------------------------------------------------------------------------------paint

    public void paint(Graphics g,int x,int y){
        super.paint(g);
        Shape s = new Ellipse2D.Double(x,y,20,20);
        g.setColor(Color.green);
        g.fillOval(0,0,20,20);
        repaint();
    }

    private DatagramSocket sendDs;
    private DatagramSocket receiveDs;
    private DatagramPacket sendData;
    private DatagramPacket receiveData;
    private int port = 10086;


    InetAddress ia = InetAddress.getByName("10.17.117.22");



    public void send() throws IOException {
        sendDs = new DatagramSocket(1000);
        byte[] data = (stringList.get(move-1)).getBytes();
        sendData=new DatagramPacket(data,data.length,ia,port);
        sendDs.send(sendData);
        sendDs.close();
    }

    public void receive() throws IOException {
        receiveDs = new DatagramSocket(port);
        byte[] data = new byte[1024];
        receiveData = new DatagramPacket(data,data.length);
        while(true) {
            receiveDs.receive(receiveData);
            String word = new String(receiveData.getData()).trim();
            stringList.add(word);
            move++;
            if(currentColor == ChessColor.RED){
                currentColor = ChessColor.BLACK;
                whoTurn.setText("BLACK TURN");
                whoTurn.setForeground(Color.BLACK);
            }else{
                currentColor = ChessColor.RED;
                whoTurn.setText("RED TURN");
                whoTurn.setForeground(Color.RED);
            }
            loadGame(stringList.get(move-1));
        }
    }


    @Override
    public void run() {
        try {
            receive();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
