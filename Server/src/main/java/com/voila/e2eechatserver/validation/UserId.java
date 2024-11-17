package com.voila.e2eechatserver.validation;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import org.hibernate.validator.constraints.Length;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UserId.IdValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Length(min = 1,max = 25)
public @interface UserId {
    String message() default "Invalid id";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    class IdValidator implements ConstraintValidator<UserId, String> {
        @Override
        public boolean isValid(String value, ConstraintValidatorContext context){
            return value.matches("[a-zA-Z0-9_]+");
        }
    }

}
