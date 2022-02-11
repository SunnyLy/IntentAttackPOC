package com.sunny.poc;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @Annotation <p>描述</p>
 * @Auth Sunny
 * @date 2022/2/11
 * @Version V1.0.0
 */
public class SunnyParcelableObj implements Parcelable {
    private String userName;
    private String address;
    private int age;

    protected SunnyParcelableObj(String userName, String address, int age) {
        this.userName = userName;
        this.address = address;
        this.age = age;
    }

    public static final Creator<SunnyParcelableObj> CREATOR = new Creator<SunnyParcelableObj>() {
        @Override
        public SunnyParcelableObj createFromParcel(Parcel in) {
            return new SunnyParcelableObj(in);
        }

        @Override
        public SunnyParcelableObj[] newArray(int size) {
            return new SunnyParcelableObj[size];
        }
    };

    public SunnyParcelableObj(Parcel in) {
        this.userName = in.readString();
        this.address = in.readString();
        this.age = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userName);
        dest.writeString(address);
        dest.writeInt(age);
    }
}
