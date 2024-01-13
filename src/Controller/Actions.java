package Controller;

import Models.Board;
import Models.Game;
import Models.Shell;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Actions {

    public static double evaluate(Game game,int playerNumber) {
        double score = 0;

        int opponent = getOtherPlayerNumber(playerNumber);

        int [] positions1;
        if(playerNumber == 1)
            positions1  = game.getFirstStonesPositions();
        else positions1 = game.getSecondStonesPositions();

        int [] positions2;
        if(opponent == 1)
            positions2  = game.getFirstStonesPositions();
        else positions2 = game.getSecondStonesPositions();

        // Count player and opponent stones on the board and in the kitchen.
        int playerStonesOnBoard = countPlayerStonesOnBoard(game, playerNumber);
        int opponentStonesOnBoard = countPlayerStonesOnBoard(game,opponent);
        int playerStonesInKitchen = countPlayerStonesInKitchen(game, playerNumber);
        int opponentStonesInKitchen = countPlayerStonesInKitchen(game,opponent);

        // Score based on stones in the kitchen.
        score += 10 * playerStonesInKitchen;
        score -= 10 * opponentStonesInKitchen;

        // Score based on stones on the board (closer to kitchen is better).
        for (int i = 0; i < 4; i++) {
            score += 2 * (positions1[i]);
            score -= 2 * (positions2[i]);
        }

        int[] X = {11, 22, 28, 39, 45, 56, 62, 73};
        // Bonus for stones on X positions (protected).
        for (int pos : positions1){
            for (int num:X){
                if (pos==num)
                    score+=5;
            }
        }

        // Adjust score based on game stage (optional).
        // Here's an example for the endgame:
        if (playerStonesOnBoard + playerStonesInKitchen < 7) {
            score += 3 * (playerStonesOnBoard + playerStonesInKitchen);
        }

        return score;
    }

    public static int countPlayerStonesOnBoard(Game game,int playerNumber){
        int counter=0;
        int [] positions;
        if(playerNumber == 1)
            positions  = game.getFirstStonesPositions();
        else positions = game.getSecondStonesPositions();
        for(int pos : positions){
            if(pos>0 && pos<84)
                counter++;
        }
        return counter;
    }
    public static int countPlayerStonesInKitchen(Game game,int playerNumber){
        int counter=0;
        int [] positions;
        if(playerNumber == 1)
            positions  = game.getFirstStonesPositions();
        else positions = game.getSecondStonesPositions();
        for(int pos : positions){
            if(pos==84)
                counter++;
        }
        return counter;
    }


    public static List<Game> allNextMoves(Game game,int steps,int playerNumber) {
        List<Game> nextMoves = new LinkedList<>();
        for (int i = 0; i < 4; i++) {
                Game gameCopy = MoveController.Move(game, steps, i + 1, playerNumber);
                //System.out.println(Arrays.toString(playerCopy.getSoldiersPositions()));
                nextMoves.add(gameCopy);

        }
        return nextMoves;
    }

    public static void MoveKhal(Game game , int playerNumber){

        while (Actions.checkNextMoves(game,playerNumber,1)){
            System.out.println("\n Choose Soldier number to move with the KHAL:\n");
            int number = new Scanner(System.in).nextInt();

            while (number>4 || number<0){
                System.out.println("number should be between 1 and 4");
                number = new Scanner(System.in).nextInt();
            }

            int[] temp = new int[4];
            int [] positions;
            if(playerNumber == 1)
                positions  = game.getFirstStonesPositions();
            else positions = game.getSecondStonesPositions();


            for(int i=0;i<temp.length;i++)
                temp[i]=positions[i];

            game = MoveController.Move(game,1,number,playerNumber);



            if(playerNumber == 1)
                positions  = game.getFirstStonesPositions();
            else positions = game.getSecondStonesPositions();

//            System.out.println("first khal");
//            for(int i:game.getFirstStonesPositions()){
//
//                System.out.println(i);
//            }
//
//            System.out.println("second khal");
//            for(int i:game.getSecondStonesPositions()){
//
//                System.out.println(i);
//            }
//
//            System.out.println("temp khal");
//            for(int i:temp){
//
//                System.out.println(i);
//            }


            if (!Arrays.equals(positions,temp)){
                break;
            }

        }
    }

    public static void MoveRegular(Game game , int playerNumber,int steps){
        int [] positions;
        if(playerNumber == 1)
            positions  = game.getFirstStonesPositions();
        else positions = game.getSecondStonesPositions();


        while (Actions.checkNextMoves(game,playerNumber,steps)){
            System.out.println("\n Choose Soldier number:\n");
            int number = new Scanner(System.in).nextInt();

            while (number>4 || number<0 ||!MoveCheckController.checkKhal(positions[number-1],steps) ){
                System.out.println("number should be between 1 and 4 (and should be already on board)");
                number = new Scanner(System.in).nextInt();
            }



            int[] temp = new int[4];


            for(int i=0;i<temp.length;i++)
                temp[i]=positions[i];

            game = MoveController.Move(game,steps,number,playerNumber);

            if(playerNumber == 1)
                positions  = game.getFirstStonesPositions();
            else positions = game.getSecondStonesPositions();

//            System.out.println("first regular");
//            for(int i:game.getFirstStonesPositions()){
//
//                System.out.println(i);
//            }
//
//            System.out.println("second regular");
//            for(int i:game.getSecondStonesPositions()){
//
//                System.out.println(i);
//            }

            if (!Arrays.equals(positions,temp))
                break;
        }
    }

    public static boolean checkNextMoves(Game game,int playerNumber,int steps){
        int [] positions;
        if(playerNumber == 1)
            positions  = game.getFirstStonesPositions();
        else positions = game.getSecondStonesPositions();

        return MoveCheckController.checkMove(playerNumber, steps, positions[0],game)
                || MoveCheckController.checkMove(playerNumber, steps, positions[1],game)
                || MoveCheckController.checkMove(playerNumber, steps, positions[2],game)
                || MoveCheckController.checkMove(playerNumber, steps, positions[3],game);
    }

    public static boolean isFinal(Game game){
            int[] firstStonesPositions = game.getFirstStonesPositions();
            int[] secondStonesPositions = game.getSecondStonesPositions();

          boolean allEqualFirst = Arrays.equals(firstStonesPositions, new int[]{84, 84, 84, 84});
          boolean allEqualSecond = Arrays.equals(secondStonesPositions, new int[]{84, 84, 84, 84});


        return allEqualFirst || allEqualSecond;

    }

    public static void printBoard(Game game) {
        Board board = new Board();

        board.SetBoard(game);

        for(String[] x : board.getBoard())
        {
            for(String y : x)
            {
                System.out.print(y + " ");
            }
            System.out.println();
        }

    }

    public static int throwShell(){
        System.out.println("\n THROWING SHELLS...");
        //System.out.println("enter how many steps ");
        //int steps = new Scanner(System.in).nextInt();
        return Shell.throwing();
        //return steps;

    }

    public static int getOtherPlayerPosition(int position) {
        if (position < 42) return position + 34;
        else return position - 34;
    }

    public static int getOtherPlayerNumber(int playerNumber){
        if (playerNumber==1)
            return 2;
       return 1;
}





    }
