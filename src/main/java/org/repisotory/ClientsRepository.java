package org.repisotory;

import io.quarkus.mongodb.panache.PanacheMongoRepository;
import io.quarkus.panache.common.Sort;
import org.model.B2C;
import org.model.Clients;
import org.model.Enum.Behavior;
import org.model.Enum.Size;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class ClientsRepository implements PanacheMongoRepository<Clients> {

    public List<Clients> sortedAscending(List<String> value) {
        return listAll(Sort.by(value.toString()));
    }
    public List<Clients> sortedDescending(String value) {
        return listAll(Sort.by(value).descending());
    }
}
