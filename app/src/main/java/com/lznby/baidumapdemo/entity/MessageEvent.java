package com.lznby.baidumapdemo.entity;

import java.util.List;

/**
 * EventBus
 */
public class MessageEvent {
    private List<Hydrant> mHydrantList;

    public MessageEvent(List<Hydrant> hydrantList) {
        mHydrantList = hydrantList;
    }
    public MessageEvent() {

    }

    public List<Hydrant> getHydrantList() {
        return mHydrantList;
    }
}
