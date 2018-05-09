package com.learnlife.learnlife.login.view;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.learnlife.learnlife.R;
import com.learnlife.learnlife.home.view.HomeActivity;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.edtEmail) EditText edtEmail;
    @BindView(R.id.edtPassword) EditText edtPassword;
    @BindView(R.id.imbLogin) ImageButton imbLogin;
    @BindView(R.id.lblForgotPassword) TextView lblForgotPassword;
    @BindView(R.id.lblNewUser) TextView lblNewUser;
    @BindView(R.id.prbLogin) ProgressBar prbLogin;

    private Animation anim; //Le faire dans une classe mère pour pas le répéter à chaque button
    private boolean isIncomplete; //boolean pour savoir si les champs sont tous remplis ou pas
    private final String Tag = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        anim = AnimationUtils.loadAnimation(this, R.anim.button_bounce);
    }

    @OnClick(R.id.imbLogin)
    public void loginEvent(View view){
        //reset du boolean
        isIncomplete = false;

        //Démarre une animation bounce sur le bouton et une progressbar (VISUEL)
        view.startAnimation(anim);
        prbLogin.setVisibility(View.VISIBLE);

        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();

        //Check si les edittexts sont vides ou 0-length
        if(TextUtils.isEmpty(email)) {
            edtEmail.setError(getString(R.string.noEmail));
            isIncomplete = true;
        }
        if(TextUtils.isEmpty(password)) {
            edtPassword.setError(getString(R.string.noPassword));
            isIncomplete = true;
        }

        //J'arrête l'event de login si les champs ne sont pas remplis
        if(isIncomplete){
            prbLogin.setVisibility(View.GONE);
            return;
        }

        JSONObject user = new JSONObject();
        try{
            user.put("email", email);
            user.put("password", password);
        }catch (JSONException e){
            e.printStackTrace();
            return;
        }

        AndroidNetworking.post("http://192.168.100.83:8080/auth/login")
                .addJSONObjectBody(user)
                .setTag("login")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        prbLogin.setVisibility(View.GONE);
                        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                        Log.d(Tag, "Login succeeded");
                    }

                    @Override
                    public void onError(ANError anError) {
                        prbLogin.setVisibility(View.GONE);
                        //Popup error à faire

                        String errorBody = anError.getErrorBody() != null ? anError.getErrorBody() : "error without content";
                        Log.d(Tag, "Login failed : "+errorBody);
                    }
                });

    }

    public void registerEvent(View view){
        startActivity(new Intent(this, RegisterActivity.class));
    }

}
