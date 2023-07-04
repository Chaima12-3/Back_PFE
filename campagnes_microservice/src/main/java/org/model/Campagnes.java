package org.model;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

import java.util.Date;

@RegisterForReflection
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Campagnes {
    @BsonId
    private ObjectId id;
    private String channelsNames;
    private String description;
    private String date;
    private String communicationChannels;
    private User user;
    private String url;
}
