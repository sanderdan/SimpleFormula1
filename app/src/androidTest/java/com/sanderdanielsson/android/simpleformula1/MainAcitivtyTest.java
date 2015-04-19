package com.sanderdanielsson.android.simpleformula1;

import android.support.v4.view.ViewPager;
import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by Sander on 2015-04-19.
 */
class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {
   public MainActivity testActivity;

   private ViewPager viewPager;

    public MainActivityTest(){
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        testActivity = getActivity();
        viewPager = (ViewPager) testActivity.findViewById(R.id.pager);
    }

    public void testViewPager(){
        assertNotNull("viewPager = null", viewPager);
    }
}
