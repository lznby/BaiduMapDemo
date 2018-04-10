package com.lznby.baidumapdemo.json;

/**
 * 消防栓基本信息实体类
 */
public class Hydrant {
    private long hydrant_id;
    private long area_id;
    private long node_id;
    private String address;
    private String checkpoint_type;
    private String checkpoint_phone;
    private double pressure;
    private double longitude;
    private double latitude;
    private int status;
    private String principal_name;
    private String principal_phone;
    private String fire_control;
    private String fire_control_phone;
    private String description;
    private String img_url;
    private String time;

    public long getHydrant_id() {
        return hydrant_id;
    }

    public void setHydrant_id(long hydrant_id) {
        this.hydrant_id = hydrant_id;
    }

    public long getArea_id() {
        return area_id;
    }

    public void setArea_id(long area_id) {
        this.area_id = area_id;
    }

    public long getNode_id() {
        return node_id;
    }

    public void setNode_id(long node_id) {
        this.node_id = node_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCheckpoint_type() {
        return checkpoint_type;
    }

    public void setCheckpoint_type(String checkpoint_type) {
        this.checkpoint_type = checkpoint_type;
    }

    public String getCheckpoint_phone() {
        return checkpoint_phone;
    }

    public void setCheckpoint_phone(String checkpoint_phone) {
        this.checkpoint_phone = checkpoint_phone;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getPrincipal_name() {
        return principal_name;
    }

    public void setPrincipal_name(String principal_name) {
        this.principal_name = principal_name;
    }

    public String getPrincipal_phone() {
        return principal_phone;
    }

    public void setPrincipal_phone(String principal_phone) {
        this.principal_phone = principal_phone;
    }

    public String getFire_control() {
        return fire_control;
    }

    public void setFire_control(String fire_control) {
        this.fire_control = fire_control;
    }

    public String getFire_control_phone() {
        return fire_control_phone;
    }

    public void setFire_control_phone(String fire_control_phone) {
        this.fire_control_phone = fire_control_phone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
