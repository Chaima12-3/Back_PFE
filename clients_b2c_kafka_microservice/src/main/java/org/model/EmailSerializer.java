package org.model;


import org.apache.kafka.common.serialization.Serializer;
import org.eclipse.yasson.internal.JsonBindingBuilder;
import javax.json.bind.JsonbBuilder;

public class EmailSerializer implements Serializer<Email> {

    JsonbBuilder jsonbBuilder = new JsonBindingBuilder();

    @Override

    public byte[] serialize(String topic, Email data){

        if (data == null)

            return new byte[0];

        return jsonbBuilder.build().toJson(data, Email.class).getBytes();

    }


}

