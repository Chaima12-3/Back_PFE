package org.acme.Model;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

import javax.enterprise.context.ApplicationScoped;
import java.util.HashMap;

@RegisterForReflection
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApplicationScoped
public class HistoriqueEnvoie {
    @BsonId
    private ObjectId id;
    private String name;
    private String mail;








}
