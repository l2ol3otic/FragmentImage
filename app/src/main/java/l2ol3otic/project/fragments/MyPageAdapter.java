package l2ol3otic.project.fragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by l2ol3otic2 on 6/25/2015.
 */
public class MyPageAdapter extends FragmentPagerAdapter {

    private final int NUM_ITEMS = 3;

    public MyPageAdapter(FragmentManager fm ) {

        super(fm);
    }

    @Override
    public int getCount() {

        return NUM_ITEMS;
    }
    public Fragment getItem(int position) {
        if(position == 0)
            return new OneFragment();
        else if(position == 1)
          return new TwoFragment();
        else if(position == 2)
            return new ThreeFragment();
        return null;
    }

}
