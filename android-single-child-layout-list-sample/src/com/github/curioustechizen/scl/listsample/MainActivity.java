
package com.github.curioustechizen.scl.listsample;

import java.util.List;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.github.curioustechizen.scl.SingleChildLayout;

public class MainActivity extends Activity {
    
    private SingleChildLayout mRootContainer;
    private BroadcastReceiver mScanResultsReceiver;
    private IntentFilter mScanFilter;
    private ListView mListView;
    private Handler mHandler = new Handler();
    private WifiManager mWifiManager;
    private Menu mMenu;
    private ArrayAdapter<String> mAdapter;
    private Runnable mTimeoutRunnable = new Runnable() {
        
        @Override
        public void run() {
            onWifiScanTimeout();
        }
    };
    
    private static final long WIFI_SCAN_TIMEOUT = 10 * 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_main);
        mRootContainer = (SingleChildLayout) findViewById(R.id.container_root);
        mListView = (ListView) mRootContainer.findViewById(android.R.id.list);
        
        mWifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1);
        mListView.setAdapter(mAdapter);
        mScanResultsReceiver = new BroadcastReceiver() {
            
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION.equals(action)){
                    onScanResultsAvailable();
                }
            }
        };
        mScanFilter = new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        mMenu = menu;
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_refresh){
            refreshAccessPoints();
            item.setVisible(false);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    private void onScanResultsAvailable(){
        clearRefreshingState();
        List<ScanResult> scanResults = mWifiManager.getScanResults();
        mAdapter.clear();
        if(scanResults != null){
            for(ScanResult result: scanResults){
                mAdapter.add(result.SSID);
            }
        }
        mAdapter.notifyDataSetChanged();
        
        if(mAdapter.isEmpty()){
            showEmptyView();
        } else {
            showListContent();
        }
        
    }

    private void onWifiScanTimeout(){
        clearRefreshingState();
        mAdapter.clear();
        showEmptyView();
    }
    
    private void clearRefreshingState(){
        mMenu.findItem(R.id.action_refresh).setVisible(true);
        setProgressBarIndeterminateVisibility(false);
        unregisterReceiver(mScanResultsReceiver);
        mHandler.removeCallbacks(mTimeoutRunnable);
    }
    
    private void refreshAccessPoints(){
        setProgressBarIndeterminateVisibility(true);
        mRootContainer.showChildAt(2);
        mHandler.postDelayed(mTimeoutRunnable, WIFI_SCAN_TIMEOUT);
        registerReceiver(mScanResultsReceiver, mScanFilter);
        mWifiManager.startScan();
    }
    
    private void showEmptyView(){
        mRootContainer.showChildAt(1);
    }
    
    private void showListContent(){
        mRootContainer.showChildAt(0);
    }
}
