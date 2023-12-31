package com.tupago.shared.pagination.domain;

import java.util.List;

import com.tupago.shared.pagination.domain.TuPagoTemplatePage.TuPagoTemplatePageBuilder;

public final class TuPagoTemplatePagesFixture {

  private TuPagoTemplatePagesFixture() {}

  public static TuPagoTemplatePage<String> page() {
    return pageBuilder().build();
  }

  public static TuPagoTemplatePageBuilder<String> pageBuilder() {
    return TuPagoTemplatePage.builder(List.of("test")).currentPage(2).pageSize(10).totalElementsCount(21);
  }
}
