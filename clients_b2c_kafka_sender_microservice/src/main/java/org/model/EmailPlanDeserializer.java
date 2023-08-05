package org.model;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serializer;
import org.eclipse.yasson.internal.JsonBindingBuilder;

import javax.json.bind.JsonbBuilder;

public class EmailPlanDeserializer implements Deserializer<EmailsPlan> {
    JsonbBuilder jsonbBuilder = new JsonBindingBuilder();
    @Override
    public EmailsPlan deserialize(String topic, byte[] data) {

        if (data == null) {

            return null;

        }

        return jsonbBuilder.build().fromJson(new String(data), EmailsPlan.class);

    }

}


