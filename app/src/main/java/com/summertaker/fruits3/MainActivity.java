package com.summertaker.fruits3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements ViewInterface {

    ArrayList<Member> mMembers;
    ViewPager2 mViewPager2;
    ViewAdapter mViewAdapter;

    boolean mAutoSlideMode = true;
    int mCurrentPage = 0;
    Timer mSlideTimer;
    final long SLIDE_DELAY_MS = 1000; // Delay in milliseconds before task is to be executed
    final long SLIDE_PERIOD_MS = 4000; // Time in milliseconds between successive task executions.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMembers = new ArrayList<>();
        mViewPager2 = findViewById(R.id.viewPager2);
        mViewAdapter = new ViewAdapter(mMembers, this);

        String url = "http://summertaker.cafe24.com/reader/akb48_json.php";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //Log.d(">>", response.toString());
                        try {
                            JSONArray jsonArray = response.getJSONArray("members");
                            JSONArray shuffleArray = shuffleJsonArray(jsonArray);
                            for (int i = 0; i < shuffleArray.length(); i++) {
                                JSONObject jsonObject = shuffleArray.getJSONObject(i);
                                Member member = new Member();
                                member.setId(jsonObject.getString("id"));
                                member.setGroup(jsonObject.getString("groups"));
                                member.setTeam(jsonObject.getString("team"));
                                member.setName(jsonObject.getString("name"));
                                member.setFurigana(jsonObject.getString("furigana"));
                                member.setBirthday(jsonObject.getString("birthday"));
                                member.setAge(jsonObject.getString("age"));
                                member.setImage(jsonObject.getString("image"));
                                member.setTwitter(jsonObject.getString("twitter"));
                                member.setInstagram(jsonObject.getString("instagram"));
                                member.setWiki(jsonObject.getString("wiki"));
                                mMembers.add(member);
                            }
                            mViewPager2.setAdapter(mViewAdapter);
                            startSlide();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, error.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        VolleySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }

    private static JSONArray shuffleJsonArray(JSONArray array) throws JSONException {
        // Implementing Fisher–Yates shuffle
        Random rnd = new Random();
        for (int i = array.length() - 1; i >= 0; i--) {
            int j = rnd.nextInt(i + 1);
            // Simple swap
            Object object = array.get(j);
            array.put(j, array.get(i));
            array.put(i, object);
        }
        return array;
    }

    private void startSlide() {
        /*After setting the adapter use the timer */
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (mAutoSlideMode) {
                    if (mCurrentPage == mMembers.size() - 1) {
                        mCurrentPage = 0;
                    }
                    mViewPager2.setCurrentItem(mCurrentPage++, true);
                }
            }
        };

        mSlideTimer = new Timer(); // This will create a new Thread
        mSlideTimer.schedule(new TimerTask() { // task to be scheduled
            @Override
            public void run() {
                handler.post(Update);
            }
        }, SLIDE_DELAY_MS, SLIDE_PERIOD_MS);
    }

    public void onMemberPictureClick(Member member) {
        mAutoSlideMode = !mAutoSlideMode;
        String msg = mAutoSlideMode ? getString(R.string.start) : getString(R.string.stop);
        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
    }
}
