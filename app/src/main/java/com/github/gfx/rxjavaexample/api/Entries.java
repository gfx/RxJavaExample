package com.github.gfx.rxjavaexample.api;

import java.util.List;

public class Entries {

    public final int index;

    public final int maxCount;

    public final List<String> entryIds;

    public Entries(int index, int maxCount, List<String> entryIds) {
        this.index = index;
        this.maxCount = maxCount;
        this.entryIds = entryIds;
    }
}
