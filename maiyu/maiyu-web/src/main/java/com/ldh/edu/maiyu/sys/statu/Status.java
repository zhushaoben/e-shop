package com.ldh.edu.maiyu.sys.statu;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Status {

    private boolean result;
    private int status=200;
    private String message;
    private Long timestamp = (new Date()).getTime();
    private String path;
    private Map<String,Object> data = new HashMap<>();
    private Object extra;

    public Status(boolean result, int status, String message, Long timestamp, String path, Map<String, Object> data, Object extra) {
        this.result = result;
        this.status = status;
        this.message = message;
        this.timestamp = timestamp;
        this.path = path;
        this.data = data;
        this.extra = extra;
    }
    public Status(){}

    public Map<String, Object> map() {
        Map<String, Object> statusMap = new LinkedHashMap();
        statusMap.put("result", this.getResult());
        statusMap.put("status", this.getStatus());
        statusMap.put("message", this.getMessage());
        statusMap.put("timestamp", this.getTimestamp());
        if (this.getPath() != null) {
            statusMap.put("path", this.getPath());
        }

        if (this.getData() != null) {
            statusMap.put("data", this.getData());
        }

        if (this.getExtra() != null) {
            statusMap.put("extra", this.getExtra());
        }

        return statusMap;
    }

    public static Status builder(){
        return new Status();
    }

    public Status addResult(boolean result) {
        this.result = result;
        return this;
    }

    public Status addStatus(int status) {
        this.status = status;
        return this;
    }

    public Status addMessage(String message) {
        this.message = message;
        return this;
    }

    public Status addPath(String path) {
        this.path = path;
        return this;
    }

    public Status addData(String key, Object data) {
        this.data.put(key, data);
        return this;
    }

    public Status addDataValue(Object data) {
        return this.addData("value", data);
    }

    public Status addDataCount(Long count) {
        return this.addData("count", count);
    }

    public Status addExtra(Object extra) {
        this.extra = extra;
        return this;
    }


    public boolean getResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public Object getExtra() {
        return extra;
    }

    public void setExtra(Object extra) {
        this.extra = extra;
    }
}
