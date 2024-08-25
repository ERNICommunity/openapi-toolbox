package erni.dev.openapitoolbox.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.openapi4j.core.exception.EncodeException;
import org.openapi4j.core.exception.ResolutionException;
import org.openapi4j.core.validation.ValidationException;
import org.openapi4j.parser.OpenApi3Parser;
import org.openapi4j.parser.model.v3.OpenApi3;
import org.openapi4j.schema.validator.ValidationContext;
import org.openapi4j.schema.validator.ValidationData;
import org.openapi4j.schema.validator.v3.SchemaValidator;
import org.openapitools.jackson.nullable.JsonNullableModule;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.util.logging.Logger;

public class OpenapiValidator {

    private final Logger LOG = Logger.getLogger(OpenapiValidator.class.getName());

    private final OpenApi3 openApi3;
    private final ObjectMapper om;

    public OpenapiValidator(String specFilePath) {

        openApi3 = createOpenapi3(specFilePath);

        om = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .addModule(new JsonNullableModule())
                .build();
    }

    public <T> boolean validate(T value) {

        String schemaName = value.getClass().getSimpleName();
        try {
            JsonNode schemaNode = openApi3.getComponents().getSchema(schemaName).toNode();
            JsonNode contentNode = om.convertValue(value, JsonNode.class);
            SchemaValidator validator = new SchemaValidator(new ValidationContext<>(openApi3.getContext()), null, schemaNode);
            ValidationData<Void> validationData = new ValidationData<>();
            validator.validate(contentNode, validationData);
            System.out.println(om.writeValueAsString(contentNode));;
            if (!validationData.isValid()) {
                LOG.info("Validation for Node " + contentNode + " failed with results:\n" + validationData.results());
            }
            return validationData.isValid();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    private static OpenApi3 createOpenapi3(String specFilePath) {
        try {
            return new OpenApi3Parser().parse(Path.of(specFilePath).toUri().toURL(), true);
        }
        catch (ResolutionException | ValidationException | MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
