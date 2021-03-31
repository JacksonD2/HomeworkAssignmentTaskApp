package com.example.HomeworkAssignmentTaskApp.deprecated;

public class ClassObject {
    private String courseName;

    public ClassObject(String name){
        courseName = name;
    }

    public String getCourseName() {
        return courseName;
    }
}


/*public class ClassData implements Parcelable {
    private String courseName;

    public ClassData(String name){
        courseName = name;
    }

    protected ClassData(Parcel in) {
        courseName = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(courseName);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ClassData> CREATOR = new Creator<ClassData>() {
        @Override
        public ClassData createFromParcel(Parcel in) {
            return new ClassData(in);
        }

        @Override
        public ClassData[] newArray(int size) {
            return new ClassData[size];
        }
    };

    public String getCourseName() {
        return courseName;
    }
}*/