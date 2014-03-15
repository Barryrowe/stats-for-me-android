package com.pineapplepiranha.mobile;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Background;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EFragment;
import com.googlecode.androidannotations.annotations.UiThread;
import com.googlecode.androidannotations.annotations.ViewById;
import com.googlecode.androidannotations.annotations.rest.RestService;
import com.pineapplepiranha.mobile.rest.ICounterClient;
import com.pineapplepiranha.mobile.rest.model.StatResult;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by barry on 3/14/14.
 */
@EFragment(R.layout.fragment_main)
public class SimpleFragment extends Fragment {

    private static final String[] statNames = new String[]{"pushup", "water"};
    private Map<String, TextView> statViews = new HashMap<String, TextView>();

    @ViewById(R.id.plus_one_pushup)
    Button p1Pushup;
    @ViewById(R.id.plus_five_pushup)
    Button p5pushup;
    @ViewById(R.id.less_one_pushup)
    Button l1Pushup;
    @ViewById(R.id.less_five_pushup)
    Button l5Pushup;

    @ViewById(R.id.plus_one_water)
    Button p1Water;
    @ViewById(R.id.plus_five_water)
    Button p5Water;
    @ViewById(R.id.less_one_water)
    Button l1Water;
    @ViewById(R.id.less_five_water)
    Button l5Water;

    @ViewById
    TextView pushupCount;
    @ViewById
    TextView waterCount;

    @RestService
    ICounterClient counterClient;

    Drawable buttonBackground;

    @AfterViews
    protected void initDataOnViews(){
        statViews.put(statNames[0], pushupCount);
        statViews.put(statNames[1], waterCount);

        populateCounts();
    }

    @Background
    void populateCounts(){
        buttonBackground = l1Pushup.getBackground();
        for(String name:statNames) {
            StatResult stat = counterClient.getResult(name);
            renderStat(stat);
        }
    }

    @UiThread
    void renderStat(StatResult stat){
        if(stat != null) {
            TextView view = statViews.get(stat.getName());
            if (view != null) {
                view.setText(String.valueOf(stat.getCount()));
            }
        }
    }

    @Click
    void plus_one_pushup(){
        Log.d("PUSHUP", "adding one pushup");
        setBackgroundColor(p1Pushup);
        sendCounterCall("pushup", "add", 1);
    }
    @Click
    void plus_five_pushup(){
        Log.d("PUSHUP", "adding five pushup");
        setBackgroundColor(p5pushup);
        sendCounterCall("pushup", "add", 5);
    }
    @Click
    void less_one_pushup(){
        Log.d("PUSHUP", "removing one pushup");
        setBackgroundColor(l1Pushup);
        sendCounterCall("pushup", "less", 1);
    }
    @Click
    void less_five_pushup(){
        Log.d("PUSHUP", "remvoing five pushup");
        setBackgroundColor(l5Pushup);
        sendCounterCall("pushup", "less", 5);
    }

    @Click
    void plus_one_water(){
        Log.d("WATER", "adding one water");
        setBackgroundColor(p1Water);
        sendCounterCall("water", "add", 1);
    }
    @Click
    void plus_five_water(){
        Log.d("WATER", "adding five water");
        setBackgroundColor(p5Water);
        sendCounterCall("water", "add", 5);
    }
    @Click
    void less_one_water(){
        Log.d("WATER", "removing one water");
        setBackgroundColor(l1Water);
        sendCounterCall("water", "less", 1);
    }
    @Click
    void less_five_water(){
        Log.d("WATER", "remvoing five water");
        setBackgroundColor(l5Water);
        sendCounterCall("water", "less", 5);
    }


    @Background
    void sendCounterCall(String name, String action, int count){
        StatResult stat = counterClient.sendCount(name, action, count);
        renderStat(stat);
        toastResult(stat);
    }

    @UiThread
    void toastResult(StatResult stat){
        Activity act = getActivity();
        if(act != null) {
            StringBuilder sb = new StringBuilder(stat.getName());
            sb.append(" now has ");
            sb.append(stat.getCount());
            sb.append(" counts!");

            String msg = sb.toString();

            Toast toast = Toast.makeText(act.getApplicationContext(), msg, Toast.LENGTH_SHORT);
            toast.show();
        }
    }


    private void setBackgroundColor(Button button){
        l1Pushup.setBackground(buttonBackground);
        l5Pushup.setBackground(buttonBackground);
        p1Pushup.setBackground(buttonBackground);
        p5pushup.setBackground(buttonBackground);

        l1Water.setBackground(buttonBackground);
        l5Water.setBackground(buttonBackground);
        p1Water.setBackground(buttonBackground);
        p5Water.setBackground(buttonBackground);

        button.setBackgroundColor(Color.BLUE);
    }
}
