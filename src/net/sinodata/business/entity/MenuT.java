package net.sinodata.business.entity;

public class MenuT {
    private String menuid;

    private String menuname;

    private String menupath;

    private String parentid;

    private String menudescription;

    private String state;

    private String iconcls;

    public String getMenuid() {
        return menuid;
    }

    public void setMenuid(String menuid) {
        this.menuid = menuid;
    }

    public String getMenuname() {
        return menuname;
    }

    public void setMenuname(String menuname) {
        this.menuname = menuname;
    }

    public String getMenupath() {
        return menupath;
    }

    public void setMenupath(String menupath) {
        this.menupath = menupath;
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    public String getMenudescription() {
        return menudescription;
    }

    public void setMenudescription(String menudescription) {
        this.menudescription = menudescription;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getIconcls() {
        return iconcls;
    }

    public void setIconcls(String iconcls) {
        this.iconcls = iconcls;
    }
}