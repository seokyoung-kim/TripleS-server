package com.triples.project.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CursorResult<T> {

    private List<T> values; // cursor based pagination list
    private Cursor cursors;

    public CursorResult(List<T> values, Cursor cursors) {
        this.values = values;
        this.cursors = cursors;
    }
}
