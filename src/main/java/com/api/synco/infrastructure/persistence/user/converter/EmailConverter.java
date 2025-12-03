package com.api.synco.infrastructure.persistence.user.converter;

import com.api.synco.module.user.domain.vo.Email;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * JPA attribute converter for the {@link Email} value object.
 *
 * <p>This converter automatically handles the conversion between the
 * {@link Email} value object used in the domain layer and its
 * {@link String} representation stored in the database.</p>
 *
 * <p>The converter is marked with {@code autoApply = true}, meaning it
 * will be automatically applied to all entity attributes of type
 * {@link Email} without requiring explicit annotation.</p>
 *
 * @author Luca5Eckert
 * @version 1.0.0
 * @since 1.0.0
 * @see Email
 * @see AttributeConverter
 */
@Converter(autoApply = true)
public class EmailConverter implements AttributeConverter<Email, String> {

    /**
     * Converts the {@link Email} value object to its database column representation.
     *
     * @param email the email value object to convert (can be null)
     * @return the email address string, or null if the input is null
     */
    @Override
    public String convertToDatabaseColumn(Email email) {
        return email != null ? email.address() : null;
    }

    /**
     * Converts the database column value to an {@link Email} value object.
     *
     * @param dbData the email string from the database (can be null)
     * @return a new {@link Email} value object, or null if the input is null
     */
    @Override
    public Email convertToEntityAttribute(String dbData) {
        return dbData != null ? new Email(dbData) : null;
    }

}