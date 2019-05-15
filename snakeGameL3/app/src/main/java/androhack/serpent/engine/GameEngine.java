package androhack.serpent.engine;

import android.service.quicksettings.Tile;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import androhack.serpent.classes.Coordonee;
import androhack.serpent.enums.Direction;
import androhack.serpent.enums.GameState;
import androhack.serpent.enums.TileType;

/**
 * Created by yazid on 30/05/17.
 */

public class GameEngine {
    public static final int GameWidth= 30;
    public static final int GameHeight= 40;

    private List<Coordonee> walls = new ArrayList<>();
    private List<Coordonee> snake= new ArrayList<>();

    private List<Coordonee> apples= new ArrayList<>();


    private Direction currentDirection= Direction.East;

    private GameState currentGameState= GameState.Running;

    private Random random= new Random();

    private boolean increaseTail= false;

    private Coordonee getSnakeHead(){
        return snake.get(0);
    }

    public GameEngine(){

    }

    public void initGame(){
        
        AddSnake();
        AddWalls();
        AddApples();
    }


    public void UpdateDirection(Direction newDirection){

        if (Math.abs(newDirection.ordinal() - currentDirection.ordinal()) %2 == 1){
            currentDirection= newDirection;
        }
    }

    public void Update(){
        // Mettre à jour le serpent

        switch (currentDirection){
            case North:
                UpdateSnake(0,-1);
                break;
            case East:
                UpdateSnake(1,0);
                break;
            case South:
                UpdateSnake(0,1);
                break;
            case West:
                UpdateSnake(-1,0);
                break;
        }
        // Contrôler les accrochages
        for (Coordonee w: walls) {
            if (snake.get(0).equals(w)){
                currentGameState = GameState.Lost;
            }
        }

        // Contrôler les accrochages avec soi-même
        for (int i=1; i< snake.size(); i++) {

            if (getSnakeHead().equals(snake.get(i))){
                currentGameState = GameState.Lost;
                return;
            }
        }

        // Contrôler les Appats
        Coordonee appleToRemove= null;
        for (Coordonee apple: apples) {
            if (getSnakeHead().equals(apple)){
                appleToRemove = apple;
                increaseTail= true;
            }
        }
        if (appleToRemove != null){
            apples.remove(appleToRemove);
            AddApples();
        }
    }




    public TileType[][] getMap(){
        TileType[][] map = new TileType[GameWidth][GameHeight];

        for (int x = 0; x < GameWidth; x++) {
            for (int y = 0; y <GameHeight ; y++) {
                map[x][y]= TileType.Nothing;
            }
        }
        for (Coordonee s: snake) {
            map[s.getX()][s.getY()] = TileType.SnakeTail;
        }
        map[snake.get(0).getX()][snake.get(0).getY()] = TileType.SnakeHead;

        for (Coordonee wall: walls) {
            map[wall.getX()][wall.getY()]  = TileType.Wall;

        }

        for (Coordonee a: apples) {
            map[a.getX()][a.getY()]= TileType.Apple;

        }

        return map;
    }

    private void UpdateSnake(int x, int y){
        int newX= snake.get(snake.size()-1).getX();
        int newY= snake.get(snake.size()-1).getY();

        for (int i = snake.size()-1; i > 0; i--) {
            snake.get(i).setX( snake.get(i-1).getX() );
            snake.get(i).setY( snake.get(i-1).getY() );

        }
        if(increaseTail){
            snake.add(new Coordonee(newX,newY));
            increaseTail= false;
        }
        snake.get(0).setX( snake.get(0).getX()+x);
        snake.get(0).setY( snake.get(0).getY()+y);
    }


    private void AddSnake() {
        snake.clear();

        snake.add(new Coordonee(8,8));
        snake.add(new Coordonee(7,8));
        snake.add(new Coordonee(6,8));
        snake.add(new Coordonee(5,8));
        snake.add(new Coordonee(4,8));
        snake.add(new Coordonee(3,8));
        snake.add(new Coordonee(2,8));
    }

    private void AddWalls() {
        for (int x = 0; x <GameWidth ; x++) {
            // les murs d'en haut et d'en bas
            walls.add(new Coordonee(x,0));
            walls.add(new Coordonee(x,GameHeight-1));
        }

            // les murs de gauche et de droite
        for (int y = 1; y <GameHeight ; y++) {
            walls.add(new Coordonee(0,y));
            walls.add(new Coordonee(GameWidth-1,y));

        }

    }

    private void AddApples(){
        Coordonee coordonee= null;


        boolean added= false;

        while (!added){
            int x = 1 + random.nextInt(GameWidth - 2);
            int y = 1 + random.nextInt(GameHeight - 2);

            coordonee = new Coordonee(x,y);

            boolean collision= false;
            for (Coordonee s: snake){
                if (s.equals(coordonee)){
                    collision= true;
                    // break;
                }
            }

            for (Coordonee a: apples){
                 if (a.equals(coordonee)) {
                     collision = true;
                    
                 }
            }
            added = !collision;

        }
        apples.add(coordonee);
    }

    public GameState getCurrentGameState(){
        return currentGameState;
    }


}
