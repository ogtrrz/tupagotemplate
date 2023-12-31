package com.tupago.shared.pagination.infrastructure.primary;

import static com.tupago.BeanValidationAssertions.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.tupago.UnitTest;
import com.tupago.shared.pagination.domain.TuPagoTemplatePageable;

@UnitTest
class RestTuPagoTemplatePageableTest {

  @Test
  void shouldConvertToDomain() {
    TuPagoTemplatePageable pageable = pageable().toPageable();

    assertThat(pageable.page()).isEqualTo(1);
    assertThat(pageable.pageSize()).isEqualTo(15);
  }

  @Test
  void shouldNotValidateWithPageUnderZero() {
    RestTuPagoTemplatePageable pageable = pageable();
    pageable.setPage(-1);

    assertThatBean(pageable).hasInvalidProperty("page");
  }

  @Test
  void shouldNotValidateWithSizeAtZero() {
    RestTuPagoTemplatePageable pageable = pageable();
    pageable.setPageSize(0);

    assertThatBean(pageable).hasInvalidProperty("pageSize").withParameter("value", 1L);
  }

  @Test
  void shouldNotValidateWithPageSizeOverHundred() {
    RestTuPagoTemplatePageable pageable = pageable();
    pageable.setPageSize(101);

    assertThatBean(pageable).hasInvalidProperty("pageSize");
  }

  private RestTuPagoTemplatePageable pageable() {
    return new RestTuPagoTemplatePageable(1, 15);
  }
}
