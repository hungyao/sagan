package sagan.web.json;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

public class DoubleJsonSerializer extends JsonSerializer<double[][]> {

	@Override
	public void serialize(double[][] value, JsonGenerator jgen,
			SerializerProvider provider) throws IOException,
			JsonProcessingException {
		jgen.writeStartArray();
		for (int i = 0; i < value.length; i++) {
			jgen.writeStartArray();
			for (int j = 0; j < value[i].length; j++) {
				if (value[i][j] == (int)value[i][j]) {
					jgen.writeNumber((int)value[i][j]);
				} else {
					jgen.writeRawValue(String.format("%.3f", value[i][j]));
				}
			}
			jgen.writeEndArray();
		}
		jgen.writeEndArray();
		
	}
	
}
