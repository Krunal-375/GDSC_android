package com.example.testnotice;

public class ProjectModel {
    private String headline, shortDescription, description, postedBy;
    private String featuredImage;

    public ProjectModel() {

    }

    public ProjectModel(String headline, String shortDescription, String description, String featuredImage, String postedBy) {
        this.headline = headline;
        this.shortDescription = shortDescription;
        this.description = description;
        this.featuredImage = featuredImage;
        this.postedBy = postedBy;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getFeaturedImage() {
        return featuredImage;
    }

    public void setFeaturedImage(String featuredImage) {
        this.featuredImage = featuredImage;
    }

    public String getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(String postedBy) {
        this.postedBy = postedBy;
    }
}
