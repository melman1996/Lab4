package com.halatek;

import java.time.LocalDate;
import java.util.Date;

public class Task {
    private String title;
    private Priority priority;
    private LocalDate expiration;
    private String description;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public void setExpiration(LocalDate expiration) {
        this.expiration = expiration;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Task(String title, Priority priority, LocalDate expiration, String description) {
        this.title = title;
        this.priority = priority;
        this.expiration = expiration;
        this.description = description;

    }

    public String getTitle() {
        return title;
    }

    public Priority getPriority() {
        return priority;
    }

    public LocalDate getExpiration() {
        return expiration;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return title;
    }
}
