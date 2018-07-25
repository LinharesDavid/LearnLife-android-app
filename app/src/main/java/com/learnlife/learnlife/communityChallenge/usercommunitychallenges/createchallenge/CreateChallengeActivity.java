package com.learnlife.learnlife.communityChallenge.usercommunitychallenges.createchallenge;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.androidnetworking.error.ANError;
import com.androidnetworking.model.Progress;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.common.util.IOUtils;
import com.learnlife.learnlife.R;
import com.learnlife.learnlife.SessionManager;
import com.learnlife.learnlife.crosslayers.models.Challenge;
import com.learnlife.learnlife.crosslayers.models.User;
import com.learnlife.learnlife.crosslayers.utils.CircleTransform;
import com.learnlife.learnlife.tags.modele.Tag;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreateChallengeActivity extends AppCompatActivity implements ICreateChallengeView {
    @BindView(R.id.challenge_imv)
    ImageView imageView;
    @BindView(R.id.challengeTitle_txv)
    EditText titleEditText;
    @BindView(R.id.createChallengeDetails_txv)
    EditText detailsEditText;
    @BindView(R.id.challengesTags_btn)
    Button tagsButton;
    @BindView(R.id.challengesDuration_btn)
    Button durationButton;
    @BindView(R.id.createBtn)
    Button createButton;
    private ICreateChallengePresenter presenter;
    private List<Tag> tags;
    private List<String> tagsNames;
    private ArrayList<String> tagsChosen = new ArrayList<>();
    private String[] durations;
    private final int SECONDS_HOUR = 3600;
    private int duration = 0;
    private AlertDialog tagDialog;
    private AlertDialog hourDialog;
    private AlertDialog.Builder loadingBuilder;
    private AlertDialog loadingDialog;
    public static final int PICK_IMAGE = 1;
    private File imageFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_challenge);

        ButterKnife.bind(this);

        this.presenter = new CreateChallengePresenter(this);
        presenter.getTags();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.durations = getResources().getStringArray(R.array.durations);

        this.setUI();
    }

    private void setUI() {
        this.tagsButton.setClickable(false);
    }

    @OnClick(R.id.challengesTags_btn)
    void onTagsButtonClicked() {
        if(tagDialog == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            ArrayAdapter adapter = new ArrayAdapter<>(this, R.layout.activity_create_challenge_list_item, tagsNames);
            ListView listView = new ListView(this);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener((parent, view, position, id) -> {
                TextView textView = (TextView) adapter.getView(position, view, parent);
                Tag tag = tags.get(position);

                if(tagsChosen.contains(tag.getId())) {
                    textView.setBackgroundResource(R.color.white);
                    tagsChosen.remove(tag.getId());
                } else {
                    textView.setBackgroundResource(R.color.colorPrimary);
                    tagsChosen.add(tag.getId());
                    tagsButton.setText(tag.getName() + "...");
                }
            });

            builder.setView(listView);
            builder.setTitle(R.string.tags);
            builder.setNeutralButton(R.string.ok, (dialog, which) -> dialog.dismiss());

            this.tagDialog = builder.create();
        }

        tagDialog.show();
    }

    @OnClick(R.id.challengesDuration_btn)
    void onDurationButtonClicked() {
        if(this.hourDialog == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            ArrayAdapter adapter = new ArrayAdapter<>(this, R.layout.activity_create_challenge_list_item, durations);

            builder.setAdapter(adapter, this::onTimeSelected);
            builder.setTitle(R.string.duration);

            this.hourDialog = builder.create();
        }

        hourDialog.show();
    }

    @OnClick(R.id.challenge_imv)
    void onImageViewProfileClicked() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == PICK_IMAGE) {
            if (data == null) return;
            Glide.with(this)
                    .load(data.getData())
                    .into(imageView);
            File file = null;
            try {
                file = getFileFromUri(data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(file != null)
                this.imageFile = file;
        }
    }

    private File getFileFromUri(Uri uri) throws IOException {
        InputStream inputStream;
        File file;

        inputStream = this.getContentResolver().openInputStream(uri);
        file = File.createTempFile("image", null, this.getCacheDir());
        FileOutputStream outputStream = new FileOutputStream(file);
        IOUtils.copyStream(inputStream, outputStream);
        inputStream.close();
        outputStream.close();

        return file;
    }

    private void onTimeSelected(DialogInterface dialog, int which) {
        durationButton.setText(durations[which]);
        this.duration = ++which * SECONDS_HOUR;
        dialog.dismiss();
    }

    private AlertDialog.Builder getLoadingBuilder(String title, String message) {
        if(loadingBuilder == null) {
            this.loadingBuilder = new AlertDialog.Builder(this);
        }

        loadingBuilder.setTitle(title);
        loadingBuilder.setMessage(message);


        return loadingBuilder;
    }

    private AlertDialog.Builder getLoadingBuilder(int title, int message) {
        return getLoadingBuilder(getResources().getString(title), getResources().getString(message));
    }

    @OnClick(R.id.createBtn)
    void onCreateButtonClicked() {
        String title = titleEditText.getText().toString();
        String details = detailsEditText.getText().toString();
        User user = SessionManager.getInstance().getUser();
        if(title.isEmpty() || details.isEmpty() || tagsChosen.isEmpty() || duration == 0) return;

        Challenge challenge = new Challenge(title, details, 50, duration, user.getId(), tagsChosen.toArray(new String[tagsChosen.size()]));

        presenter.createChallenge(user, challenge);
        loadingDialog = getLoadingBuilder(R.string.challenge, R.string.wait_while_creation).create();
        loadingDialog.setCanceledOnTouchOutside(false);
        loadingDialog.show();
    }

    @Override
    public void onTagsRetrieved(List<Tag> tags) {
        this.tags = tags;
        tagsNames = new ArrayList<>();
        for(Tag tag : tags)
            tagsNames.add(tag.getName());
        this.tagsButton.setClickable(true);
    }

    @Override
    public void onTagsError(ANError error) {
        loadingBuilder = getLoadingBuilder(R.string.challenge, R.string.network_error_unknown);
        loadingBuilder.setPositiveButton(R.string.retry, (dialog, which) -> presenter.getTags());
        loadingBuilder.setNeutralButton(R.string.cancel, (dialog, which) -> finish());
        loadingBuilder.create().show();
    }

    @Override
    public void onChallengeCreated(Challenge challenge) {
        presenter.setChallengeImage(challenge, this.imageFile);
    }

    @Override
    public void onChallengeCreationError(ANError error) {
        loadingDialog.dismiss();
        getLoadingBuilder(R.string.challenge, R.string.network_error_unknown).create().show();
    }

    @Override
    public void onSetChallengeImageSucceed(Challenge challenge) {
        this.loadingDialog.dismiss();
        finish();
    }

    @Override
    public void onSetChallengeImageFailed(ANError error) {
        this.loadingDialog.dismiss();
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }

        return true;
    }
}
