package xyz.dushyant31.laundroapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {


    private Button login;
    String Username1,Password1;
    private EditText Username,Password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        login = (Button) findViewById(R.id.login_btn);
        Username = (EditText) findViewById(R.id.Username);
        Password = (EditText) findViewById(R.id.Password);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // view.startAnimation(anim_translate);
                Username1 = Username.getText().toString();
                Password1 = Password.getText().toString();
                new ValidateDataActivity().execute();
            }
        });
    }

//    Intent intent = new Intent(Login.this, MainActivity.class);
//    startActivity(intent);

    class ValidateDataActivity extends AsyncTask < Void, Void, Void > {
        String Role1 = "default";
        @Nullable
        @Override
        protected Void doInBackground(Void...params) {
            //Log.i(Controller.TAG, "IDVALUE" + Username1);
            JSONObject jsonObject = Controller.UserValidator(Username1,Password1);
            //Log.i(Controller.TAG, "Json obj " + jsonObject);

            try {
                if (jsonObject != null) {

                    JSONObject user1 = jsonObject.getJSONObject("user");

                    Role1 = user1.getString("Role");

                }
            } catch (JSONException je) {
                Log.i(Controller.TAG, "" + je.getLocalizedMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (Role1.equals("Driver")) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);

            } else
                Toast.makeText(getApplicationContext(), "ID not found", Toast.LENGTH_LONG).show();
        }


    }

}