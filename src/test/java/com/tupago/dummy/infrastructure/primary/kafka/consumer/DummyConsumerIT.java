package com.tupago.dummy.infrastructure.primary.kafka.consumer;

import static org.assertj.core.api.Assertions.assertThat;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import com.tupago.IntegrationTest;
import com.tupago.dummy.infrastructure.secondary.kafka.producer.DummyProducer;

@IntegrationTest
class DummyConsumerIT {

  @Autowired
  private DummyProducer dummyProducer;

  @Autowired
  private DummyConsumer dummyConsumer;

  @Test
  void shouldHandleMessage() {
    final String messageToSend = "dummy";
    dummyProducer.send(messageToSend);

    ConsumerRecord<String, String> record = new ConsumerRecord<>("queue.kafkaapp.dummy", 0, 0, null, messageToSend);
    boolean actualResult = dummyConsumer.handleMessage(record);

    assertThat(actualResult).isTrue();
  }
}
