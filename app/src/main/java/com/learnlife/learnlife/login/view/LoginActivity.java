package com.learnlife.learnlife.login.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.learnlife.learnlife.R;
import com.learnlife.learnlife.crosslayers.widget.LearnLifeButton;
import com.learnlife.learnlife.home.view.HomeActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.edtEmail)
    EditText edtEmail;
    @BindView(R.id.edtPassword)
    EditText edtPassword;
    @BindView(R.id.imbLogin)
    LearnLifeButton imbLogin;
    @BindView(R.id.lblForgotPassword)
    TextView lblForgotPassword;
    @BindView(R.id.lblNewUser)
    TextView lblNewUser;
    @BindView(R.id.prbLogin)
    ProgressBar prbLogin;

    private FirebaseAuth auth;
    private boolean isIncomplete; //boolean pour savoir si les champs sosnt tous remplis ou pas

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        auth = FirebaseAuth.getInstance();
    }

    @OnClick(R.id.imbLogin)
    public void loginEvent(View view) {
        //reset du boolean
        isIncomplete = false;

        //Démarre une animation bounce sur le bouton et une progressbar (VISUEL)
        //view.startAnimation(anim);
        prbLogin.setVisibility(View.VISIBLE);

        String email = edtEmail.getText().toString();
        String password = edtPassword.getText().toString();

        //Check si les edittexts sont vides ou 0-length
        if (TextUtils.isEmpty(email)) {
            edtEmail.setError(getString(R.string.noEmail));
            isIncomplete = true;
        }
        if (TextUtils.isEmpty(password)) {
            edtPassword.setError(getString(R.string.noPassword));
            isIncomplete = true;
        }

        //J'arrête l'event de login si les champs ne sont pas remplis
        if (isIncomplete) {
            prbLogin.setVisibility(View.GONE);
            return;
        }


        //Methode SignIN de Firebase
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        prbLogin.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                        } else {
                            Toast.makeText(LoginActivity.this, "Incorrect password or email", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
