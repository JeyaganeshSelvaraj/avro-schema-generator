package org.example;

import org.example.models.Person;
import org.junit.jupiter.api.Test;

import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.assertFalse;

class AvroPojoSchemaGeneratorTest {
    @Test
    void generateSchema() {
        StringWriter sw = new StringWriter();
        AvroPojoSchemaGenerator.generateSchema(Person.class, sw);
        System.out.println(sw);
        assertFalse(sw.toString().isBlank());
    }
}