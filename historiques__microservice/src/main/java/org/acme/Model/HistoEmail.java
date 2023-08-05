package org.acme.Model;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

import javax.enterprise.context.ApplicationScoped;

@RegisterForReflection
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApplicationScoped
public class HistoEmail {

    @BsonId
    private ObjectId id;
    private String UserName;
    private String UserEmail;
    private Long count;
    private Long countP;
    private Long countB;
    private Long countBP;
}
