package com.example.dragun.data;

public class Request extends Model {
    private int mentorId;
    private String username, studentEmail, sessionMode, skillCategory, featuredSkill;

    public Request(int mentorId, String username, String studentEmail, String sessionMode, String skillCategory, String featuredSkill) {
        this.mentorId = mentorId;
        this.username = username;
        this.studentEmail = studentEmail;
        this.sessionMode = sessionMode;
        this.skillCategory = skillCategory;
        this.featuredSkill = featuredSkill;
    }

    @Override
    public String toString() {
        return "Request{" +
                "mentorId=" + mentorId +
                ", username='" + username + '\'' +
                ", sessionMode='" + sessionMode + '\'' +
                ", skillCategory='" + skillCategory + '\'' +
                ", featuredSkill='" + featuredSkill + '\'' +
                ", id=" + id +
                '}';
    }

    public int getMentorId() {
        return mentorId;
    }

    public void setMentorId(int mentorId) {
        this.mentorId = mentorId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSessionMode() {
        return sessionMode;
    }

    public void setSessionMode(String sessionMode) {
        this.sessionMode = sessionMode;
    }

    public String getSkillCategory() {
        return skillCategory;
    }

    public void setSkillCategory(String skillCategory) {
        this.skillCategory = skillCategory;
    }

    public String getFeaturedSkill() {
        return featuredSkill;
    }

    public void setFeaturedSkill(String featuredSkill) {
        this.featuredSkill = featuredSkill;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }
}
