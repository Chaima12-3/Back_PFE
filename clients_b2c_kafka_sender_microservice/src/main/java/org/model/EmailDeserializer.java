package org.model;

import org.apache.kafka.common.serialization.Deserializer;
import org.eclipse.yasson.internal.JsonBindingBuilder;
import javax.json.bind.JsonbBuilder;

public class EmailDeserializer implements Deserializer<Emails> {

    JsonbBuilder jsonbBuilder = new JsonBindingBuilder();
    @Override
    public Emails deserialize(String topic, byte[] data) {

        if (data == null) {

            return null;

        }

        return jsonbBuilder.build().fromJson(new String(data), Emails.class);

    }


}

