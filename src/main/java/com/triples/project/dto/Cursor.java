package com.triples.project.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Cursor {

    private String previous;      // previous cursor Id
    private boolean hasPrevious;  // whether the prev cursor exists
    private String next;          // next cursor id
    private boolean hasNext;      // whether the next cursor exists

    @Builder
    public Cursor(String previous, boolean hasPrevious, String next, boolean hasNext) {
        this.previous = previous;
        this.hasPrevious = hasPrevious;
        this.next = next;
        this.hasNext = hasNext;
    }
}
