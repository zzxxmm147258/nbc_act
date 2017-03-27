package com.nbcedu.user.model;

import com.nbcedu.bas.model.ModelBas;

public class AdminUser extends ModelBas{

    /**
	 * 
	 */
	private static final long serialVersionUID = -4087961548960751618L;

	private String userName;

    private String password;

    private String trueName;

    private String delStatus="0"; // 0：未删除，1：已删除

    private String status="0";//默认（其他状态赞不设计）


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getTrueName() {
        return trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName == null ? null : trueName.trim();
    }

    public String getDelStatus() {
        return delStatus;
    }

    public void setDelStatus(String delStatus) {
        this.delStatus = delStatus == null ? null : delStatus.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }
}