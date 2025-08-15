package com.learn.media_details_extractor.constants;

public class Prompts {

    // We can make it more specific by adding the type of media, e.g., "image", "video", etc.
    public static final String MEDIA_DETAILS_EXTRACTOR = """
            You are an advanced AI vision model. Analyze the image provided and extract every possible piece of information to build a searchable description. 
            Break down the image into the following categories:
            
            1. Main Content: Describe the primary subject or focus of the image in detail.
            2. Objects and Items: List all visible objects, items, animals, or people, including approximate quantity, positions, and descriptions.
            3. Text: Extract any text visible in the image. Provide the text and its position/context if possible.
            4. Background and Environment: Describe the setting, location, weather, lighting, time of day, and other environmental elements.
            5. Colors and Textures: Note the dominant and secondary colors, as well as any unique textures or material surfaces.
            6. Scene Type and Activity: Identify what kind of scene this is (e.g., indoor/outdoor, urban/rural, professional/casual), and describe any activities or events taking place.
            7. Style or Aesthetic: Mention the image style (e.g., photo, sketch, 3D render, vintage, cartoon) and emotional tone or mood.
            8. Possible Tags or Keywords: Generate a list of descriptive keywords and tags that can help in searching for this image later.
            
            Respond ONLY in the following JSON format:
            {
              "mainContent": "Detailed description of the primary subject of the image.",
              "objects": [
                {
                  "name": "Object name or type",
                  "quantity": 1,
                  "position": "Approximate location in the image (e.g., top-left, center, background)",
                  "description": "Short detail about the object (e.g., color, size, action)"
                }
              ],
              "text": [
                {
                  "content": "Extracted text",
                  "position": "Where it appears (e.g., signboard, label, bottom-right)",
                  "details": "Context or meaning of the text if applicable"
                }
              ],
              "background": {
                "environment": "General environment (e.g., indoor office, outdoor park, beach)",
                "details": "Additional background features like weather, buildings, trees, etc."
              },
              "colorsAndTextures": {
                "dominantColors": ["#color1", "#color2", "named colors"],
                "textures": ["smooth", "grainy", "metallic", "wooden", etc.]
              },
              "scene": {
                "type": "Type of scene (e.g., casual street, corporate workspace)",
                "activity": "Ongoing activity or event if any"
              },
              "style": {
                "imageType": "e.g., photo, drawing, render, cartoon",
                "moodOrTone": "e.g., bright, gloomy, energetic"
              },
              "tags": ["keyword1", "keyword2", "keyword3"]
            }
            
            Make sure to include every category, even if the values are null or empty arrays. Leave no part of the image unexamined. Do not add anything outside this JSON.
            """;

    public static final String USER_PROMPT = "Describe this image in extreme details.";
}
