package com.appbusters.robinkamboj.facebook;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;

import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

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
//                                    Bitmap profilePic= BitmapFactory.decodeStream(profilePicUrl.openConnection().getInputStream());
//                                    mImageView.setBitmap(profilePic);
                                    Glide.with(ProfileActivity.this)
                                            .load(profilePicUrl)
                                            .into((CircleImageView) findViewById(R.id.profile_image));

                                    final String firstName = data.getString("first_name");
                                    final String lastName = data.getString("last_name");
                                    final String gender = data.getString("gender");



                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Log.e("Login" + "FirstName", firstName);
                                            Log.e("Login" + "LastName", lastName);
                                            Log.e("Login" + "Gender", gender);

                                            String name = firstName+lastName;
                                            ((TextView) findViewById(R.id.name)).setText(name);
                                            ((TextView) findViewById(R.id.gender)).setText(gender);
                                        }
                                    });
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).executeAsync();
    }
}
