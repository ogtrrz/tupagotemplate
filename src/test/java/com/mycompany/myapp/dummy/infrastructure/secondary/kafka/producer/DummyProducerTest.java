package com.mycompany.myapp.dummy.infrastructure.secondary.kafka.producer;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import org.apache.kafka.clients.producer.MockProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.mycompany.myapp.UnitTest;

@UnitTest
class DummyProducerTest {

  private DummyProducer dummyProducer;
  private MockProducer mockProducer;

  @BeforeEach
  public void setUp() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    mockProducer = new MockProducer<>(true, new StringSerializer(), new StringSerializer());
    dummyProducer = new DummyProducer("queue.jhipster.dummy", mockProducer);
    Method postConstruct = DummyProducer.class.getDeclaredMethod("init");
    postConstruct.setAccessible(true);
    postConstruct.invoke(dummyProducer);
  }

  @Test
  void shouldSend() {
    dummyProducer.send("dummy message");

    assertThat(mockProducer.history()).hasSize(1);
    assertThat(((ProducerRecord) mockProducer.history().get(0)).value()).isEqualTo("dummy message");
  }

  @Test
  void shouldNotSend() {
    MockProducer<String, String> mockProducer = new MockProducer<>(false, new StringSerializer(), new StringSerializer());

    dummyProducer = new DummyProducer("queue.jhipster.dummy", mockProducer);
    final Future<RecordMetadata> producerRecord = dummyProducer.send("dummy message");
    RuntimeException expectedException = new RuntimeException();
    mockProducer.errorNext(expectedException);

    try {
      producerRecord.get();
    } catch (ExecutionException | InterruptedException ex) {
      assertThat(ex.getCause()).isEqualTo(expectedException);
    }
    assertThat(producerRecord.isDone()).isTrue();
  }
}
