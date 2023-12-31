package com.tupago.wire.logstash.infrastructure.secondary;

import static org.assertj.core.api.Assertions.assertThat;

import com.tupago.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class LogstashTcpPropertiesTest {

  @Test
  void shouldDisableByDefault() {
    assertThat(new LogstashTcpProperties().isEnabled()).isFalse();
  }
}
