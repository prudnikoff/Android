package com.worlds.prudnikoff.cipher;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;


class SectionsPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> fragments;

    SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
        fragments = new ArrayList<>();
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new CipherFragment();
        Bundle args = new Bundle();
        args.putInt("position", position);
        fragment.setArguments(args);
        if (fragments.size() == 3) {
            fragments.set(position, fragment);
        } else fragments.add(position, fragment);
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Thread";
            case 1:
                return "Geffe";
            case 2:
                return "RC4";
        }
        return null;
    }

    Fragment getFragment(int position) {
        return fragments.get(position);
    }
}
