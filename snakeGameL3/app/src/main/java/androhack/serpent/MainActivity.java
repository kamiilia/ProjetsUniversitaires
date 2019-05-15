package androhack.serpent;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androhack.serpent.engine.GameEngine;
import androhack.serpent.enums.Direction;
import androhack.serpent.enums.GameState;
import androhack.serpent.views.SnakeView;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener{

    private GameEngine gameEngine;
    private SnakeView snakeView;
    private SnakeView mySnake;

    private final Handler handler= new Handler();

    private final long updateDelay= 160;

    private float prevX,prevY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameEngine = new GameEngine();
        gameEngine.initGame();


        snakeView = (SnakeView)findViewById(R.id.snakeView);
        snakeView.setOnTouchListener(this);

        mySnake = (SnakeView)findViewById(R.id.snakeView);
        mySnake.setOnTouchListener(this);
        startUpdateHandler();
 	}
    
    

    private void startUpdateHandler(){
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                gameEngine.Update();

                if( gameEngine.getCurrentGameState() == GameState.Running){
                    handler.postDelayed(this, updateDelay);

                }
                if (gameEngine.getCurrentGameState()== GameState.Lost){
                    onGameLost();
                }


                snakeView.setSnakeViewMap(gameEngine.getMap());
                mySnake.setSnakeViewMap(gameEngine.getMap());

                snakeView.invalidate();


            }
        }, updateDelay);
    }
   

    private void onGameLost(){
        Toast.makeText(this,"Vous avez perdu !!!!", Toast.LENGTH_SHORT).show();


    }
    /* private void ranDomWalk(){
         int dirAleatoire=((int) Math.random())%4;

         switch (dirAleatoire) {
             case 0:
                 gameEngine.UpdateDirection(Direction.North);
                 break;
             case 1:
                 gameEngine.UpdateDirection(Direction.East);
                 break;
             case 2:
                 gameEngine.UpdateDirection(Direction.West);
                 break;
             case 3:
                 gameEngine.UpdateDirection(Direction.South);
                 break;

         }


     }*/


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                prevX= event.getX();
                prevY= event.getY();

                break;
            case MotionEvent.ACTION_UP:
                float newX= event.getX();
                float newY= event.getY();

                //Calculer où on swipe
                if (Math.abs(newX - prevX) > Math.abs(newY - prevY)){
                    //Direction à  Gauche et à Droite
                    if (newX > prevX){
                        // Tourner à Droite
                        gameEngine.UpdateDirection(Direction.East);
                    }else {
                        // Tourner à Gauche
                        gameEngine.UpdateDirection(Direction.West);
                    }


                }else{
                    // Direction en Haut et en bas
                    if (newY > prevY){
                        // Bas
                        gameEngine.UpdateDirection(Direction.South);
                    }else {
                        // Haut
                        gameEngine.UpdateDirection(Direction.North);
                    }

                }

                break;
        }

        return true;
    }





    public void retour(View view){
        Intent intentt= new Intent(this, FirstActivity.class);
        startActivity(intentt);
    }
    /*public void rejouer(View view1){
        Intent rej= new Intent(this, MainActivity.class);
        startActivity(rej);
    }*/

    protected void onRestart(){
        super.onRestart();
        setContentView(R.layout.activity_main);

        gameEngine = new GameEngine();
        gameEngine.initGame();


        snakeView = (SnakeView)findViewById(R.id.snakeView);
        snakeView.setOnTouchListener(this);

        mySnake = (SnakeView)findViewById(R.id.snakeView);
        mySnake.setOnTouchListener(this);
        startUpdateHandler();
    }

}
