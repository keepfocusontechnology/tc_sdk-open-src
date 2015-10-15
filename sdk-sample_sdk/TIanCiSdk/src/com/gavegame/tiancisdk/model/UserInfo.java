package com.gavegame.tiancisdk.model;

/**
 * Created by Tianci on 15/9/18.
 */
public class UserInfo {

    private int gameId;
    private int serverId;
    private int channelId;
    private int deviceType;
    private String userLogin;
    private String userPsw;
    private String deviceId;
    private String userAgent;


    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public void setGameId(String gameId) {
        try {
            this.gameId = Integer.valueOf(gameId);
        } catch (Exception e) {
            System.err.print("beanStirng转integer出错");
            e.printStackTrace();
        }
    }


    public int getServerId() {
        return serverId;
    }

    public void setServerId(int serverId) {
        this.serverId = serverId;
    }

    public void setServerId(String serverId) {
        try {
            this.serverId = Integer.valueOf(serverId);
        } catch (Exception e) {
            System.err.print("beanStirng转integer出错");
            e.printStackTrace();
        }
    }

    public int getChannelId() {
        return channelId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    public void setChannelId(String channelId) {
        try {
            this.channelId = Integer.valueOf(channelId);
        } catch (Exception e) {
            System.err.print("beanStirng转integer出错");
            e.printStackTrace();
        }
    }

    public int getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(int deviceType) {
        this.deviceType = deviceType;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public String getUserPsw() {
        return userPsw;
    }

    public void setUserPsw(String userPsw) {
        this.userPsw = userPsw;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "gameId=" + gameId +
                ", serverId=" + serverId +
                ", channelId=" + channelId +
                ", deviceType=" + deviceType +
                ", userLogin='" + userLogin + '\'' +
                ", userPsw='" + userPsw + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", userAgent='" + userAgent + '\'' +
                '}';
    }
}
