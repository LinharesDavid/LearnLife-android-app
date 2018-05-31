package com.learnlife.learnlife.login.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.JsonObject;
import com.learnlife.learnlife.LearnLifeApplication;
import com.learnlife.learnlife.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterActivity extends AppCompatActivity {

    /***********************************************************
     *  Attributes
     **********************************************************/
    @BindView(R.id.edtFirstname) EditText edtFirstname;
    @BindView(R.id.edtPassword) EditText edtPassword;
    @BindView(R.id.edtEmail) EditText edtEmail;
    @BindView(R.id.edtConfirmPassword) EditText edtConfirmPassword;
    @BindView(R.id.edtLastname) EditText edtLastname;
    @BindView(R.id.prbRegister) ProgressBar prbRegister;

    private final String Tag = getClass().getSimpleName();
    private Animation anim; //Le faire dans une classe mère pour pas le répéter à chaque button
    private boolean isInvalid; //boolean pour savoir si les champs sont tous remplis ou pas


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ButterKnife.bind(this);
        anim = AnimationUtils.loadAnimation(this, R.anim.button_bounce);

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

        if(TextUtils.isEmpty(email)) {
            edtEmail.setError(getString(R.string.noEmail));
            isInvalid = true;
        }
        if(TextUtils.isEmpty(password)) {
            edtPassword.setError(getString(R.string.noPassword));
            isInvalid = true;
        }
        if(TextUtils.isEmpty(firstName)){
            edtFirstname.setError(firstName);
            isInvalid = true;
        }
        if(TextUtils.isEmpty(lastName)){
            edtLastname.setError(lastName);
            isInvalid = true;
        }

        if(!password.equals(confirmPassword)){
            edtPassword.setError(getString(R.string.password_unmatched));
            edtConfirmPassword.setError(getString(R.string.password_unmatched));
            isInvalid = true;
        }


        if(isInvalid){

            //popup error invalid à faire

            prbRegister.setVisibility(View.GONE);
            return;
        }

        JSONObject user = new JSONObject();
        try{
            user.put("email", email);
            user.put("password", password);
            user.put("firstName", firstName);
            user.put("lastName", lastName);
        }catch (JSONException e){
            e.printStackTrace();
            return;
        }

        AndroidNetworking.post(LearnLifeApplication.BASE_URL + "/users")
                .addJSONObjectBody(user)
                .setTag("register")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        prbRegister.setVisibility(View.GONE);
                        Log.d(Tag, "Register succeeded");
                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                    }

                    @Override
                    public void onError(ANError anError) {
                        prbRegister.setVisibility(View.GONE);

                        //Popup error à faire

                        String errorBody = anError.getErrorBody() != null ? anError.getErrorBody() : "error without content";
                        Log.d(Tag, "Register failed : "+errorBody);
                    }
                });
    }
}
