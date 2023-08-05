package org.ressource;

import io.quarkus.mailer.Mail;
import io.quarkus.mailer.Mailer;
import io.quarkus.mailer.MockMailbox;
import io.quarkus.test.Mock;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.kafka.InjectKafkaCompanion;
import io.quarkus.test.kafka.KafkaCompanionResource;
import io.smallrye.reactive.messaging.kafka.companion.ConsumerTask;
import io.smallrye.reactive.messaging.kafka.companion.KafkaCompanion;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import static io.smallrye.common.constraint.Assert.assertTrue;
import static org.awaitility.Awaitility.given;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import io.smallrye.reactive.messaging.kafka.Record;
import org.testcontainers.shaded.org.hamcrest.Matchers;

@QuarkusTest
@QuarkusTestResource(KafkaCompanionResource.class)
public class MailConsumerTest {
    @InjectKafkaCompanion
    KafkaCompanion companion;
    @Inject
    MockMailbox mailbox;

    @Test
    public void test() throws ExecutionException, InterruptedException {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("key.serializer", StringSerializer.class.getName());
        props.put("value.serializer", StringSerializer.class.getName());
        KafkaProducer<String, String> producer = new KafkaProducer<>(props);
        ProducerRecord<String, String> record = new ProducerRecord<>("email", "ggggg", "value");
        var xx = producer.send(record);
        System.out.println("xx = " + xx.get());

//       /* List<Mail> sent = mailbox.getMessagesSentTo(TO);
//        assertThat(sent).hasSize(1);
//        Mail actual = sent.get(0);
//        assertThat(actual.getText()).contains("Wake up!");
//        assertThat(actual.getSubject()).isEqualTo("Alarm!");
//
//        assertThat(mailbox.getTotalMessagesSent()).isEqualTo(6);
//    */
    }







//    @Test
//    public void testProducer() {
//        Properties props = new Properties();
//        props.put("bootstrap.servers", "localhost:9092");
//        props.put("key.serializer", StringSerializer.class.getName());
//        props.put("value.serializer", StringSerializer.class.getName());
//
//        // Create a new Kafka producer
//        KafkaProducer<String, String> producer = new KafkaProducer<>(props);
//
//        // Send a message to the "email" topic
//        ProducerRecord<String, String> record = new ProducerRecord<>("email", "key", "value");
//        producer.send(record);
//
//        // Verify that the message was sent successfully
//        assertTrue(true);
//
//        // Close the producer
//        producer.close();
//    }

}