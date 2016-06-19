package com.example.oleg.keepontrack.pojo;

import java.util.Date;
import java.util.Map;

/**
 * Created by andrew on 17.06.16.
 */

public class StatsResponse {

    private Map<Long, String> data;

    public Map<Long, String> getData() {
        return data;
    }

    public void setData(Map<Long, String> data) {
        this.data = data;
    }
}
