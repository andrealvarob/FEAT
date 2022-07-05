package pit.feat;

import com.facebook.Profile;

/**
 * Created by Usuário on 18/05/2016.
 */
public class User {


    Profile user_profile;
    String username;
    String user_profile_id;



    private static User ourInstance = new User();

    public static User getInstance() {
        return ourInstance;
    }

    private User() {

    }

    public void setUser_profile(Profile user_profile) {

        this.user_profile = user_profile;
        this.username = this.user_profile.getName();
        this.user_profile_id = this.user_profile.getId();

    }

    public Profile getUser_profile() {
        return user_profile;
    }

    public void setUser_profile_id(String id){
        this.user_profile_id = this.user_profile.getId();
    }

    public String getUser_profile_id(){
        return this.user_profile.getId();
    }

}



