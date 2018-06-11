package com.learnlife.learnlife.login.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.learnlife.learnlife.R;
import com.learnlife.learnlife.crosslayers.utils.Dialog;
import com.learnlife.learnlife.tags.view.TagActivity;

import org.json.JSONException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterActivity extends AppCompatActivity implements ILoginView {

    /***********************************************************
     *  Attributes
     **********************************************************/
    @BindView(R.id.edtFirstname)
    EditText edtFirstname;
    @BindView(R.id.edtPassword)
    EditText edtPassword;
    @BindView(R.id.edtEmail)
    EditText edtEmail;
    @BindView(R.id.edtConfirmPassword)
    EditText edtConfirmPassword;
    @BindView(R.id.edtLastname)
    EditText edtLastname;
    @BindView(R.id.prbRegister)
    ProgressBar prbRegister;

    private final String Tag = getClass().getSimpleName();
    private Animation anim; //Le faire dans une classe mère pour pas le répéter à chaque button
    private boolean isInvalid; //boolean pour savoir si les champs sont tous remplis ou pas
    private ILoginPresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ButterKnife.bind(this);
        anim = AnimationUtils.loadAnimation(this, R.anim.button_bounce);

        presenter = new LoginPresenter(this);

    }


    public void imbRegisterEvent(View view) throws JSONException {
        view.startAnimation(anim);
        prbRegister.setVisibility(View.VISIBLE);

        //reset du boolean
        isInvalid = false;

        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        String confirmPassword = edtConfirmPassword.getText().toString().trim();
        String firstName = edtFirstname.getText().toString().trim();
        String lastName = edtLastname.getText().toString().trim();

        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            edtEmail.setError(getString(R.string.noValidEmail));
            isInvalid = true;
        }
        if (TextUtils.isEmpty(email)) {
            edtEmail.setError(getString(R.string.noEmail));
            isInvalid = true;
        }
        if (TextUtils.isEmpty(password)) {
            edtPassword.setError(getString(R.string.noPassword));
            isInvalid = true;
        }
        if (TextUtils.isEmpty(firstName)) {
            edtFirstname.setError(firstName);
            isInvalid = true;
        }
        if (TextUtils.isEmpty(lastName)) {
            edtLastname.setError(lastName);
            isInvalid = true;
        }

        if (!password.equals(confirmPassword)) {
            edtPassword.setError(getString(R.string.password_unmatched));
            edtConfirmPassword.setError(getString(R.string.password_unmatched));
            isInvalid = true;
        }


        if (isInvalid) {

            //popup error invalid à faire

            prbRegister.setVisibility(View.GONE);
            return;
        }

        presenter.registerUser(firstName, lastName, email, password);
    }

    @Override
    public void loginSucceed() {
        prbRegister.setVisibility(View.GONE);
        Log.d(Tag, "Register succeeded");
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
    }


    @Override
    public void loginFailed(String error) {
        prbRegister.setVisibility(View.GONE);
        Dialog.showErrorMessageDialog(this, getString(R.string.register_error_msg));
    }
}
