package com.example.demo.Entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Set;

@Document(collection = "messages")
public class Message {

    @Id
    private String id;

    @Field
    private long value;

    @DBRef
    private Session session;

    @DBRef
    private Set<Discussion> discussionSet;

    // Default Constructor
    public Message() {}

    // Parameterized Constructor
    public Message(long value, Session session, Set<Discussion> discussionSet) {
        this.value = value;
        this.session = session;
        this.discussionSet = discussionSet;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public Set<Discussion> getDiscussionSet() {
        return discussionSet;
    }

    public void setDiscussionSet(Set<Discussion> discussionSet) {
        this.discussionSet = discussionSet;
    }
}
