package org.model;

import org.apache.kafka.common.serialization.Deserializer;
import org.eclipse.yasson.internal.JsonBindingBuilder;

import javax.json.bind.JsonbBuilder;

public class EmailDeserializer implements Deserializer<B2BClients> {

    JsonbBuilder jsonbBuilder = new JsonBindingBuilder();
    @Override
    public B2BClients deserialize(String topic, byte[] data) {

        if (data == null) {

            return null;

        }

        return jsonbBuilder.build().fromJson(new String(data), B2BClients.class);

    }


}

