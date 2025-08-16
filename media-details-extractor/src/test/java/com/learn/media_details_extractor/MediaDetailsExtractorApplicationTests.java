package com.learn.media_details_extractor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.learn.media_details_extractor.models.ImageAnalysis;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

class MediaDetailsExtractorApplicationTests {

	private static final String TEST_MEDIA_DATA = """
			{
			    "mainContent": "A scenic landscape showcasing a vast desert terrain with rolling sand dunes and distant mountains under a twilight sky.",
			    "objects": [
			        {
			            "name": "Text box",
			            "quantity": 1,
			            "position": "center",
			            "description": "A rectangular text box with a blue background containing promotional text."
			        }
			    ],
			    "text": [
			        {
			            "content": "Interested in attending Event Title?",
			            "position": "center of text box"
			        },
			        {
			            "content": "Register Now",
			            "position": "bottom of text box"
			        }
			    ],
			    "background": {
			        "environment": "Desert landscape with mountains",
			        "details": "The scene features a wide expanse of sand dunes with a gradient sky transitioning from orange to blue, indicating either sunset or sunrise."
			    },
			    "colorsAndTextures": {
			        "dominantColors": [
			            "#E1B07F",
			            "#8C5D4E",
			            "#A7A3A1"
			        ],
			        "textures": [
			            "smooth",
			            "grainy"
			        ]
			    },
			    "scene": {
			        "type": "outdoor desert landscape",
			        "activity": "Promotional event registration"
			    },
			    "style": {
			        "imageType": "digital image",
			        "moodOrTone": "inviting and serene"
			    },
			    "tags": [
			        "desert",
			        "promotion",
			        "event",
			        "landscape",
			        "registration"
			    ]
			}
			""";

	@Test
	void contextLoads() throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		ImageAnalysis imageAnalysis = mapper.readValue(TEST_MEDIA_DATA, ImageAnalysis.class);
		String s = toEmbeddingInput(imageAnalysis);
		System.out.println(s);
	}

	public static String toEmbeddingInput(Object obj) {
		StringBuilder sb = new StringBuilder();
		serializeObject(obj, sb, "");
		return sb.toString().trim();
	}

	private static void serializeObject(Object obj, StringBuilder sb, String prefix) {
		if (obj == null) {
			return;
		}

		Class<?> clazz = obj.getClass();

		// If primitive, wrapper, or string → append directly
		if (clazz.isPrimitive() || obj instanceof String || obj instanceof Number || obj instanceof Boolean) {
			sb.append(prefix).append(obj.toString()).append(". ");
			return;
		}

		// If it's a collection
		if (obj instanceof Collection<?>) {
			Collection<?> collection = (Collection<?>) obj;
			for (Object item : collection) {
				serializeObject(item, sb, prefix);
			}
			return;
		}

		// If it's a map
		if (obj instanceof Map<?, ?>) {
			Map<?, ?> map = (Map<?, ?>) obj;
			for (Map.Entry<?, ?> entry : map.entrySet()) {
				sb.append(prefix).append(entry.getKey()).append(": ");
				serializeObject(entry.getValue(), sb, prefix + entry.getKey() + " ");
			}
			return;
		}

		// If it's an array
		if (clazz.isArray()) {
			int length = java.lang.reflect.Array.getLength(obj);
			for (int i = 0; i < length; i++) {
				serializeObject(java.lang.reflect.Array.get(obj, i), sb, prefix);
			}
			return;
		}

		// Otherwise, assume it's a custom object → reflect fields
		for (Field field : clazz.getDeclaredFields()) {
			field.setAccessible(true);
			try {
				Object value = field.get(obj);
				if (value != null) {
					String fieldName = field.getName();
					sb.append(prefix).append(fieldName).append(": ");
					serializeObject(value, sb, prefix + fieldName + " ");
					sb.append("\n");
				}
			} catch (IllegalAccessException e) {
				// ignore inaccessible fields
			}
		}
	}

}
