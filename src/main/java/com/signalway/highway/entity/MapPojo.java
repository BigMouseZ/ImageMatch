package com.signalway.highway.entity;

/**
 * Created by ZhangGang on 2017/5/16.
 */
public class MapPojo {
    private Integer type;
    private Integer zoom;
    private Integer x;
    private Integer y;
    private byte[] tile;

    public byte[] getTile() {
        return tile;
    }

    public void setTile(byte[] tile) {
        this.tile = tile;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getZoom() {
        return zoom;
    }

    public void setZoom(Integer zoom) {
        this.zoom = zoom;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

}
