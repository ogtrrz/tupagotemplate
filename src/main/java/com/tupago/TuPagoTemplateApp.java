package com.tupago;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.jhipster.lite.JHLiteApp;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

import com.tupago.shared.generation.domain.ExcludeFromGeneratedCodeCoverage;

@SpringBootApplication(scanBasePackageClasses = { JHLiteApp.class, TuPagoTemplateApp.class })
@ExcludeFromGeneratedCodeCoverage(reason = "Not testing logs")
public class TuPagoTemplateApp {

  private static final Logger log = LoggerFactory.getLogger(TuPagoTemplateApp.class);

  public static void main(String[] args) {
    Environment env = SpringApplication.run(TuPagoTemplateApp.class, args).getEnvironment();

    if (log.isInfoEnabled()) {
      log.info(ApplicationStartupTraces.of(env));
    }
  }
}
