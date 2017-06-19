package com.appbusters.robinkamboj.facebook;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;

import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity{

    private CardView hidenshowCardMain, hidenshowCardUnwanted;
    private LinearLayout hidenshowMain, hidenshowUnwanted;
    private RadioGroup rgUserType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        hidenshowCardMain = (CardView) findViewById(R.id.hidenshowcardmain);
        hidenshowCardUnwanted = (CardView) findViewById(R.id.hidenshowcardunwanted);
        hidenshowMain = (LinearLayout) findViewById(R.id.hidenshowmain);
        hidenshowUnwanted = (LinearLayout) findViewById(R.id.hidenshowunwanted);
        rgUserType = (RadioGroup) findViewById(R.id.rgusertype);

        Bundle params = new Bundle();
        params.putString("fields", "id,email,name,gender,cover,picture.type(large)");
        new GraphRequest(AccessToken.getCurrentAccessToken(), "me", params, HttpMethod.GET,
                new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse response) {
                        if (response != null) {
                            try {
                                JSONObject data = response.getJSONObject();

                                if(data.has("name")){
                                    Log.e("Login" + "name", data.getString("name"));
                                    ((TextView) findViewById(R.id.name)).setText(data.getString("name"));
                                }
                                if(data.has("gender")){
                                    Log.e("Login" + "Gender", data.getString("gender"));
                                    ((TextView) findViewById(R.id.gender)).setText(data.getString("gender"));
                                }
                                if(data.has("id")){
                                    String id = data.getString("id");
                                    Log.e("Login" + "Id", id);
                                    ((TextView) findViewById(R.id.id)).setText(id);

                                    String profilePicUrl = "http://graph.facebook.com/" + id + "/picture?type=large";

                                    Glide.with(ProfileActivity.this)
                                            .load(profilePicUrl)
                                            .into((CircleImageView) findViewById(R.id.profile_image));
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).executeAsync();

        rgUserType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int checkedId) {
                switch (checkedId){
                    case R.id.standard:{
                        hideGuruStuff();
                        break;
                    }
                    case R.id.guru:{
                        showGuruStuff();
                        break;
                    }
                }
            }
        });
    }

    public void showGuruStuff(){
        if(hidenshowCardMain.getVisibility()==View.GONE){
            hidenshowCardMain.setVisibility(View.VISIBLE);
            hidenshowUnwanted.setVisibility(View.VISIBLE);
            hidenshowMain.setVisibility(View.VISIBLE);
            hidenshowCardUnwanted.setVisibility(View.VISIBLE);
        }
    }

    public void hideGuruStuff(){
        if(hidenshowCardMain.getVisibility()==View.VISIBLE){
            hidenshowCardMain.setVisibility(View.GONE);
            hidenshowUnwanted.setVisibility(View.GONE);
            hidenshowMain.setVisibility(View.GONE);
            hidenshowCardUnwanted.setVisibility(View.GONE);
        }
    }
}
