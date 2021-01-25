package com.pangjie.doubleDBConfig;

import java.lang.annotation.*;

@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DataSource {
    String value() default DataSourceNames.ONE;
}
