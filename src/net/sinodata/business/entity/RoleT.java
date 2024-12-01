package net.sinodata.business.entity;

public class RoleT {
    private String roleid;

    private String rolename;

    private String menuids;

    private String roledescription;

    public String getRoleid() {
        return roleid;
    }

    public void setRoleid(String roleid) {
        this.roleid = roleid;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    public String getMenuids() {
        return menuids;
    }

    public void setMenuids(String menuids) {
        this.menuids = menuids;
    }

    public String getRoledescription() {
        return roledescription;
    }

    public void setRoledescription(String roledescription) {
        this.roledescription = roledescription;
    }
}