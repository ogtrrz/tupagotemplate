package com.tupago.dummy.infrastructure.primary.kafka.consumer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.Duration;
import java.util.*;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.errors.WakeupException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.tupago.UnitTest;

@UnitTest
@ExtendWith(MockitoExtension.class)
class DummyConsumerTest {

  public static final int TIMEOUT_FOR_EXECUTOR = 3000;

  @Mock
  private KafkaConsumer<String, String> consumer;

  private DummyConsumer dummyConsumer;

  @BeforeEach
  public void setUp() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
    dummyConsumer = new DummyConsumer("queue.jhipster.dummy", 10_000, consumer);

    Method postConstructInit = DummyConsumer.class.getDeclaredMethod("init");
    postConstructInit.setAccessible(true);
    postConstructInit.invoke(dummyConsumer);

    Method postConstructDestroy = DummyConsumer.class.getDeclaredMethod("destroy");
    postConstructDestroy.setAccessible(true);
    postConstructDestroy.invoke(dummyConsumer);
  }

  @Test
  void shouldHandleMessage() {
    ConsumerRecord<String, String> record = new ConsumerRecord<>("queue.jhipster.dummy", 0, 0, null, "dummy message");
    boolean actualResult = dummyConsumer.handleMessage(record);
    assertThat(actualResult).isTrue();
  }

  @Test
  void shouldNotHandleMessage() {
    ConsumerRecord<String, String> record = new ConsumerRecord<>("queue.jhipster.dummy", 0, 0, null, null);
    boolean actualResult = dummyConsumer.handleMessage(record);
    assertThat(actualResult).isFalse();
  }

  @Test
  void shouldExecuteKafkaRunner() {
    Map<TopicPartition, List<ConsumerRecord<String, String>>> recordsMap = new HashMap<>();
    List<ConsumerRecord<String, String>> recordsList = new ArrayList<>();
    recordsList.add(new ConsumerRecord<>("queue.jhipster.dummy", 0, 0, null, "dummy message"));
    recordsMap.put(new TopicPartition("queue.jhipster", 0), recordsList);
    ConsumerRecords<String, String> records = new ConsumerRecords(recordsMap);
    when(consumer.poll(Duration.ofMillis(10000))).thenReturn(records);
    dummyConsumer.setClosed(false);

    dummyConsumer.executeKafkaRunner();

    verify(consumer, timeout(TIMEOUT_FOR_EXECUTOR)).subscribe(Collections.singleton("queue.jhipster.dummy"));
  }

  @Test
  void shouldExecuteKafkaRunnerNotPolling() {
    dummyConsumer.setClosed(true);

    dummyConsumer.executeKafkaRunner();

    verify(consumer, timeout(TIMEOUT_FOR_EXECUTOR)).subscribe(Collections.singleton("queue.jhipster.dummy"));
  }

  @Test
  void shouldThrowsException() {
    dummyConsumer.setClosed(false);
    when(consumer.poll(Duration.ofMillis(10000))).thenThrow(NullPointerException.class);

    dummyConsumer.executeKafkaRunner();

    verify(consumer, timeout(TIMEOUT_FOR_EXECUTOR)).subscribe(Collections.singleton("queue.jhipster.dummy"));
  }

  @Test
  void shouldThrowsWakeupException() {
    dummyConsumer.setClosed(false);
    when(consumer.poll(Duration.ofMillis(10000))).thenThrow(WakeupException.class);

    dummyConsumer.executeKafkaRunner();

    verify(consumer, timeout(TIMEOUT_FOR_EXECUTOR)).subscribe(Collections.singleton("queue.jhipster.dummy"));
  }

  @Test
  void shouldThrowsWakeupExceptionWithClosing() {
    dummyConsumer.setClosed(true);
    doThrow(WakeupException.class).when(consumer).subscribe(Collections.singleton("queue.jhipster.dummy"));

    dummyConsumer.executeKafkaRunner();

    verify(consumer, timeout(TIMEOUT_FOR_EXECUTOR)).subscribe(Collections.singleton("queue.jhipster.dummy"));
  }

  @Test
  void shouldSetClosed() {
    dummyConsumer.setClosed(true);
    assertThat(dummyConsumer.isClosed()).isTrue();
  }
}
