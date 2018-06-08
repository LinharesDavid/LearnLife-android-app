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
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.learnlife.learnlife.LearnLifeApplication;
import com.learnlife.learnlife.Main.view.MainActivity;
import com.learnlife.learnlife.R;
import com.learnlife.learnlife.crosslayers.utils.Dialog;
import com.learnlife.learnlife.tags.view.TagActivity;

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
    public final static String EXTRA_IDUSER = "IDUSER";
    public final static String EXTRA_NAMEUSER = "NAMEUSER";
    private final String jsonId = "user_id";
    private final String jsonToken = "token";
    private final String jsonFirstName = "firstname";
    private final String jsonTagCount = "tagCount";

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


        String urlRouteLogin = LearnLifeApplication.BASE_URL + "/auth/login";
        AndroidNetworking.post(urlRouteLogin)
                .addJSONObjectBody(user)
                .setTag("login")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        prbLogin.setVisibility(View.GONE);
                        Log.d(Tag, "Login succeeded");

                        //Instancie une ResponseLogin qui récupère le token et l'id
                        ResponseLogin responseLogin = null;
                        try{
                            responseLogin = new ResponseLogin(response.getString(jsonToken), response.getString(jsonId), response.getString(jsonFirstName), response.getInt(jsonTagCount));
                        }catch (JSONException e){e.printStackTrace();}

                        if(responseLogin == null)
                            return;

                        Intent intent;
                        //Si premiere connexion alors TagCount == 0
                        if(responseLogin.tagCount == 0) {
                            intent = new Intent(LoginActivity.this, TagActivity.class);
                            intent.putExtra(EXTRA_NAMEUSER, responseLogin.firstName);
                            intent.putExtra(EXTRA_IDUSER, responseLogin.idUser);
                        }else{
                            intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.putExtra(EXTRA_IDUSER, responseLogin.idUser);
                        }
                        startActivity(intent);

                    }

                    @Override
                    public void onError(ANError anError) {
                        prbLogin.setVisibility(View.GONE);

                        Dialog.showErrorMessageDialog(LoginActivity.this, getString(R.string.login_error_msg));

                        String errorBody = anError.getErrorBody() != null ? anError.getErrorBody() : "error without content";
                        Log.d(Tag, "Login failed : "+errorBody);
                    }
                });

    }

    public void registerEvent(View view){
        startActivity(new Intent(this, RegisterActivity.class));
    }


    public class ResponseLogin{
        private String token;
        private String idUser;
        private String firstName;
        private int tagCount;

        public ResponseLogin(String token, String idUser, String username, int tagCount) {
            this.token = token;
            this.idUser = idUser;
            this.firstName = username;
            this.tagCount = tagCount;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getIdUser() {
            return idUser;
        }

        public void setIdUser(String idUser) {
            this.idUser = idUser;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String username) {
            this.firstName = username;
        }

        public int getTagCount() {
            return tagCount;
        }

        public void setTagCount(int tagCount) {
            this.tagCount = tagCount;
        }
    }
}
