package com.study.study_ai.converter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.ai.converter.FormatProvider;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class StringToMapWithPojo<S, T> implements Converter<String, Map<S, T>>, FormatProvider {

    private final ObjectMapper objectMapper;

    public StringToMapWithPojo(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Map<S, T> convert(String source) {
        try {
            return objectMapper.readValue(source, new TypeReference<>() {
            });
        } catch (IOException e) {
            throw new RuntimeException("Failed to convert String to Map<String, FeaturesWrapper>", e);
        }
    }

    @Override
    public String getFormat() {
        return """
            Respond with a JSON object where each key is a string and each value is an object with the following structure:
                {
                    "Java18": {
                        "features": [
                            {
                                "name": "JEP 400: UTF-8 by Default",
                                "description": "Sets UTF-8 as the default charset of the standard Java APIs."
                            },
                            {
                                "name": "JEP 408: Simple Web Server",
                                "description": "Provides a command-line tool to start a minimal web server serving static files."
                            },
                            {
                                "name": "JEP 413: Code Snippets in Java API Documentation",
                                "description": "Introduces a new `@snippet` tag for adding code snippets in API documentation."
                            },
                            {
                                "name": "JEP 416: Reimplement Core Reflection with Method Handles",
                                "description": "Improves performance by reimplementing parts of the core reflection API using method handles."
                            },
                            {
                                "name": "JEP 417: Vector API (Third Incubator)",
                                "description": "Adds enhancements to the incubating Vector API for better performance of vector computations."
                            },
                            {
                                "name": "JEP 418: Internet-Address Resolution SPI",
                                "description": "Introduces a service provider interface for host name and address resolution."
                            },
                            {
                                "name": "JEP 419: Foreign Function & Memory API (Second Incubator)",
                                "description": "Updates the incubating API for interacting with native code and memory."
                            },
                            {
                                "name": "JEP 420: Pattern Matching for switch (Second Preview)",
                                "description": "Enhances the `switch` statement and expression with pattern matching capabilities."
                            }
                        ]
                    },
                    "Java19": {
                        "features": [
                            {
                                "name": "JEP 405: Record Patterns (Preview)",
                                "description": "Introduces record patterns for deconstructing record values."
                            },
                            {
                                "name": "JEP 422: Linux/RISC-V Port",
                                "description": "Adds support for the RISC-V instruction set architecture on Linux."
                            },
                            {
                                "name": "JEP 424: Foreign Function & Memory API (Preview)",
                                "description": "Provides a preview of an API for calling native functions and accessing native memory."
                            },
                            {
                                "name": "JEP 425: Virtual Threads (Preview)",
                                "description": "Introduces virtual threads to simplify writing, maintaining, and observing high-throughput concurrent applications."
                            },
                            {
                                "name": "JEP 426: Vector API (Fourth Incubator)",
                                "description": "Continues the incubation of the Vector API, providing performance improvements and new features."
                            },
                            {
                                "name": "JEP 427: Pattern Matching for switch (Third Preview)",
                                "description": "Further enhances `switch` with pattern matching, building on previous previews."
                            },
                            {
                                "name": "JEP 428: Structured Concurrency (Incubator)",
                                "description": "Introduces an API to simplify multithreaded programming by treating multiple tasks running in different threads as a single unit of work."
                            }
                        ]
                    }
                }
            """;
    }
}
