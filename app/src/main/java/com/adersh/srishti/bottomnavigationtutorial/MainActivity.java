package com.adersh.srishti.bottomnavigationtutorial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.HashMap;
import java.util.Stack;

public class MainActivity extends AppCompatActivity{

    private HashMap<String, Stack<Fragment>> mStacks;
    public static final String TAB_HOME  = "tab_home";
    public static final String TAB_DASHBOARD  = "tab_dashboard";
    public static final String TAB_NOTIFICATIONS  = "tab_notifications";

    private String mCurrentTab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.btm_nav);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        mStacks = new HashMap<String, Stack<Fragment>>();
        mStacks.put(TAB_HOME, new Stack<Fragment>());
        mStacks.put(TAB_DASHBOARD, new Stack<Fragment>());
        mStacks.put(TAB_NOTIFICATIONS, new Stack<Fragment>());

        navigation.setSelectedItemId(R.id.btm_nav_home);

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.btm_nav_home:
                    selectedTab(TAB_HOME);
                    return true;
                case R.id.btm_nav_dashboard:
                    selectedTab(TAB_DASHBOARD);
                    return true;
                case R.id.btm_nav_Notification:
                    selectedTab(TAB_NOTIFICATIONS);
                    return true;
            }
            return false;
        }

    };

    private void gotoFragment(Fragment selectedFragment)
    {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, selectedFragment);
        fragmentTransaction.commit();
    }

    private void selectedTab(String tabId)
    {
        mCurrentTab = tabId;

        if(mStacks.get(tabId).size() == 0){
            /*
             *    First time this tab is selected. So add first fragment of that tab.
             *    Dont need animation, so that argument is false.
             *    We are adding a new fragment which is not present in stack. So add to stack is true.
             */
            if(tabId.equals(TAB_HOME)){
                pushFragments(tabId, new HomeFragment(),true);
            }else if(tabId.equals(TAB_DASHBOARD)){
                pushFragments(tabId, new DashBoadFragment(),true);
            }else if(tabId.equals(TAB_NOTIFICATIONS)){
                pushFragments(tabId, new NotificationFragment(),true);
            }
        }else {
            /*
             *    We are switching tabs, and target tab is already has atleast one fragment.
             *    No need of animation, no need of stack pushing. Just show the target fragment
             */
            pushFragments(tabId, mStacks.get(tabId).lastElement(),false);
        }
    }

    public void pushFragments(String tag, Fragment fragment, boolean shouldAdd){
        if(shouldAdd)
            mStacks.get(tag).push(fragment);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.replace(R.id.fragment_container, fragment);
        ft.commit();
    }

    public void popFragments(){
        /*
         *    Select the second last fragment in current tab's stack..
         *    which will be shown after the fragment transaction given below
         */
        Fragment fragment = mStacks.get(mCurrentTab).elementAt(mStacks.get(mCurrentTab).size() - 2);

        /*pop current fragment from stack.. */
        mStacks.get(mCurrentTab).pop();

        /* We have the target fragment in hand.. Just show it.. Show a standard navigation animation*/
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.replace(R.id.fragment_container, fragment);
        ft.commit();
    }

    @Override
    public void onBackPressed() {
        if(mStacks.get(mCurrentTab).size() == 1){
            // We are already showing first fragment of current tab, so when back pressed, we will finish this activity..
            finish();
            return;
        }

        /* Goto previous fragment in navigation stack of this tab */
        popFragments();
    }


}