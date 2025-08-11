package com.learn.search.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.io.entity.StringEntity;

import java.io.IOException;
import java.util.Map;

public class EmbeddingUtils {

    private static final ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
    }

    public static float[] generateEmbedding(String embeddingsUrl, String text) throws IOException {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost request = new HttpPost(embeddingsUrl);
            String body = objectMapper.writeValueAsString(Map.of(
                    "model", "gte-small",
                    "input", text
            ));
            request.setEntity(new StringEntity(body, ContentType.APPLICATION_JSON));
            var response = client.execute(request);
            JsonNode root = objectMapper.readTree(response.getEntity().getContent());
            JsonNode embeddingNode = root.get("data").get(0).get("embedding");
            float[] embedding = new float[embeddingNode.size()];
            for (int i = 0; i < embeddingNode.size(); i++) {
                embedding[i] = embeddingNode.get(i).floatValue();
            }
            return embedding;
        }
    }
}
