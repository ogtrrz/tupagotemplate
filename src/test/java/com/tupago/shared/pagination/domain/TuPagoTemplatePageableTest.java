package com.tupago.shared.pagination.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.tupago.UnitTest;
import com.tupago.shared.error.domain.NumberValueTooHighException;
import com.tupago.shared.error.domain.NumberValueTooLowException;

@UnitTest
class TuPagoTemplatePageableTest {

  @Test
  void shouldNotBuildWithNegativePage() {
    assertThatThrownBy(() -> new TuPagoTemplatePageable(-1, 10))
      .isExactlyInstanceOf(NumberValueTooLowException.class)
      .hasMessageContaining("page");
  }

  @Test
  void shouldNotBuildWithPageSizeAtZero() {
    assertThatThrownBy(() -> new TuPagoTemplatePageable(0, 0))
      .isExactlyInstanceOf(NumberValueTooLowException.class)
      .hasMessageContaining("pageSize");
  }

  @Test
  void shouldNotBuildWithPageSizeOverHundred() {
    assertThatThrownBy(() -> new TuPagoTemplatePageable(0, 101))
      .isExactlyInstanceOf(NumberValueTooHighException.class)
      .hasMessageContaining("pageSize");
  }

  @Test
  void shouldGetFirstPageInformation() {
    TuPagoTemplatePageable pageable = new TuPagoTemplatePageable(0, 15);

    assertThat(pageable.page()).isZero();
    assertThat(pageable.pageSize()).isEqualTo(15);
    assertThat(pageable.offset()).isZero();
  }

  @Test
  void shouldGetPageableInformation() {
    TuPagoTemplatePageable pageable = new TuPagoTemplatePageable(2, 15);

    assertThat(pageable.page()).isEqualTo(2);
    assertThat(pageable.pageSize()).isEqualTo(15);
    assertThat(pageable.offset()).isEqualTo(30);
  }
}
