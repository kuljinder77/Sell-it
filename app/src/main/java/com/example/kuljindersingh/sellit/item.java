package com.example.kuljindersingh.sellit;

public class item {

    private String name;
    private String type;
    private String url;
    private String description;
    private String contact;



    private String uid;

    public item() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContact() {
        return contact;
    }
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public item(String name, String type, String url, String description, String contact , String uid) {
        this.name = name;
        this.type = type;
        this.url = url;
        this.description = description;
        this.uid = uid;

        this.contact = contact;
    }
}
