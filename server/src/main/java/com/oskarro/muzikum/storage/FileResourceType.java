package com.oskarro.muzikum.storage;

public enum FileResourceType {
    COVER_IMAGE("coverImage"),
    ARTICLE_IMAGE("articleImage"),
    USER_IMAGE("userImage"),
    IMAGE("image");

    private final String name;

    FileResourceType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
