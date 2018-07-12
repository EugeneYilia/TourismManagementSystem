package com.EugeneStudio.pojo;

import java.util.Date;

public class Recorder {
    private Date date;
    private String id;//Cookie对应的Recorder的id
    private String privilege;//0-->管理员    1-->普通用户
    private int timeOutSeconds;//以秒为单位,默认60 * 10seconds(10minutes)

    public Recorder() {
    }

    public Recorder(Date date, String id, String privilege) {
        this.date = date;
        this.id = id;
        this.privilege = privilege;
        this.timeOutSeconds = 60 * 10;
    }

    public Recorder(Date date, String id, String privilege, int timeOutSeconds) {
        this.date = date;
        this.id = id;
        this.privilege = privilege;
        this.timeOutSeconds = timeOutSeconds;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPrivilege() {
        return privilege;
    }

    public void setPrivilege(String privilege) {
        this.privilege = privilege;
    }

    public int getTimeOutSeconds() {
        return timeOutSeconds;
    }

    public void setTimeOutSeconds(int timeOutSeconds) {
        this.timeOutSeconds = timeOutSeconds;
    }
}
