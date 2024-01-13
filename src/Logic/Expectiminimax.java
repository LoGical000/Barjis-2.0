package Logic;

import Controller.Actions;
import Controller.MoveCheckController;
import Models.Game;
import Models.Pair;
import Models.Shell;

import java.util.*;

public class Expectiminimax {

    Game gameModel;
    static int turn = 0;


    public Expectiminimax(Game gameModel)
    {
        this.gameModel = gameModel;
        this.play();
    }

    public void play() {

        Actions.printBoard(gameModel);
        while (true) {
            humanPlay(turn);
            turn++;
            gameModel.refreshBoard();
            Actions.printBoard(gameModel);

            if (Actions.isFinal(gameModel)) {
                System.out.println("Human wins");
                break;
            }

            System.out.println("_____Computer Turn______");
            computerPlay(turn);
            turn++;

            gameModel.refreshBoard();
            Actions.printBoard(gameModel);

            if (Actions.isFinal(gameModel)) {
                System.out.println("Computer wins!");
                break;
            }
        }

    }

    private void computerPlay(int turn) {
        int limit =0;
        while(turn % 2 != 0) {
            System.out.println("\n Computer turn");
            int steps = Actions.throwShell();

            //Khal logic
            if (steps == 10 || steps == 25) {
//                System.out.println(firstPlayer);
//                System.out.println(Arrays.toString(secondPlayer.getSoldiersPositions()));
//                System.out.println(steps);
                Pair<Double, Game> result = MaxMove(gameModel,1,1);
//                System.out.println(secondPlayer);
//                System.out.println(result);
                gameModel = result.getValue();
                result = MaxMove(gameModel,steps,1);
                gameModel = result.getValue();
                //Actions.MoveKhal(players, secondPlayer);
                //Actions.MoveRegular(players, secondPlayer, steps);
            } else if (MoveCheckController.checkNoSoldiers(gameModel,2)) {
                System.out.println("\n There is no soldiers on board...\n");
            } else {
                Pair<Double, Game> result = MaxMove(gameModel,steps,1);
                gameModel = result.getValue();
                //Actions.MoveRegular(players, secondPlayer, steps);
            }

            if (steps == 2 || steps == 3 || steps == 4 || limit == 10) {
                turn++;
            }
            limit++;
        }


    }

    private static final int maxDepth = 4;

    private Pair<Double, Game> MaxMove(Game game, int steps, int depth) {
        if (Actions.isFinal(game) || depth == maxDepth) {
            return new Pair<>(Actions.evaluate(game, 2), game);
        }

        double avgEval = Double.NEGATIVE_INFINITY; // Initialize with infinity for Min nodes
        Game bestMove = null;

        List<Game> nextStates = Actions.allNextMoves(game, steps,2);


        for (Game nextState : nextStates) {
            //System.out.println(Arrays.toString(nextState.getSoldiersPositions()));
            double eval = ChanceMove(nextState, steps, depth + 1).getKey();
            avgEval = Math.max(avgEval, eval); // Take the maximum evaluation for Max nodes
            if (eval > avgEval) { // Keep track of the best move for Max nodes (optional for clarity)
                avgEval = eval;
                bestMove = nextState;
            }
        }

        return new Pair<>(avgEval, bestMove);
    }


    private Pair<Double, Game> MinMove(Game game, int steps, int depth) {
        if (Actions.isFinal(game) || depth == maxDepth) {
            return new Pair<>(Actions.evaluate(game, 2), game);
        }

        double avgEval = Double.POSITIVE_INFINITY; // Initialize with infinity for Min nodes
        Game bestOpponentMove = null;

        List<Game> nextStates = Actions.allNextMoves(game, steps,1);

        for (Game nextState : nextStates) {
            double eval = ChanceMove(nextState, steps, depth + 1).getKey();
            avgEval = Math.min(avgEval, eval); // Take the minimum evaluation for Min nodes
            if (eval < avgEval) { // Keep track of the best move (optional for clarity)
                avgEval = eval;
                bestOpponentMove = nextState;
            }
        }

        return new Pair<>(avgEval, bestOpponentMove);
    }


    private Pair<Double, Game> ChanceMove(Game game, int steps, int depth) {
        if (Actions.isFinal(game) || depth == maxDepth) {
            return new Pair<>(Actions.evaluate(game, 2), game);
        }

        double avgEval = 0.0;

        // Determine the chance player based on game logic (adjust as needed)
        int chancePlayer = (turn % 2 == 0) ? 1 : 2;

        List<Game> nextStates = Actions.allNextMoves(game, steps, chancePlayer);

        for (Game nextState : nextStates) {
            double eval = MaxMove(nextState,steps, depth + 1).getKey(); // Recursively call MaxMove
            avgEval += eval * getProbability(steps); // Weighted average based on probabilities

            System.out.println("this is eval" + eval);
            System.out.println("this is getp " + getProbability(steps));
            System.out.println("this is avgeval: " + avgEval);
        }


        return new Pair<>(avgEval, null); // Return average evaluation without bestMove for chance nodes
    }


    private double getProbability(int steps) {
        if (steps == 1)
            steps = 10;
        int[] probabilities = {40, 369, 1385, 2765, 3111, 1860, 466};

        // Find the index of the shell with the matching steps value
        int index = -1;
        List<Integer> values = new ArrayList<>(Shell.createShellMap().values()); // Convert keySet to a List
        for (int i = 0; i < values.size(); i++) {
            if ((values.get(i)) == steps) {
                index = i;
                break;
            }
        }

        if (index == -1) {
            return 0.0; // Or throw an exception if appropriate
        }

        int probability = probabilities[index];

        return (double) probability / Shell.createShellMap().values().stream().mapToInt(Integer::intValue).sum();
    }






    private void humanPlay(int turn) {
        int limit =0;
        while(turn % 2 == 0){
            System.out.println("\n First player turn");
            int steps = Actions.throwShell();

            //Khal logic
            if(steps == 10 || steps == 25){
                Actions.MoveKhal(gameModel,1);
                Actions.MoveRegular(gameModel,1,steps);
            }

            else if(MoveCheckController.checkNoSoldiers(gameModel,1)){
                System.out.println("\n There is no soldiers on board...\n");
            }

            else{
                Actions.MoveRegular(gameModel,1,steps);
            }

            if (steps==2 || steps==3 || steps==4 || limit==10) {
                turn++;
                limit=0;
            }
            limit++;
        }}

    public static void main(String[] args) {
        Game gameModel = new Game();
        Expectiminimax g = new Expectiminimax(gameModel);
        g.play();

    }

}
