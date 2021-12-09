package model;

public class UserBean {
    private String userid;
    private String userpassword;
    private String useremail;
    private String useremailhash;
    private boolean useremailhaschecked;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUserpassword() {
        return userpassword;
    }

    public void setUserpassword(String userpassword) {
        this.userpassword = userpassword;
    }

    public String getUseremail() {
        return useremail;
    }

    public void setUseremail(String useremail) {
        this.useremail = useremail;
    }

    public String getUseremailhash() {
        return useremailhash;
    }

    public void setUseremailhash(String useremailhash) {
        this.useremailhash = useremailhash;
    }

    public boolean getUseremailhaschecked() {
        return useremailhaschecked;
    }

    public void setUseremailhaschecked(boolean useremailhaschecked) {
        this.useremailhaschecked = useremailhaschecked;
    }
}
