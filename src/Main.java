import Controller.Actions;
import Logic.UserPlay;
import Models.Game;

public class Main {
    public static void main(String[] args) {
        //UserPlay userPlay = new UserPlay();

        Game game = new Game();
        Actions.allNextMoves(game,1,2);

    }
}