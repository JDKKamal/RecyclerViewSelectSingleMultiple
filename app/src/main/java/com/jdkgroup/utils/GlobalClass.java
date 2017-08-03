package com.jdkgroup.utils;

import java.util.ArrayList;
import java.util.List;

public class GlobalClass {
    public static GlobalClass instance;

    public List<Integer> alSelectedItemsStore = new ArrayList<>();

    public static GlobalClass getInstance() {

        if (instance == null) {
            instance = new GlobalClass();
        }
        return instance;
    }

}
