package model;

public class BoardBean {

    private int bnum;
    private String title;
    private String userid;
    private String date;
    private String content;
    private int delcheck;

    public int getBnum() {
        return bnum;
    }

    public void setBnum(int bnum) {
        this.bnum = bnum;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }



    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getDelcheck() {
        return delcheck;
    }

    public void setDelcheck(int delcheck) {
        this.delcheck = delcheck;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
