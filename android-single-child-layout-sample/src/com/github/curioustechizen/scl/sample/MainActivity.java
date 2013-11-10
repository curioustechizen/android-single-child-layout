
package com.github.curioustechizen.scl.sample;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.github.curioustechizen.scl.SingleChildLayout;

public class MainActivity extends Activity {
    
    private SingleChildLayout mSingleChildLayout;
    int currentlyShowing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSingleChildLayout = (SingleChildLayout)findViewById(R.id.SingleChildLayout1);
        currentlyShowing = mSingleChildLayout.getShownChildIndex();
    }

   public void onButtonClick(View v){
       if(v.getId() == R.id.button1){
           changeToNextState();
       }
   }

   private void changeToNextState(){
       currentlyShowing = (currentlyShowing + 1) % mSingleChildLayout.getChildCount();
       mSingleChildLayout.showChildAt(currentlyShowing);
   }
}
