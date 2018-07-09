package com.learnlife.learnlife.tags.view;

import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.google.gson.Gson;
import com.learnlife.learnlife.Constants;
import com.learnlife.learnlife.SessionManager;
import com.learnlife.learnlife.crosslayers.models.User;
import com.learnlife.learnlife.tags.modele.Tag;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Adama on 10/06/2018.
 */

public class TagPresenter implements ITagPresenter {
    private final String Tag = getClass().getSimpleName();
    private ITagView tagView;

    public TagPresenter(ITagView tagView){
        this.tagView = tagView;
    }

    @Override
    public void displayAllTags(String urlAllTag) {

        AndroidNetworking.get(urlAllTag)
                .setTag("tags")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsObjectList(com.learnlife.learnlife.tags.modele.Tag.class, new ParsedRequestListener<List<Tag>>() {

                    @Override
                    public void onResponse(List<Tag> response) {
                        Log.d(Tag, "get all tag succeeded");
                        tagView.getTagSucceed(response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        String errorBody = anError.getErrorBody() != null ? anError.getErrorBody() : "error without content";
                        Log.d(Tag, "Display Tag failed : "+errorBody);
                        tagView.getTagFailed();
                    }
                });
    }

    @Override
    public void affectTagToUser(Tag.JsonTag jsonTag) {
        JSONObject jsonObject = new JSONObject();
        try {
            JSONArray array = new JSONArray(jsonTag.tags);
            jsonObject.accumulate("tags", array);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String idUser = SessionManager.getInstance().getUser().getId();
        AndroidNetworking.put(Constants.BASE_URL + Constants.EXTENDED_URL_UPDATE_USERTAGS + idUser)
                .addJSONObjectBody(jsonObject)
                .setTag("tag")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(Tag, "Tag update succeeded");
                        tagView.updateUserTagSucceed();
                        Gson gson = new Gson();
                        User user = gson.fromJson(response.toString(), User.class);
                        SessionManager.getInstance().updateUser(user);
                    }

                    @Override
                    public void onError(ANError anError) {
                        String errorBody = anError.getErrorBody() != null ? anError.getErrorBody() : "error without content";
                        Log.d(Tag, "Update Tag user failed : "+errorBody);
                        tagView.updateUserTagFailed();
                    }
                });
    }
}
