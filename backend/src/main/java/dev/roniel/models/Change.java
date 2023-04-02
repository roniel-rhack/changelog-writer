package dev.roniel.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import dev.roniel.utils.ChangeType;
import dev.roniel.utils.ChangeTypeDeserializer;
import lombok.Data;

@Data
public class Change {
    @JsonDeserialize(using = ChangeTypeDeserializer.class)
    private ChangeType type;
    private String description;
    private String usNumber;
}
