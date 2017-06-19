package com.appbusters.robinkamboj.facebook;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        hidenshowCardMain = (CardView) findViewById(R.id.hidenshowcardmain);
        hidenshowCardUnwanted = (CardView) findViewById(R.id.hidenshowcardunwanted);
        hidenshowMain = (LinearLayout) findViewById(R.id.hidenshowmain);
        hidenshowUnwanted = (LinearLayout) findViewById(R.id.hidenshowunwanted);

        Bundle params = new Bundle();
        params.putString("fields", "id,email,name,gender,cover,picture.type(large)");
        new GraphRequest(AccessToken.getCurrentAccessToken(), "me", params, HttpMethod.GET,
                new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse response) {
                        if (response != null) {
                            try {
                                JSONObject data = response.getJSONObject();

                                if (data.has("picture")) {
                                    String profilePicUrl = data.getJSONObject("picture").getJSONObject("data").getString("url");
                                    Log.e("URL", profilePicUrl);

                                    Glide.with(ProfileActivity.this)
                                            .load(profilePicUrl)
                                            .into((CircleImageView) findViewById(R.id.profile_image));

                                }
                                if(data.has("name")){
                                    Log.e("Login" + "name", data.getString("name"));
                                    ((TextView) findViewById(R.id.name)).setText(data.getString("name"));
                                }
                                if(data.has("gender")){
                                    Log.e("Login" + "Gender", data.getString("gender"));
                                    ((TextView) findViewById(R.id.gender)).setText(data.getString("gender"));

                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).executeAsync();
    }

    public void showGuruStuff(){
        hidenshowCardMain.setVisibility(View.VISIBLE);
        hidenshowUnwanted.setVisibility(View.VISIBLE);
        hidenshowMain.setVisibility(View.VISIBLE);
        hidenshowCardUnwanted.setVisibility(View.VISIBLE);
    }

    public void hideGuruStuff(){
        hidenshowCardMain.setVisibility(View.INVISIBLE);
        hidenshowUnwanted.setVisibility(View.INVISIBLE);
        hidenshowMain.setVisibility(View.INVISIBLE);
        hidenshowCardUnwanted.setVisibility(View.INVISIBLE);
    }
}
