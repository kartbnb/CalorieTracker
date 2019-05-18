package com.example.calorietracker;

import java.util.Date;

public class DailyreportPK {
    private Date dateofreport;
    private int userid;

    public DailyreportPK(Date dateofreport, int userid) {
        this.dateofreport = dateofreport;
        this.userid = userid;
    }

    public Date getDateofreport() {
        return dateofreport;
    }

    public void setDateofreport(Date dateofreport) {
        this.dateofreport = dateofreport;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }
}
