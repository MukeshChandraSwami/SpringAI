package com.study.cmd_chat_assistant.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class CassandraVectorService {

    @Autowired
    private VectorStore vectorStore;

    @Value("classpath:cassandra/data.json")
    private Resource data;

    @Value("classpath:cassandra/mobiles.json")
    private Resource mobilesData;

    public List<Document> loadMobileDocumentsInStore() {
        return loadDataInStore(mobilesData);
    }

    public List<Document> loadDocumentsInStore() {
        return loadDataInStore(data);
    }

    public List<Document> search(String query) {
        return vectorStore.similaritySearch(SearchRequest.query(query)
                .withFilterExpression("price > 999 && brand == 'Asus'"));
    }

    private List<Document> loadDataInStore(Resource data) {
        List<Document> documents;

        ObjectMapper objectMapper = new ObjectMapper();
        try (InputStream inputStream = data.getInputStream()) {
            documents = objectMapper.readValue(inputStream, new TypeReference<>() {});
        } catch (IOException e) {
            throw new RuntimeException("Failed to read mobiles.json", e);
        }

        TextSplitter textSplitter = new TokenTextSplitter();
        documents.forEach(document -> {
            List<Document> splitedDocs = textSplitter.split(document);
            vectorStore.add(splitedDocs);
        });
        return documents;
    }
}
