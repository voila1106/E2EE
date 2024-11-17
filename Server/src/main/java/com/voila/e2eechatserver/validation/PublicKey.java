package com.voila.e2eechatserver.validation;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import lombok.SneakyThrows;
import org.apache.tomcat.util.buf.HexUtils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.spec.X509EncodedKeySpec;

@Constraint(validatedBy = PublicKey.PublicKeyValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface PublicKey {
    String message() default "Invalid public key";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    class PublicKeyValidator implements ConstraintValidator<PublicKey, String> {
        @SneakyThrows(NoSuchAlgorithmException.class)
        @Override
        public boolean isValid(String value, ConstraintValidatorContext context){
            KeyFactory keyFactory = KeyFactory.getInstance("EC");
            try{
                X509EncodedKeySpec spec = new X509EncodedKeySpec(HexUtils.fromHexString(value));
                keyFactory.generatePublic(spec);
            }catch(Exception e){
                return false;
            }
            return true;
        }
    }
}
