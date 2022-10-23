package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.avro.AvroFactory;
import com.fasterxml.jackson.dataformat.avro.AvroSchema;
import com.fasterxml.jackson.dataformat.avro.schema.AvroSchemaGenerator;
import org.apache.avro.Schema;
import org.example.models.Person;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;

public class AvroPojoSchemaGenerator {
    public static <T> void generateSchema(Class<T> clazz, Writer writer) {
        ObjectMapper mapper = new ObjectMapper(new AvroFactory());
        AvroSchemaGenerator generator = new AvroSchemaGenerator();
        generator.enableLogicalTypes();
        try {
            mapper.acceptJsonFormatVisitor(clazz, generator);
            AvroSchema generatedSchema = generator.getGeneratedSchema();
            Schema avroSchema = generatedSchema.getAvroSchema();
            writer.write(avroSchema.toString(true));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        String fname = Person.class.getSimpleName().toLowerCase();
        try (Writer bw = Files.newBufferedWriter(Paths.get(".", fname + ".avsc"))) {
            generateSchema(Person.class, bw);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}