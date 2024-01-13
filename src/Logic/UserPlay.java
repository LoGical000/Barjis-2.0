package Logic;

import Controller.Actions;
import Controller.MoveCheckController;
import Models.Game;

import java.util.ArrayList;

public class UserPlay {
    static ArrayList<Game> games = new ArrayList<>();

    public UserPlay(){
        games.add(new Game());
        this.play();
    }

    public void play(){
        int turn = 0;
        int limit=0;

        while (!Actions.isFinal(games.get(games.size()-1))){

            Actions.printBoard(games.get(games.size()-1));

            //First PLayer
            if(turn % 2 == 0){
                System.out.println("\n First player turn");
                int steps = Actions.throwShell();
                int playerNumber = 1;

                Game g1 = new Game(games.get(games.size()-1));

                //Khal logic
                if(steps == 10 || steps == 25){
                    Actions.MoveKhal(g1,playerNumber);
                    Actions.MoveRegular(g1,playerNumber,steps);
                }

                else if(MoveCheckController.checkNoSoldiers(g1,playerNumber)){
                    System.out.println("\n There is no soldiers on board...\n");
                }

                else{
                    Actions.MoveRegular(g1,playerNumber,steps);
                }

                if (steps==2 || steps==3 || steps==4 || limit==10) {
                    turn++;
                    limit=0;
                }
                limit++;


                games.add(g1);

            }

            //Second Player
            else {
                System.out.println("\n Second player turn");
                int steps = Actions.throwShell();
                int playerNumber = 2;

                Game g2 = new Game(games.get(games.size()-1));

                //Khal logic
                if(steps == 10 || steps == 25){
                    Actions.MoveKhal(g2,playerNumber);
                    Actions.MoveRegular(g2,playerNumber,steps);
                }

                else if(MoveCheckController.checkNoSoldiers(g2,playerNumber)){
                    System.out.println("\n There is no soldiers on board...\n");
                }

                else{
                    Actions.MoveRegular(g2,playerNumber,steps);
                }

                if (steps==2 || steps==3 || steps==4 || limit==10) {
                    turn++;
                    limit=0;
                }
                limit++;

                games.add(g2);

            }

            games.get(games.size()-1).refreshBoard();

//            System.out.println("first");
//            for(int i:games.get(games.size()-1).getFirstStonesPositions()){
//
//                System.out.println(i);
//            }
//
//            System.out.println("second");
//            for(int i:games.get(games.size()-1).getSecondStonesPositions()){
//
//                System.out.println(i);
//            }







        }



    }
}
