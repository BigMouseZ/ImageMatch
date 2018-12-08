package com.signalway.highway.entity;

/**
 * Created by ZhangGang on 2017/5/16.
 */
public class MapQueryPojo {
    private Integer id;
    private Integer type;
    private Integer Xstart;
    private Integer Xend;
    private Integer Ystart;
    private Integer Yend;
    private Integer zoom;
    private String RoadCode;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getXstart() {
        return Xstart;
    }

    public void setXstart(Integer xstart) {
        Xstart = xstart;
    }

    public Integer getXend() {
        return Xend;
    }

    public void setXend(Integer xend) {
        Xend = xend;
    }

    public Integer getYstart() {
        return Ystart;
    }

    public void setYstart(Integer ystart) {
        Ystart = ystart;
    }

    public Integer getYend() {
        return Yend;
    }

    public void setYend(Integer yend) {
        Yend = yend;
    }

    public Integer getZoom() {
        return zoom;
    }

    public void setZoom(Integer zoom) {
        this.zoom = zoom;
    }

    public String getRoadCode() {
        return RoadCode;
    }

    public void setRoadCode(String roadCode) {
        RoadCode = roadCode;
    }

    @Override
    public String toString() {
        return "MapQueryPojo{" +
                "id=" + id +
                ", type=" + type +
                ", Xstart=" + Xstart +
                ", Xend=" + Xend +
                ", Ystart=" + Ystart +
                ", Yend=" + Yend +
                ", zoom=" + zoom +
                ", RoadCode='" + RoadCode + '\'' +
                '}';
    }
}
