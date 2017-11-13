package app.ray.wechatmements.ui.moments.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Ray on 2017/11/10.
 */

public class UserProfile implements MomentsItem {


    /**
     * profile-image : http://img2.findthebest.com/sites/default/files/688/media/images/Mingle_159902_i0.png
     * avatar : http://info.thoughtworks.com/rs/thoughtworks2/images/glyph_badge.png
     * nick : John Smith
     * username : jsmith
     */

    @SerializedName("profile-image")
    private String profileImage;
    private String avatar;
    private String nick;
    private String username;

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileimage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
