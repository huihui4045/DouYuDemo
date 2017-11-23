package com.team.zhuoke.model.logic.video.bean;

import com.google.gson.annotations.SerializedName;

/**
 * 作者：${User}
 * 电话：18810474975
 * 邮箱：18810474975@163.com
 * 版本号：
 * 类描述：
 * 修改时间：${DATA}1611
 */

public class VideoStramInfo {

    /**
     * thumb_video : {"normal":"http://vodhls1.douyucdn.cn/live/normal_live-535534rutfbwudz7--20170531223237/playlist.m3u8?k=313949c69a31f8768655c7c1bc9c0a1f&t=592fc8af&u=0&ct=android&vid=738456&d=1959fc3b-56dc-46c0-803c-64fbe5b58c04","super":"","high":""}
     * timestamp : 7200
     */

    private ThumbVideoEntity thumb_video;
    private int timestamp;

    public ThumbVideoEntity getThumb_video() {
        return thumb_video;
    }

    public void setThumb_video(ThumbVideoEntity thumb_video) {
        this.thumb_video = thumb_video;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public static class ThumbVideoEntity {
        /**
         * normal : http://vodhls1.douyucdn.cn/live/normal_live-535534rutfbwudz7--20170531223237/playlist.m3u8?k=313949c69a31f8768655c7c1bc9c0a1f&t=592fc8af&u=0&ct=android&vid=738456&d=1959fc3b-56dc-46c0-803c-64fbe5b58c04
         * super :
         * high :
         */

        private String normal;
        @SerializedName("super")
        private String superX;
        private String high;

        public String getNormal() {
            return normal;
        }

        public void setNormal(String normal) {
            this.normal = normal;
        }

        public String getSuperX() {
            return superX;
        }

        public void setSuperX(String superX) {
            this.superX = superX;
        }

        public String getHigh() {
            return high;
        }

        public void setHigh(String high) {
            this.high = high;
        }
    }
}
