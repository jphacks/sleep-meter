package com.sierens.sleepmeter;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class Home extends Activity implements ValueAnimator.AnimatorUpdateListener {
    public static float HOUR_THRESHOLD = 7f;

    EditText sleptHours;
    TextView resultText,gapView;
    LinearLayout homeLayout,answerLayout;

    private boolean isExpandedResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);

        resultText = (TextView) findViewById(R.id.result_text);
        answerLayout = (LinearLayout) findViewById(R.id.answer_layout);
        homeLayout = (LinearLayout) findViewById(R.id.home_layout);

        //when initializing, the gap view is expanded
        isExpandedResult=false;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button_selector, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Called when you click on the button_selector.
     */
    public void getSleepAdvice(View v){
        sleptHours = (EditText) findViewById(R.id.slept_hours);

        //validate, no need to validate for numbers, as android:inputType="number" is used on
        //the xml definition of the edittext.
        if (isEmpty(sleptHours)){
            Toast.makeText(this,"Please fill in the hours you slept man...",Toast.LENGTH_SHORT).show();
            return;
        }

        //get the hours
        String strHours = sleptHours.getText().toString();
        float hours = Float.parseFloat(strHours);

        //animate the answer
        expandResult();

        //logic
        if (hours < HOUR_THRESHOLD) {
            resultText.setText(R.string.shud);
            answerLayout.setBackgroundColor(Color.parseColor("#ffc9ff82"));
        }else{
            resultText.setText(R.string.shudnt);
            answerLayout.setBackgroundColor(Color.parseColor("#ffffd982"));
        }
    }

    /**
     * expand the result.
     */
    private void expandResult() {
        if (!isExpandedResult) {
            ObjectAnimator anim = ObjectAnimator.ofFloat(homeLayout, "weightSum", 400f);
            anim.setDuration(500);
            anim.addUpdateListener(this);
            anim.start();
            isExpandedResult = true;
        }
    }

    /**
     * Contract the result.
     */
    private void contractResult() {
        if (isExpandedResult) {
            ObjectAnimator anim = ObjectAnimator.ofFloat(homeLayout, "weightSum", 100f);
            anim.setDuration(500);
            anim.addUpdateListener(this);
            anim.start();
            isExpandedResult = false;
        }
    }

    /**
     * Check if an edittext field is empty.
     * @param eText
     * @return
     */
    private boolean isEmpty(EditText eText) {
        return eText.getText().toString().trim().length() == 0;
    }

    /**
     * contract the result if it's expanded, otherwise close app.
     */
    public void onBackPressed() {
        if (isExpandedResult) contractResult();
        else finish();
    }

    /**
     * Listener of the animation.
     * @param animation
     */
    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        homeLayout.requestLayout();
    }
}

















