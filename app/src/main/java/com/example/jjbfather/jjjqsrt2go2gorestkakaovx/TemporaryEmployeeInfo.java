package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

/**
 * Created by JJBFATHER on 2015-10-22.
 */
// 인덱스 || 직원이름 || 나이 || 전화번호 || 이메일 || 사진 || 등록일시 || 윈도우포스코드 || 직원아이디 || 비밀버호 || 직원등급
public class TemporaryEmployeeInfo {
    public String empIdx, empName, empAge, empPhone, empEmail, empPhoto;
    public String empWdate, empPosCode, empEid, empPwd, empELevel;
    public String empCommissionratio;

    public TemporaryEmployeeInfo() {
        this.empIdx = "";
        this.empName = "";
        this.empAge = "";
        this.empPhone = "";
        this.empEmail = "";
        this.empPhoto = "";
        this.empWdate = "";
        this.empPosCode = "";
        this.empEid = "";
        this.empPwd = "";
        this.empELevel = "";
        this.empCommissionratio = "";
    }

    public TemporaryEmployeeInfo(String paramEmpIdx, String paramEmpName, String paramEmpAge, String paramEmpPhone,
                                 String paramEmpEmail, String paramEmpPhoto, String paramEmpWdate, String paramEmpPoscode,
                                 String paramEmpEid, String paramEmpPwd, String paramEmpELevel, String paramCommissionratio) {
        this.empIdx = paramEmpIdx;
        this.empName = paramEmpName;
        this.empAge = paramEmpAge;
        this.empPhone = paramEmpPhone;
        this.empEmail = paramEmpEmail;
        this.empPhoto = paramEmpPhoto;
        this.empWdate = paramEmpWdate;
        this.empPosCode = paramEmpPoscode;
        this.empEid = paramEmpEid;
        this.empPwd = paramEmpPwd;
        this.empELevel = paramEmpELevel;
        this.empCommissionratio = paramCommissionratio;
    }
}
