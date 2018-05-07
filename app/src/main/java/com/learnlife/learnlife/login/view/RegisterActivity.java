package com.learnlife.learnlife.login.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.learnlife.learnlife.R;

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

    private final String Tag = getClass().getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ButterKnife.bind(this);


    }



    public void imbRegisterEvent(View view){
        final String email = edtEmail.getText().toString();
        final String password = edtPassword.getText().toString();
        final String firstname = edtFirstname.getText().toString();
        final String lastname = edtLastname.getText().toString();


        AndroidNetworking.post("http://localhost:8080/users")
                .addBodyParameter("email", email)
                .addBodyParameter("password", password)
                .addBodyParameter("firstname", firstname)
                .addBodyParameter("lastname", lastname)
                .setTag("register")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(RegisterActivity.this, "OK", Toast.LENGTH_SHORT).show();
                        Log.d(Tag, "Register OK with user email: "+email+" & password: "+password);
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(RegisterActivity.this, "NOT OK", Toast.LENGTH_SHORT).show();
                        String error = anError.getMessage();
                        Log.d(Tag, anError.getErrorBody()+ " ");
                    }
                });
    }
}
