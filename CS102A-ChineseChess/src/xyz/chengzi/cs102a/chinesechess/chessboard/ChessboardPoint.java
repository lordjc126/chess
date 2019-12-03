package xyz.chengzi.cs102a.chinesechess.chessboard;

public class ChessboardPoint {
    private int x, y;
    private String id;

    public ChessboardPoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
