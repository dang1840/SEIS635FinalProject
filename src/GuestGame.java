import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;


public class GuestGame implements ActionListener, Runnable{
    private Board board;
    private EndOptionPanel endOptionPanel;
    private Player player;
    private ComputerAI ai;
    private Judger judger;
    private int rowSelected;
    private int columnSelected;
    private boolean myTurn = false;
    private boolean waiting = true;

    public GuestGame(String name){
        board = new Board();
        endOptionPanel = new EndOptionPanel();
        player = new Player();
        ai = new ComputerAI();
        judger = new Judger();



        player.setName(name);
        board.setMyName(name);
        board.setOpponentName(ai.getName());
        for (int i=0;i<3;i++){   //add listener to cells
            for (int j=0;j<3;j++){
                board.getCell()[i][j].addActionListener(this);
            }
        }


        endOptionPanel.rematchBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                waiting = false;
                endOptionPanel.setVisible(false);
            }
        });

        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        int time = 0;
        while (true){
            try {
                if (time != 0) {				//rematch
                    waitForPlayerAction();
                    gameReset();
                }
                System.out.println("start to connect");
                playerChoose();
                System.out.println(player.getRole());

                board.setMyMarker(player.getRole()+"");
                board.setOpponentMarker(ai.getRole()+"");

                while (true){
                    if (player.getRole() == 'x'){     //game flow
                        waitForPlayerAction();
                        if (judger.getWinner()!=' ')
                            break;
                        aiTurn();
                        judger.judge(board.getStatus());
                        if (judger.getWinner()!=' ')
                            break;
                    }else if (player.getRole() == 'o'){
                        aiTurn();
                        judger.judge(board.getStatus());
                        if (judger.getWinner()!=' ')
                            break;
                        waitForPlayerAction();
                        if (judger.getWinner()!=' ')
                            break;
                    }
                }
                if (judger.getWinner() == player.getRole()){
                    endOptionPanel.setResult("You win!");

                }
                else if (judger.getWinner() == 't'){
                    endOptionPanel.setResult("It's a tie!");

                }else {
                    endOptionPanel.setResult("You lose!");

                }
                waiting = true;
                myTurn = false;
                time++;
                endOptionPanel.setVisible(true);

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Connection Lost!");
                System.exit(2);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        if (myTurn == true){
            Cell cellClicked = (Cell) e.getSource();
            player.putMarker(cellClicked);
            rowSelected = cellClicked.getRow();
            columnSelected = cellClicked.getColumn();
            board.getStatus()[rowSelected][columnSelected] = cellClicked.getToken();
            judger.judge(board.getStatus());
            System.out.println("cell"+ "("+cellClicked.getRow()+","+cellClicked.getColumn()+")"+ "clicked");
            waiting = false;
            myTurn = false;
        }
    }

    private void waitForPlayerAction() throws InterruptedException {
        while (waiting) {
            Thread.sleep(100);
        }
        waiting = true;
    }

    public void gameReset(){
        board.setMyMarker("Waiting");
        board.setOpponentMarker("Waiting");
        judger.setWinner(' ');

        for (int i=0;i<3;i++){
            for (int j=0;j<3;j++){
                board.getCell()[i][j].setToken(' ');
                board.getCell()[i][j].setIcon(null);
                board.getCell()[i][j].setEnabled(true);
                board.getStatus()[i][j] = ' ';
            }
        }
    }

    private void playerChoose(){
        double e = Math.random();

        if (e<0.5){
            player.setRole('x');
            myTurn = true;
            ai.setRole('o');
        }else {
            player.setRole('o');
            myTurn = false;
            ai.setRole('x');
        }
        System.out.println(e);
    }
    public void aiTurn(){
        ai.putMarker(board.getCell(), board.getStatus());
        myTurn = true;
        waiting=true;
    }

    public static void main(String arg[]){
        new GuestGame("test");
    }
}

