package org.model;
import org.apache.kafka.common.serialization.Serializer;
import org.eclipse.yasson.internal.JsonBindingBuilder;

import javax.json.bind.JsonbBuilder;

public class EmailSerializer implements Serializer<B2BClients>{
    JsonbBuilder jsonbBuilder = new JsonBindingBuilder();

    @Override

    public byte[] serialize(String topic, B2BClients data){

        if (data == null)

            return new byte[0];

        return jsonbBuilder.build().toJson(data,B2BClients.class).getBytes();

    }

}


