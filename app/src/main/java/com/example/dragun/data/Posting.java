package com.example.dragun.data;

public class Posting extends Model {
    private int userId;
    private String featuredSkill, skillCategory, description,
                   estimatedLearningTime, contactOptions, skillLevel;

    public Posting(int userId, String featuredSkill, String skillCategory, String description, String estimatedLearningTime, String contactOptions, String skillLevel) {
        this.userId = userId;
        this.featuredSkill = featuredSkill;
        this.skillCategory = skillCategory;
        this.description = description;
        this.estimatedLearningTime = estimatedLearningTime;
        this.contactOptions = contactOptions;
        this.skillLevel = skillLevel;
    }

    @Override
    public String toString() {
        return "Posting{" +
                "userId=" + userId +
                ", featuredSkill='" + featuredSkill + '\'' +
                ", skillCategory='" + skillCategory + '\'' +
                ", description='" + description + '\'' +
                ", estimatedLearningTime='" + estimatedLearningTime + '\'' +
                ", contactOptions='" + contactOptions + '\'' +
                ", skillLevel='" + skillLevel + '\'' +
                ", id=" + id +
                '}';
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFeaturedSkill() {
        return featuredSkill;
    }

    public void setFeaturedSkill(String featuredSkill) {
        this.featuredSkill = featuredSkill;
    }

    public String getSkillCategory() {
        return skillCategory;
    }

    public void setSkillCategory(String skillCategory) {
        this.skillCategory = skillCategory;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEstimatedLearningTime() {
        return estimatedLearningTime;
    }

    public void setEstimatedLearningTime(String estimatedLearningTime) {
        this.estimatedLearningTime = estimatedLearningTime;
    }

    public String getContactOptions() {
        return contactOptions;
    }

    public void setContactOptions(String contactOptions) {
        this.contactOptions = contactOptions;
    }

    public String getSkillLevel() {
        return skillLevel;
    }

    public void setSkillLevel(String skillLevel) {
        this.skillLevel = skillLevel;
    }
}
