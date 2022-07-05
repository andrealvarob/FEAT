package pit.feat.models;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Usuário on 29/05/2016.
 */
@IgnoreExtraProperties
public class User_model {

    public String id;
    public String name;
    public String facebook_profile_id;
    public int age;

    public String gender;

    public String gender_preference;

    public User_model(){

    }

    public User_model(String _id, String _name, int _age, String _gender, String _gender_preference){
        this.id = _id;
        this.name = _name;

        this.age = _age;
        this.gender = _gender;
        this.gender_preference = _gender_preference;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getGender_preference() {
        return gender_preference;
    }

    public void setGender_preference(String gender_preference) {
        this.gender_preference = gender_preference;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFacebook_profile_id() {
        return facebook_profile_id;
    }

    public void setFacebook_profile_id(String facebook_profile_id) {
        this.facebook_profile_id = facebook_profile_id;
    }
}
