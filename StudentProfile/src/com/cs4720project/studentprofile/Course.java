//Team Pepper, {Amas, Larsen, Seid}, Phase 2

package com.cs4720project.studentprofile; //should change this

public class Course {

	public String courseID;
	public String courseName;
	public String sectionNum;
	public String courseInstructor;
	public String meetString;
	public String meetRoom;
	
	public Course(String courseID, String courseName, String sectionNum,
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
	
	public String getCourseID() {
		return courseID;
	}

	public void setCourseID(String courseID) {
		this.courseID = courseID;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getSectionNum() {
		return sectionNum;
	}

	public void setSectionNum(String sectionNum) {
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