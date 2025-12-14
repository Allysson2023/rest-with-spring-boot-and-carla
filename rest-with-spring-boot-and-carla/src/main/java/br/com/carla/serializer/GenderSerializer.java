package br.com.carla.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class GenderSerializer extends JsonSerializer<String> {
    @Override
    public void serialize(String gende, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        String formatedGende = "Male".equals(gende) ? "M" : "F";
        gen.writeString(formatedGende);
    }
}
