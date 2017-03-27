package pwgame.passwordgame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
    }

    public void openIntroduction(View view) {
        Intent intent = new Intent(this, introduction.class);
        startActivity(intent);
    }

    public void openInstruction(View view) {
        Intent intent = new Intent(this, Instruction.class);
        startActivity(intent);
    }
    public void openAbout(View view) {
        Intent intent = new Intent(this, AboutPage.class);
        startActivity(intent);
    }

    public void openLogin(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
