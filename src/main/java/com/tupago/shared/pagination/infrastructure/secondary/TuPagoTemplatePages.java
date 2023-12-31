package com.tupago.shared.pagination.infrastructure.secondary;

import java.util.function.Function;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.tupago.shared.error.domain.Assert;
import com.tupago.shared.pagination.domain.TuPagoTemplatePage;
import com.tupago.shared.pagination.domain.TuPagoTemplatePageable;

public final class TuPagoTemplatePages {

  private TuPagoTemplatePages() {}

  public static Pageable from(TuPagoTemplatePageable pagination) {
    return from(pagination, Sort.unsorted());
  }

  public static Pageable from(TuPagoTemplatePageable pagination, Sort sort) {
    Assert.notNull("pagination", pagination);
    Assert.notNull("sort", sort);

    return PageRequest.of(pagination.page(), pagination.pageSize(), sort);
  }

  public static <S, T> TuPagoTemplatePage<T> from(Page<S> springPage, Function<S, T> mapper) {
    Assert.notNull("springPage", springPage);
    Assert.notNull("mapper", mapper);

    return TuPagoTemplatePage
      .builder(springPage.getContent().stream().map(mapper).toList())
      .currentPage(springPage.getNumber())
      .pageSize(springPage.getSize())
      .totalElementsCount(springPage.getTotalElements())
      .build();
  }
}
