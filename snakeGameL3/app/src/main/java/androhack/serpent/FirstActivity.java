package androhack.serpent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class FirstActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
    }


    public void affich(View view){
        Intent intt= new Intent(this, MainActivity.class);
        startActivity(intt);

    }

    public void apropos(View vi){
    	Intent in= new Intent(this, ScrollingActivity.class);
    	startActivity(in);
    }

    public void aide(View vie){
    	Intent aidee= new Intent(this, AideActivity.class);
    	startActivity(aidee);
    }


}
