package org.model;

import org.apache.kafka.common.serialization.Serializer;
import org.eclipse.yasson.internal.JsonBindingBuilder;

import javax.json.bind.JsonbBuilder;

public class EmailListSerialize implements Serializer<test> {

        JsonbBuilder jsonbBuilder = new JsonBindingBuilder();

@Override

public byte[] serialize(String topic, test data){

        if (data == null)

        return new byte[0];

        return jsonbBuilder.build().toJson(data, test.class).getBytes();

        }

        }
