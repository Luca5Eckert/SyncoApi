package com.api.synco.module.user.infrastructure.converter;

import com.api.synco.module.user.domain.vo.Name;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * JPA attribute converter for the {@link Name} value object.
 *
 * <p>This converter automatically handles the conversion between the
 * {@link Name} value object used in the domain layer and its
 * {@link String} representation stored in the database.</p>
 *
 * <p>The converter is marked with {@code autoApply = true}, meaning it
 * will be automatically applied to all entity attributes of type
 * {@link Name} without requiring explicit annotation.</p>
 *
 * @author Luca5Eckert
 * @version 1.0.0
 * @since 1.0.0
 * @see Name
 * @see AttributeConverter
 */
@Converter(autoApply = true) 
public class NameConverter implements AttributeConverter<Name, String> {

    /**
     * Converts the {@link Name} value object to its database column representation.
     *
     * @param name the name value object to convert (can be null)
     * @return the name string, or null if the input is null
     */
    @Override
    public String convertToDatabaseColumn(Name name) {
        return name != null ? name.value() : null;
    }

    /**
     * Converts the database column value to a {@link Name} value object.
     *
     * @param dbData the name string from the database (can be null)
     * @return a new {@link Name} value object, or null if the input is null
     */
    @Override
    public Name convertToEntityAttribute(String dbData) {
        return dbData != null ? new Name(dbData) : null;
    }

}