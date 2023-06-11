package com.example.project.Entity;

import jakarta.persistence.Entity;
import lombok.Data;

@Entity(name = "FEED")
@Data
public class FeedUsers {
    feed_number;
    feed_userID
    feed_appenddate
    user_profile
    feed_like
    feed_comment
    feed_image
    feed_content
}
