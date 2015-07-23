package com.hankyjcheng.mycontact;

/**
 * Created by Hank on 7/16/2015.
 */
public class Contact {


    private String name;
    private int employeeId;
    private String detailsURL;
    private String company;
    private String smallImageURL;
    private String birthdate;
    private String workPhone;
    private String homePhone;
    private String mobilePhone;

    public Contact(String name, int employeeId, String detailsURL, String company, String smallImageURL, String birthdate, String workPhone, String homePhone, String mobilePhone) {
        this.name = name;
        this.employeeId = employeeId;
        this.detailsURL = detailsURL;
        this.company = company;
        this.smallImageURL = smallImageURL;
        this.birthdate = birthdate;
        this.workPhone = workPhone;
        this.homePhone = homePhone;
        this.mobilePhone = mobilePhone;
    }

    public String getName() {
        return name;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public String getDetailsURL() {
        return detailsURL;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public String getCompany() {
        return company;
    }

    public String getSmallImageURL() {
        return smallImageURL;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public String getWorkPhone() {
        return workPhone;
    }
}
