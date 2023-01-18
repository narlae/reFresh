package com.onlyfresh.devkurly.web.utils.validator;

import com.onlyfresh.devkurly.web.exception.LoginFormCheckException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.server.ServerErrorException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;

@Slf4j
public class ConfirmPwdValidator implements ConstraintValidator<ConfirmPwd, Object> {
    private String message;
    private String pwd;
    private String cpwd;

    @Override
    public void initialize(ConfirmPwd constraintAnnotation) {
        message = constraintAnnotation.message();
        pwd = constraintAnnotation.pwd();
        cpwd = constraintAnnotation.cpwd();
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext context) {
        String pwdValue = getFieldValue(o, pwd);
        String cpwdValue = getFieldValue(o, cpwd);

        if (!pwdValue.equals(cpwdValue)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(cpwd)
                    .addConstraintViolation();
            return false;
        }
        return true;
    }

    private String getFieldValue(Object object, String fieldName) {
        Class<?> clazz = object.getClass();
        Field dataField;

        try {
            dataField = clazz.getDeclaredField(fieldName);
            dataField.setAccessible(true);
            Object target = dataField.get(object);
            return (String) target;
        } catch (NoSuchFieldException e) {
            log.error("NoSuchFieldException", e);
        } catch (IllegalAccessException e) {
            log.error("IllegalAccessException", e);
        }
        throw new LoginFormCheckException("비밀번호를 입력해야합니다.");
    }
}
