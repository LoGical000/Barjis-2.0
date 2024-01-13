package Controller;

import Models.Game;

import java.util.Arrays;
import java.util.List;

public class MoveCheckController {

    public static boolean checkMove(int playerNumber,int steps,int soldierPosition , Game game ){
        return boundaryCheck(soldierPosition + steps)
                &&   XMarkCheck(soldierPosition + steps,playerNumber,game)
                &&   checkKhal(soldierPosition,steps);
    }

    public static boolean checkMoveWithDetails(int playerNumber,int steps,int soldierPosition , Game game){

        if (!boundaryCheck(soldierPosition + steps)){
            System.out.println("Invalid move. cannot go further");
            return false;
        }

        if (!XMarkCheck(soldierPosition + steps , playerNumber , game )){
            System.out.println("Invalid move. X mark check failed");
            return false;
        }

        if (!checkKhal(soldierPosition,steps)){
            System.out.println("Invalid move. stone should be inside the board");
            return false;
        }

        return true;



    }

    public static boolean boundaryCheck(int newPos){
        return newPos <= 84 && newPos >= 0;
    }

    //Check if the cell is X and has enemy stone in it.
    public static boolean XMarkCheck(int newPos, int playerNumber, Game game){
        int[] X = {11, 22, 28, 39, 45, 56, 62, 73};
        boolean exists = false;

        for (int number : X) {
            if (number == newPos) {
                exists = true;
                break;
            }
        }

        if (exists){
            //Get other player position
            int index = Actions.getOtherPlayerPosition(newPos);

            //Get enemy array
            int [] positions;
            if(playerNumber == 1)
               positions  = game.getSecondStonesPositions();
            else positions = game.getFirstStonesPositions();



            for (int position : positions) {
                if (position == index)
                    return false;
            }
            return true;

        }
        else return true;



    }

    //Check if the cell has one enemy stone in it.
    public static boolean checkEnemyStone(Game game,int newPos,int playerNumber){
        if (newPos < 8 || newPos > 75 ) return false;

        else{
            //Get other player index
            int index = Actions.getOtherPlayerPosition(newPos);

            //Get enemy array
            int [] positions;
            if(playerNumber == 1)
                positions  = game.getSecondStonesPositions();
            else positions = game.getFirstStonesPositions();



            for (int position : positions) {
                if (position == index)
                    return true;
            }
            return false;

        }
    }

    public static boolean checkKhal(int soldierPosition,int steps){
        if (soldierPosition == 0){
            return steps == 1;
        }
        return true;
    }

    public static boolean checkNoSoldiers(Game game,int playerNumber){
        int [] positions;
        if(playerNumber == 1)
            positions  = game.getFirstStonesPositions();
        else positions = game.getSecondStonesPositions();


        for(int position : positions){
            if (position!=0)
                return false;
        }
        return true;
    }

}
