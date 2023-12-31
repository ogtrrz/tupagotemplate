package com.tupago.shared.pagination.infrastructure.secondary;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.function.Function;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.tupago.UnitTest;
import com.tupago.shared.error.domain.MissingMandatoryValueException;
import com.tupago.shared.pagination.domain.TuPagoTemplatePage;
import com.tupago.shared.pagination.domain.TuPagoTemplatePageable;

@UnitTest
class TuPagoTemplatePagesTest {

  @Test
  void shouldNotBuildPageableFromNullTuPagoTemplatePageable() {
    assertThatThrownBy(() -> TuPagoTemplatePages.from(null))
      .isExactlyInstanceOf(MissingMandatoryValueException.class)
      .hasMessageContaining("pagination");
  }

  @Test
  void shouldBuildPageableFromTuPagoTemplatePageable() {
    Pageable pagination = TuPagoTemplatePages.from(pagination());

    assertThat(pagination.getPageNumber()).isEqualTo(2);
    assertThat(pagination.getPageSize()).isEqualTo(15);
    assertThat(pagination.getSort()).isEqualTo(Sort.unsorted());
  }

  @Test
  void shouldNotBuildWithoutSort() {
    assertThatThrownBy(() -> TuPagoTemplatePages.from(pagination(), null))
      .isExactlyInstanceOf(MissingMandatoryValueException.class)
      .hasMessageContaining("sort");
  }

  @Test
  void shouldBuildPageableFromTuPagoTemplatePageableAndSort() {
    Pageable pagination = TuPagoTemplatePages.from(pagination(), Sort.by("dummy"));

    assertThat(pagination.getPageNumber()).isEqualTo(2);
    assertThat(pagination.getPageSize()).isEqualTo(15);
    assertThat(pagination.getSort()).isEqualTo(Sort.by("dummy"));
  }

  private TuPagoTemplatePageable pagination() {
    return new TuPagoTemplatePageable(2, 15);
  }

  @Test
  void shouldNotConvertFromSpringPageWithoutSpringPage() {
    assertThatThrownBy(() -> TuPagoTemplatePages.from(null, source -> source))
      .isExactlyInstanceOf(MissingMandatoryValueException.class)
      .hasMessageContaining("springPage");
  }

  @Test
  void shouldNotConvertFromSpringPageWithoutMapper() {
    assertThatThrownBy(() -> TuPagoTemplatePages.from(springPage(), null))
      .isExactlyInstanceOf(MissingMandatoryValueException.class)
      .hasMessageContaining("mapper");
  }

  @Test
  void shouldConvertFromSpringPage() {
    TuPagoTemplatePage<String> page = TuPagoTemplatePages.from(springPage(), Function.identity());

    assertThat(page.content()).containsExactly("test");
    assertThat(page.currentPage()).isEqualTo(2);
    assertThat(page.pageSize()).isEqualTo(10);
    assertThat(page.totalElementsCount()).isEqualTo(30);
  }

  private PageImpl<String> springPage() {
    return new PageImpl<>(List.of("test"), PageRequest.of(2, 10), 30);
  }
}
