package com.project.fanyuzeng.niubilityvideoplayer;

import com.project.fanyuzeng.niubilityvideoplayer.fragment.BaseFragment;

import java.util.HashMap;

/**
 * Created by fanyuzeng on 2017/9/22.
 * Function:
 */
public class FragmentManagerWrapper {
    private static volatile FragmentManagerWrapper mInstance = null;

    private FragmentManagerWrapper() {
    }

    public static FragmentManagerWrapper getInstance() {
        if (mInstance == null) {
            synchronized (FragmentManagerWrapper.class) {
                if (mInstance == null) {
                    mInstance = new FragmentManagerWrapper();
                }
            }
        }
        return mInstance;
    }

    private HashMap<String, BaseFragment> mHashMap = new HashMap<>();

    public BaseFragment createFragment(Class<?> clazz) {
        BaseFragment resultFragment = null;
        String className = clazz.getName();
        if (mHashMap.containsKey(className)) {
            resultFragment = mHashMap.get(className);
        } else {
            try {
                resultFragment = (BaseFragment) Class.forName(className).newInstance();
                if (resultFragment != null) {
                    mHashMap.put(className, resultFragment);
                }
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
        return resultFragment;
    }
}


