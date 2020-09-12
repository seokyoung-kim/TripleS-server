package com.triples.project.dto;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;

import java.util.List;

@Getter
@Setter
public class CursorResult<T> {

    private List<T> values; // cursor based pagination list
    private boolean hasNext; // if there is data following the cursor
    private ObjectId cursorId; // cursor based pagination
    private int findCardsCount;  // result cards count

    public CursorResult(List<T> values, boolean hasNext, ObjectId cursorId, int findCardsCount) {
        this.values = values;
        this.hasNext = hasNext;
        this.cursorId = cursorId;
        this.findCardsCount = findCardsCount;
    }
}
