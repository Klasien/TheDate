package miraclethings.camvidtest;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class ChooseGender extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_gender);
    }

    public void clicked(View b) {
    	Intent i = new Intent(getApplicationContext(), MainActivity.class);
    	startActivity(i);
    	finish();
    }

}
