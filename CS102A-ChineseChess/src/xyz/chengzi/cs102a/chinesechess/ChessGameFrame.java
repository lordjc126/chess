package xyz.chengzi.cs102a.chinesechess;

import xyz.chengzi.cs102a.chinesechess.chess.ChessColor;
import xyz.chengzi.cs102a.chinesechess.chessboard.ChessboardComponent;
import xyz.chengzi.cs102a.chinesechess.listener.GameMusic1;
import xyz.chengzi.cs102a.chinesechess.listener.GameMusic2;
import xyz.chengzi.cs102a.chinesechess.listener.GameMusic3;
import xyz.chengzi.cs102a.chinesechess.listener.MainFrame;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class ChessGameFrame extends JFrame {
    private JLabel statusLabel;
    private int x = 540;
    private int y = 600;
    private GameMusic1 bgm1 = new GameMusic1();
    private GameMusic2 bgm2 = new GameMusic2();
    private GameMusic3 bgm3 = new GameMusic3();
    private DatagramSocket sendDs;
    private DatagramPacket sendData;
    private int port = 10086;
    private boolean If = false;

    public boolean isIf() {
        return If;
    }

    public void setIf(boolean anIf) {
        If = anIf;
        System.out.println(If);
    }

    @Override
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    @Override
    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public JLabel getStatusLabel() {
        return statusLabel;
    }

    public ChessGameFrame(boolean b) throws IOException {
        setTitle("Chinese Chess");
        setSize(x, y);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null);
        setBak();
        If = b;


        statusLabel = new JLabel("RED TURN");
        Font f = new Font("华文行楷", Font.BOLD, 20);//设置字体大小
        statusLabel.setFont(f);
        statusLabel.setForeground(Color.RED);
        statusLabel.setSize(200, 30);
        ChessboardComponent chessboard = new ChessboardComponent(500, 560, statusLabel);
        if(If){
            chessboard.setWhetherNet(true);
        }
        new Thread(chessboard).start();


        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                int windowWidth = getWidth();
                int windowHeight = getHeight();
                statusLabel.setLocation(0, windowHeight - 150);
                chessboard.setChessboardSize(windowWidth - 50, windowHeight - 150);
                //chessboard.setLocation(windowWidth / 2 - (chessboard.getWidth() / 2), 0);
                if (chessboard.getStringList().size() != 0) {
                    chessboard.loadGame(chessboard.getStringList().get(chessboard.getMove() - 1));
                } else {
                    chessboard.loadGame(chessboard.initial());
                }
                chessboard.repaint();
            }
        });

        add(chessboard);
        add(statusLabel);

        JButton button = new JButton("点开有惊喜哦");
        button.addActionListener((e) -> JOptionPane.showMessageDialog(this, "Fuck You!"));
        button.setLocation(170, 400);
        button.setSize(200, 20);
        add(button);
        button.setVisible(false);

        JMenuBar bar = new JMenuBar();
        JMenu Edit = new JMenu("Edit");
        JMenuItem item = new JMenuItem("Undo");
        JMenuItem item2 = new JMenuItem("UndoUndo");
        JMenu file = new JMenu("file");
        JMenuItem initialGame = new JMenuItem("initialize");
        JMenuItem loadGame = new JMenuItem("load");
        JMenuItem save = new JMenuItem("save");
        JMenu Music = new JMenu("Music");
        JMenuItem BGM1 = new JMenuItem("BGM1");
        JMenuItem BGM2 = new JMenuItem("BGM2");
        JMenuItem BGM3 = new JMenuItem("BGM3");
        JMenuItem NoMusic = new JMenuItem("NoMusic");
        bar.add(Edit);
        bar.add(file);
        Edit.add(item);
        Edit.add(item2);
        file.add(initialGame);
        file.add(loadGame);
        file.add(save);
        setJMenuBar(bar);
        Music.add(BGM1);
        Music.add(BGM2);
        Music.add(BGM3);
        Music.add(NoMusic);
        bar.add(Music);


        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (chessboard.getMove() - chessboard.getN() >= 2) {
                    System.out.println(chessboard.getStringList().get(chessboard.getMove() - 2 - chessboard.getN()));
                    chessboard.loadGame(chessboard.getStringList().get(chessboard.getMove() - 2 - chessboard.getN()));
                    chessboard.setN(chessboard.getN() + 1);
                    if (chessboard.getCurrentColor() == ChessColor.RED) {
                        chessboard.setCurrentColor(ChessColor.BLACK);
                        chessboard.getWhoTurn().setText("BLACK TURN");
                        chessboard.getWhoTurn().setForeground(Color.BLACK);
                    } else if (chessboard.getCurrentColor() == ChessColor.BLACK) {
                        chessboard.setCurrentColor(ChessColor.RED);
                        chessboard.getWhoTurn().setText("RED TURN");
                        chessboard.getWhoTurn().setForeground(Color.RED);
                    }
                    chessboard.setStopUndoing(true);
                } else if (chessboard.getMove() - chessboard.getN() == 1) {
                    chessboard.loadGame(chessboard.initial());
                    chessboard.setCurrentColor(ChessColor.RED);
                    chessboard.getWhoTurn().setText("RED TURN");
                    chessboard.getWhoTurn().setForeground(Color.RED);
                    chessboard.setN(chessboard.getN() + 1);
                    chessboard.setStopUndoing(true);
                }

                if(If) {
                    try {
                        send();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }

            InetAddress ia = InetAddress.getByName("10.17.117.22");

            public void send() throws IOException {
                sendDs = new DatagramSocket(1000);

                byte[] data = (0+chessboard.getStringList().get(chessboard.getMove() - 2 - chessboard.getN())).getBytes();

                sendData=new DatagramPacket(data,data.length,ia,port);
                sendDs.send(sendData);
                sendDs.close();
            }

        });


        item2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (chessboard.getN() > 0) {
                    chessboard.loadGame(chessboard.getStringList().get(chessboard.getMove() - chessboard.getN()));
                    chessboard.setN(chessboard.getN() - 1);
                    if (chessboard.getCurrentColor() == ChessColor.RED) {
                        chessboard.setCurrentColor(ChessColor.BLACK);
                        chessboard.getWhoTurn().setText("BLACK TURN");
                        chessboard.getWhoTurn().setForeground(Color.BLACK);
                    } else if (chessboard.getCurrentColor() == ChessColor.BLACK) {
                        chessboard.setCurrentColor(ChessColor.RED);
                        chessboard.getWhoTurn().setText("RED TURN");
                        chessboard.getWhoTurn().setForeground(Color.RED);
                    }
                }

                if(If) {
                    try {
                        send();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }

            InetAddress ia = InetAddress.getByName("10.17.117.22");

            public void send() throws IOException {
                sendDs = new DatagramSocket(1000);

                byte[] data = (1+chessboard.getStringList().get(chessboard.getMove() - chessboard.getN()-1)).getBytes();

                sendData=new DatagramPacket(data,data.length,ia,port);
                sendDs.send(sendData);
                sendDs.close();
            }

        });


        loadGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JFileChooser jfc = new JFileChooser("E:\\");
                FileFilter filter = new FileNameExtensionFilter("文本文件（txt)", "txt");
                jfc.setFileFilter(filter);
                int i = jfc.showOpenDialog(getContentPane());
                if (i == JFileChooser.APPROVE_OPTION) {
                    File file = jfc.getSelectedFile();  //获取选中的文件
                    try {
                        FileReader fileReader = new FileReader(file);
                        BufferedReader BufferedReader = new BufferedReader(fileReader);
                        ArrayList<String> input = new ArrayList<>();
                        String str;
                        String board = "";
                        boolean ifMissingRiver = true;

                        while ((str = BufferedReader.readLine()) != null) {
                            input.add(str);
                        }
                        if (input.get(0).equals("@LAST_MOVER=BLACK")) {
                            for (int j = 3; j < input.size(); j++) {
                                if (!(input.get(j).equals("---------"))) {
                                    board += input.get(j);
                                } else {
                                    ifMissingRiver = false;
                                }
                            }

                            if (ifMissingRiver) {
                                JOptionPane.showMessageDialog(chessboard, "River Missing", "error",
                                        JOptionPane.PLAIN_MESSAGE);
                            } else if (chessboard.judgeCase1(board) == 1) {
                                JOptionPane.showMessageDialog(chessboard, "Space Missing", "error",
                                        JOptionPane.PLAIN_MESSAGE);
                            } else if (chessboard.judgeCase1(board) == 2) {
                                JOptionPane.showMessageDialog(chessboard, "Invalid Chess Amount", "error",
                                        JOptionPane.PLAIN_MESSAGE);
                            } else if (chessboard.judgeCase1(board) == 3) {
                                JOptionPane.showMessageDialog(chessboard, "Invalid Dimension", "error",
                                        JOptionPane.PLAIN_MESSAGE);
                            } else {
                                System.out.println(board);
                                chessboard.loadGame(board);
                                chessboard.setCurrentColor(ChessColor.RED);
                                chessboard.setN(0);
                                chessboard.setMove(0);
                                chessboard.clearStringList();
                                chessboard.getWhoTurn().setText("RED TURN");
                                chessboard.getWhoTurn().setForeground(Color.RED);
                            }


                        } else if (input.get(0).equals("@LAST_MOVER=RED")) {
                            for (int j = 3; j < input.size(); j++) {
                                if (!(input.get(j).equals("---------"))) {
                                    board += input.get(j);
                                } else {
                                    ifMissingRiver = false;
                                }
                            }

                            if (ifMissingRiver) {
                                JOptionPane.showMessageDialog(chessboard, "River Missing", "error",
                                        JOptionPane.PLAIN_MESSAGE);
                            } else if (chessboard.judgeCase1(board) == 1) {
                                JOptionPane.showMessageDialog(chessboard, "Space Missing", "error",
                                        JOptionPane.PLAIN_MESSAGE);
                            } else if (chessboard.judgeCase1(board) == 2) {
                                JOptionPane.showMessageDialog(chessboard, "Invalid Chess Amount", "error",
                                        JOptionPane.PLAIN_MESSAGE);
                            } else if (chessboard.judgeCase1(board) == 3) {
                                JOptionPane.showMessageDialog(chessboard, "Invalid Dimension", "error",
                                        JOptionPane.PLAIN_MESSAGE);
                            } else {
                                System.out.println(board);
                                chessboard.loadGame(board);
                                chessboard.setCurrentColor(ChessColor.BLACK);
                                chessboard.setN(0);
                                chessboard.setMove(0);
                                chessboard.clearStringList();
                                chessboard.getWhoTurn().setText("BLACK TURN");
                                chessboard.getWhoTurn().setForeground(Color.BLACK);
                            }

                        } else if (input.get(0).startsWith("@TOTAL_STEP=")) {
                            char[] array = input.get(0).toCharArray();
                            String n1 = "";

                            for (int j = 0; j < array.length; j++) {
                                if(Character.isDigit(array[j])){
                                    n1 += array[j];
                                }
                            }

                            int n = Integer.parseInt(n1);
                            char[] line;

                            for (int j = 0; j < n; j++) {
                                line = input.get(3 + j).toCharArray();

                                if (chessboard.getCurrentColor() == ChessColor.RED) {
                                    if (chessboard.judgeCase2(line) == 0) {
                                        chessboard.swapChessComponents(chessboard.getChessboard()
                                                        [10 - Integer.parseInt(String.valueOf(line[2]))][9 - Integer.parseInt(String.valueOf(line[0]))],
                                                chessboard.getChessboard()
                                                        [10 - Integer.parseInt(String.valueOf(line[6]))][9 - Integer.parseInt(String.valueOf(line[4]))]);
                                        chessboard.swapColor();
                                    } else if (chessboard.judgeCase2(line) == 1) {
                                        JOptionPane.showMessageDialog(chessboard, "Position Out Of Range", "error",
                                                JOptionPane.PLAIN_MESSAGE);
                                    } else if (chessboard.judgeCase2(line) == 2) {
                                        JOptionPane.showMessageDialog(chessboard, "Invalid From Position", "error",
                                                JOptionPane.PLAIN_MESSAGE);
                                    } else if (chessboard.judgeCase2(line) == 3) {
                                        JOptionPane.showMessageDialog(chessboard, "Invalid To Position", "error",
                                                JOptionPane.PLAIN_MESSAGE);
                                    } else if (chessboard.judgeCase2(line) == 4) {
                                        JOptionPane.showMessageDialog(chessboard, "Invalid Move Pattern", "error",
                                                JOptionPane.PLAIN_MESSAGE);
                                    }

                                } else if (chessboard.getCurrentColor() == ChessColor.BLACK) {
                                    if (chessboard.judgeCase2(line) == 0) {
                                        chessboard.swapChessComponents(chessboard.getChessboard()
                                                        [Integer.parseInt(String.valueOf(line[2])) - 1][Integer.parseInt(String.valueOf(line[0])) - 1],
                                                chessboard.getChessboard()
                                                        [Integer.parseInt(String.valueOf(line[6])) - 1][Integer.parseInt(String.valueOf(line[4])) - 1]);
                                        chessboard.swapColor();
                                    } else if (chessboard.judgeCase2(line) == 1) {
                                        JOptionPane.showMessageDialog(chessboard, "Position Out Of Range", "error",
                                                JOptionPane.PLAIN_MESSAGE);
                                    } else if (chessboard.judgeCase2(line) == 2) {
                                        JOptionPane.showMessageDialog(chessboard, "Invalid From Position", "error",
                                                JOptionPane.PLAIN_MESSAGE);
                                    } else if (chessboard.judgeCase2(line) == 3) {
                                        JOptionPane.showMessageDialog(chessboard, "Invalid To Position", "error",
                                                JOptionPane.PLAIN_MESSAGE);
                                    } else if (chessboard.judgeCase2(line) == 4) {
                                        JOptionPane.showMessageDialog(chessboard, "Invalid Move Pattern", "error",
                                                JOptionPane.PLAIN_MESSAGE);
                                    }
                                }

                            }


                        }

                        BufferedReader.close();
                        fileReader.close();

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });


        BGM1.addActionListener(new ActionListener() {
                                   @Override
                                   public void actionPerformed(ActionEvent e) {
                                       if (e.getSource().equals(BGM1)) {
                                           try {
                                               bgm1.playBgm1();
                                               bgm2.stop();
                                               bgm3.stop();
                                           } catch (IOException ex) {
                                               ex.printStackTrace();
                                           }
                                       }
                                   }
                               }
        );


        BGM2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource().equals(BGM2)) {
                    try {
                        bgm2.playBgm2();
                        bgm1.stop();
                        bgm3.stop();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        BGM3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource().equals(BGM3)) {
                    try {
                        bgm3.playBgm3();
                        bgm1.stop();
                        bgm2.stop();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        NoMusic.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource().equals(NoMusic)) {
                    bgm1.stop();
                    bgm2.stop();
                    bgm3.stop();
                }
            }
        });





        initialGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                chessboard.loadGame(chessboard.initial());
                chessboard.setCurrentColor(ChessColor.RED);
                chessboard.setN(0);
                chessboard.setMove(0);
                chessboard.clearStringList();
                chessboard.getWhoTurn().setText("RED TURN");
                chessboard.getWhoTurn().setForeground(Color.RED);
            }
        });

        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    FileWriter fileWriter = new FileWriter("E:\\南方科技大学\\saveChineseChess.txt");
                    BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                    char[][] saveArray = chessboard.toCharArray(chessboard.getStringList().get(chessboard.getMove() - 1));

                    if (chessboard.getCurrentColor() == ChessColor.RED) {
                        bufferedWriter.write("@LAST_MOVER=BLACK");
                    } else if (chessboard.getCurrentColor() == ChessColor.BLACK) {
                        bufferedWriter.write("@LAST_MOVER=RED");
                    }
                    bufferedWriter.write("\n");
                    bufferedWriter.write("@@");
                    bufferedWriter.write("\n");
                    bufferedWriter.write("\n");

                    for (int i = 0; i < saveArray.length; i++) {
                        for (int j = 0; j < saveArray[0].length; j++) {
                            bufferedWriter.write(saveArray[i][j]);
                        }
                        bufferedWriter.write("\n");
                    }
                    bufferedWriter.close();
                    fileWriter.close();


                } catch (IOException ex) {
                    ex.printStackTrace();
                }

            }
        });

    }


    private void setBak() {
        ((JPanel) this.getContentPane()).setOpaque(false);
        ImageIcon img = new ImageIcon("./untitled1.png");
        JLabel background = new JLabel(img);
        this.getLayeredPane().add(background, new Integer(Integer.MIN_VALUE));
        background.setBounds(0, 0, img.getIconWidth(), img.getIconHeight());
    }
    /*public static void main(String[] args) throws IOException {
        MainFrame mainFrame = new MainFrame();
        SwingUtilities.invokeLater(() ->
        {
            ChessGameFrame chessFrame = null;
            try {
                chessFrame = new ChessGameFrame();
            } catch (IOException e) {
                e.printStackTrace();
            }
            chessFrame.setVisible(true);
        });
    }*/
}
