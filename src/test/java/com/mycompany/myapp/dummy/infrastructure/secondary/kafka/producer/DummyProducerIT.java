package com.mycompany.myapp.dummy.infrastructure.secondary.kafka.producer;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.concurrent.Future;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import com.mycompany.myapp.IntegrationTest;

@IntegrationTest
class DummyProducerIT {

  @Autowired
  private DummyProducer dummyProducer;

  @Test
  void shouldSend() {
    final Future<RecordMetadata> dummy = dummyProducer.send("dummy");

    assertNotNull(dummy);
  }
}
