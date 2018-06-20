package com.learnlife.learnlife.profile.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.OkHttpResponseListener;
import com.learnlife.learnlife.Constants;
import com.learnlife.learnlife.R;
import com.learnlife.learnlife.SessionManager;
import com.learnlife.learnlife.crosslayers.models.User;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Response;

public class ProfileUserActivity extends AppCompatActivity {

    @BindView(R.id.edtInfoFirstname)
    EditText editFirstname;
    @BindView(R.id.edtInfoLastname)
    EditText editLastname;
    @BindView(R.id.edtInfoEmail)
    EditText editEmail;

    private final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private User user = SessionManager.getInstance().getUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_user);
        ButterKnife.bind(this);
        editFirstname.setText(user.getFirstname());
        editLastname.setText(user.getLastname());
        editEmail.setText(user.getEmail());
    }

    @OnClick(R.id.btnInfoSave)
    void ButtonSaveClicked() {
        String firstname = editFirstname.getText().toString();
        String lastname = editLastname.getText().toString();
        String email = editEmail.getText().toString();

        if(firstname.trim().length() > 0
                || lastname.toString().trim().length() > 0
                || email.trim().length() > 0) {
            if(email.matches(emailPattern)) {

                user.setFirstname(firstname);
                user.setLastname(lastname);
                user.setEmail(email);

                JSONObject userJson = new JSONObject();
                try {
                    userJson.put(Constants.RESPONSE_KEY_USER_FIRSTNAME, firstname);
                    userJson.put(Constants.RESPONSE_KEY_USER_LASTNAME, lastname);
                    userJson.put(Constants.RESPONSE_KEY_USER_EMAIL, email);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                AndroidNetworking
                        .put(Constants.BASE_URL +
                                Constants.EXTENDED_URL_USERS +
                                SessionManager.getInstance().getUser().getId())
                        .addHeaders(Constants.HEADER_AUTHORIZATION, user.getToken())
                        .addJSONObjectBody(userJson)
                        .build()
                        .getAsOkHttpResponse(new OkHttpResponseListener() {
                            @Override
                            public void onResponse(Response response) {
                                if(response.code() != 200) {
                                    Toast.makeText(getApplicationContext(), R.string.network_error_unknown, Toast.LENGTH_SHORT).show();
                                }

                                finish();
                            }

                            @Override
                            public void onError(ANError anError) {

                            }
                        });
            } else {
                Toast.makeText(this, R.string.noValidEmail, Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, R.string.no_text_entry, Toast.LENGTH_SHORT).show();
        }
    }
}
