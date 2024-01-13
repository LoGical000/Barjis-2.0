package Controller;



import Models.Game;

import java.util.List;

public class MoveController {

    public static Game Move(Game game, int steps, int soldierIndex , int playerNumber) {


        Game gameCopy = new Game(game);

        int[] soldiersPositions;

        if(playerNumber == 1)
            soldiersPositions  = gameCopy.getFirstStonesPositions();
        else soldiersPositions = gameCopy.getSecondStonesPositions();


        int soldierPosition = soldiersPositions[soldierIndex-1];

        if(MoveCheckController.checkMoveWithDetails(playerNumber,steps,soldierPosition,gameCopy)){

            //check if the cell has an enemy stone
            if (MoveCheckController.checkEnemyStone(gameCopy,soldierPosition+steps ,playerNumber)){
                //eatStone(gameCopy,soldierPosition+steps,playerNumber);
                int index = Actions.getOtherPlayerPosition(soldierPosition+steps);

                //Get enemy array
                int [] positions;
                if(playerNumber == 1)
                    positions  = game.getSecondStonesPositions();
                else positions = game.getFirstStonesPositions();


                for (int i=0;i<positions.length;i++)
                    if (positions[i] == index){
                        positions[i] = 0;
                    }

                if(playerNumber == 1)
                    game.setSecondStonesPositions(positions);
                else game.setFirstStonesPositions(positions);
            }

            soldiersPositions[soldierIndex-1] = soldierPosition+steps;

         if(playerNumber == 1)
            game.setFirstStonesPositions(soldiersPositions);
        else game.setSecondStonesPositions(soldiersPositions);


        }

//        System.out.println("first move ");
//        for(int i:gameCopy.getFirstStonesPositions()){
//
//            System.out.println(i);
//        }
//
//        System.out.println("second move");
//        for(int i:gameCopy.getSecondStonesPositions()){
//
//            System.out.println(i);
//        }

        return gameCopy;
    }

    public static void eatStone(Game game,int newPos,int playerNumber){
        int index = Actions.getOtherPlayerPosition(newPos);

        //Get enemy array
        int [] positions;
        if(playerNumber == 1)
            positions  = game.getSecondStonesPositions();
        else positions = game.getFirstStonesPositions();


        for (int i=0;i<positions.length;i++)
            if (positions[i] == index){
                positions[i] = 0;
            }

        if(playerNumber == 1)
            game.setSecondStonesPositions(positions);
        else game.setFirstStonesPositions(positions);

//        System.out.println("first eat");
//        for(int i:game.getFirstStonesPositions()){
//
//            System.out.println(i);
//        }
//
//        System.out.println("second eat");
//        for(int i:game.getSecondStonesPositions()){
//
//            System.out.println(i);
//        }




    }
}
