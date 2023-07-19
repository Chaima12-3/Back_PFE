package org.model;

import org.apache.kafka.common.serialization.Deserializer;
import org.eclipse.yasson.internal.JsonBindingBuilder;

import javax.json.bind.JsonbBuilder;

public class EmailListDeserializer implements Deserializer<Message> {

    JsonbBuilder jsonbBuilder = new JsonBindingBuilder();
    @Override
    public Message deserialize(String topic, byte[] data) {

        if (data == null) {

            return null;

        }
        return jsonbBuilder.build().fromJson(new String(data), Message.class);
    }
}
