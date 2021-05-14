package at.aau.risiko;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class login_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activity);

        EditText txtNickname = (EditText)findViewById(R.id.txtNickname);
        Button btnConfirm = (Button)findViewById(R.id.btnConfirm);

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //to send on server
                String enteredNickname = txtNickname.getText().toString();

                if(txtNickname.getText().toString().isEmpty()){
                    showToast("Player's name has not been entered!");
                }
                else{
                    showToast("Player's name: " + enteredNickname);
                }
            }
        });
    }

    public void showToast(String message){
        Log.i("BUTTON", "Showing toast!!");
        Toast.makeText(this.getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}