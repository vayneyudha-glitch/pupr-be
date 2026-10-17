package com.pupr.client.dto;

/**
 * Request payload for creating or updating a data record in PUPR-CRS.
 */
public class CrsDataRequest {

    private String name;
    private String description;
    private String type;
    private String status;

    public CrsDataRequest() {
    }

    public CrsDataRequest(String name, String description, String type, String status) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "CrsDataRequest{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
