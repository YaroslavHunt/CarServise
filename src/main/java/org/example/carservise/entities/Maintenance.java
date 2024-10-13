package org.example.carservise.entities;

import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.math.BigDecimal;

@Data
@Builder
@Document("maintenance")
public class Maintenance {

    @MongoId
    private ObjectId id;

    private String name;

    private String description;

    private BigDecimal price;

}
