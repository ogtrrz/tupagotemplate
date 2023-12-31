package com.tupago.shared.pagination.infrastructure.primary;

import static org.assertj.core.api.Assertions.*;
import static com.tupago.shared.pagination.domain.TuPagoTemplatePagesFixture.*;

import java.util.function.Function;

import org.junit.jupiter.api.Test;

import com.tupago.UnitTest;
import com.tupago.JsonHelper;
import com.tupago.shared.error.domain.MissingMandatoryValueException;

@UnitTest
class RestTuPagoTemplatePageTest {

  @Test
  void shouldNotConvertWithoutSourcePage() {
    assertThatThrownBy(() -> RestTuPagoTemplatePage.from(null, source -> "test")).isExactlyInstanceOf(MissingMandatoryValueException.class);
  }

  @Test
  void shouldNotConvertWithoutMappingFunction() {
    assertThatThrownBy(() -> RestTuPagoTemplatePage.from(page(), null)).isExactlyInstanceOf(MissingMandatoryValueException.class);
  }

  @Test
  void shouldMapFromDomainPage() {
    RestTuPagoTemplatePage<String> page = RestTuPagoTemplatePage.from(page(), Function.identity());

    assertThat(page.getContent()).containsExactly("test");
    assertThat(page.getCurrentPage()).isEqualTo(2);
    assertThat(page.getPageSize()).isEqualTo(10);
    assertThat(page.getTotalElementsCount()).isEqualTo(21);
    assertThat(page.getPagesCount()).isEqualTo(3);
  }

  @Test
  void shouldGetPageCountForPageLimit() {
    RestTuPagoTemplatePage<String> page = RestTuPagoTemplatePage.from(pageBuilder().totalElementsCount(3).pageSize(3).build(), Function.identity());

    assertThat(page.getPagesCount()).isEqualTo(1);
  }

  @Test
  void shouldSerializeToJson() {
    assertThat(JsonHelper.writeAsString(RestTuPagoTemplatePage.from(page(), Function.identity()))).isEqualTo(json());
  }

  private String json() {
    return """
        {"content":["test"],\
        "currentPage":2,\
        "pageSize":10,\
        "totalElementsCount":21,\
        "pagesCount":3,\
        "hasPrevious":true,\
        "hasNext":false\
        }\
        """;
  }
}
