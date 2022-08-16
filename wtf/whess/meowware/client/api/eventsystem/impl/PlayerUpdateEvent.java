package wtf.whess.meowware.client.api.eventsystem.impl;

import wtf.whess.meowware.client.api.eventsystem.Event;

public final class PlayerUpdateEvent extends Event {
    private boolean isPre;
    private float yaw;
    private float pitch;
    private double x,y,z;
    private boolean onground;

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public PlayerUpdateEvent(double y, float yaw, float pitch, boolean ground) {
        this.isPre = true;
        this.yaw = yaw;
        this.pitch = pitch;
        this.y = y;
        this.onground = ground;
    }

    public PlayerUpdateEvent() {
        this.isPre = false;
    }

    public boolean isPre() {
        return isPre;
    }

    public boolean isPost() {
        return !isPre;
    }

    public float getYaw() {
        return this.yaw;
    }

    public float getPitch() {
        return this.pitch;
    }

    public double getY() {
        return this.y;
    }

    public boolean isOnground() {
        return this.onground;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setGround(boolean ground) {
        this.onground = ground;
    }
}
