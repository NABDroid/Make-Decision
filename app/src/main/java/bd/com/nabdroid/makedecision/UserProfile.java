package bd.com.nabdroid.makedecision;

public class UserProfile {
    String userName, mobileNumber, email, dateOfBirth, gender ,password;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserProfile() {
    }

    public UserProfile(String userName, String mobileNumber, String email, String dateOfBirth, String gender, String password) {
        this.userName = userName;
        this.mobileNumber = mobileNumber;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.password = password;
    }

}
