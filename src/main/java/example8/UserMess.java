package example8;

import java.util.List;

public class UserMess {
    public final String mFullName;
    public final Address mAddress;
    public final PhoneNumber mPhoneNumber;

    public UserMess(String fullName, Address address, PhoneNumber phoneNumber) {
        mFullName = fullName;
        mAddress = address;
        mPhoneNumber = phoneNumber;
    }

    public void logOut() {
        // code
    }

    public void connectWith(UserMess otherUser) {
        // code
    }

    public List<UserMess> getConnectedUsers() {
        // code
        return null;
    }

    public void disconnectFromAll() {
        // code
    }

    public String getFullName() {
        return mFullName;
    }

    public Address getAddress() {
        return mAddress;
    }

    public PhoneNumber getPhoneNumber() {
        return  mPhoneNumber;
    }
}
