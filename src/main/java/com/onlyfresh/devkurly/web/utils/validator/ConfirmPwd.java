package com.onlyfresh.devkurly.web.utils.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ConfirmPwdValidator.class)
public @interface ConfirmPwd {
    String message() default "비밀번호와 동일하게 입력해주십시오.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
    String pwd();
    String cpwd();
}
