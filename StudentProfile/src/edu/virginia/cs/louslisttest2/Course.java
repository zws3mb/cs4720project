//Team Pepper, {Amas, Larsen, Seid}, Phase 2

package edu.virginia.cs.louslisttest2; //should change this

public class Course {

	public int courseID;
	public String courseName;
	public int sectionNum;
	public String courseInstructor;
	public String meetString;
	public String meetRoom;

	public Course(int courseID, String courseName, int sectionNum,
			String courseInstructor, String meetString, String meetRoom) {
		super();
		this.courseID = courseID;
		this.courseName = courseName;
		this.sectionNum = sectionNum;
		this.courseInstructor = courseInstructor;
		this.meetString = meetString;
		this.meetRoom = meetRoom;
	}

	@Override
	public String toString() {
		return "Course [courseID=" + courseID + ", courseName=" + courseName
				+ ", sectionNum=" + sectionNum + ", courseInstructor=" + courseInstructor
				+ ", meetString=" + meetString + ", meetRoom=" + meetRoom
				+ "]";
	}

	public int getCourseID() {
		return courseID;
	}

	public void setCourseID(int courseID) {
		this.courseID = courseID;
	}
	
	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public int getSectionNum() {
		return sectionNum;
	}

	public void setSectionNum(int sectionNum) {
		this.sectionNum = sectionNum;
	}

	public String getCourseInstructor() {
		return courseInstructor;
	}

	public void setCourseInstructor(String courseInstructor) {
		this.courseInstructor = courseInstructor;
	}

	public String getMeetString() {
		return meetString;
	}

	public void setMeetString(String meetString) {
		this.meetString = meetString;
	}

	public String getMeetRoom() {
		return meetRoom;
	}

	public void setMeetRoom(String meetRoom) {
		this.meetRoom = meetRoom;
	}
	

}