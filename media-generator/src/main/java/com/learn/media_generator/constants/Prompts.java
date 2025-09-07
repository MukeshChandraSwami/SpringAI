package com.learn.media_generator.constants;

import com.learn.media_generator.requests.MediaRequest.MediaDetails;
import com.learn.media_generator.requests.PersonalizedPostRequest;
import com.learn.media_generator.requests.PersonalizedPostRequest.PostDetails;

public class Prompts {

    public final static String MEDIA_GENERATOR_PROMPT = """
            You are an AI assistant specialized in generating event promotion images for social media platforms like LinkedIn, Twitter, and Instagram.
            
            Generate promotional images with the following event details:
            
            1. Event Title: %s
            2. Event Description: %s
            3. Event Date and Time: %s
            4. Registration Link: %s
            5. Industry: %s
            6. Event Type: %s
            7. Event Status: %s
            8. Demography: %s
            9. Audience Type: %s
            10. Social Media Type: %s
            11. Social Media Post Title: %s
            12. Social Media Post Content: %s
            
            Your task is to generate at least 2 different, high-quality promotional images for each event, optimized for professional marketing.
            
            Guidelines
            1. Provide 2 or more visually distinct variations (different layouts, colors, or visual styles).
            2. The design must be professional, clean, and eye-catching.
            3. Style and theme should align with the given industry and audience type.
            4. Text overlay should include the event title and optionally the social media post title.
            5. Imagery should reflect the event type (e.g., webinar = digital/online theme, conference = stage/audience, health = medical visuals, tech = futuristic/innovation).
            6. Adapt the design for a global professional audience unless a specific demography is provided.
            7. Ensure readability and proper balance between text and visuals.
            8. Generate images in dimensions optimized for LinkedIn, Twitter, and Instagram posts.
            9. Include register now button or call to action if a registration link is provided.
            10. Use colors and fonts that are consistent with professional branding standards.
            11. Avoid overly complex designs that may distract from the event information.
            12. Ensure the images are suitable for use in paid promotions and organic posts.
            13. Avoid using any copyrighted material or logos unless explicitly provided.
            14. Ensure the image should reflect the event status (e.g., upcoming, active, completed).
            """;
    
    public static final String PERSONALIZED_POST_PROMPT = """
            You are an AI assistant that generates social media-ready event images.
            I am attending the event as %s and would like you to create a high-quality, visually engaging image personalized for the given attendee and event.
            
            Event details:
            - Title: %s
            - Description: %s
            - Date and Time: %s
            
            Attendee details:
            - Name: %s
            - Designation: %s
            - Company: %s
            
            - Social Media Channel: %s
            
            ### Image Generation Guidelines:
            1. Event branding:
               - Display the event title and date clearly.
               - Optionally include a short tagline or phrase from the event description.
               - Use the event’s theme or domain (e.g., technology, healthcare, marketing, leadership) as inspiration for background visuals.
            
            2. Attendee personalization:
               - Always include attendee’s full name, designation, and company.
               - Apply the runtime personalization details → %s
            
            3. Channel-specific adaptation:
               - LinkedIn → professional, sleek, corporate look with muted tones.
               - Twitter → bold, minimal, strong typography.
               - Instagram → colorful, vibrant, playful, visually dynamic.
               - Facebook → approachable, community-focused, warm design.
            
            4. Optional common enhancements:
               - If attendee’s photo URL is available, integrate it seamlessly into the design.
               - If no photo is available, generate a polished template that still highlights their details.
            
            5. Output:
               - Generate a polished event image that feels professional and social-media ready.
               - Do not include any explanatory text outside of the designed content.
            """;

    public static String getFormattedPrompt(MediaDetails request) {
        return MEDIA_GENERATOR_PROMPT.formatted(request.getTitle(), request.getDescription(),
                request.getEventDateAndTime(), request.getRegistrationLink(), request.getIndustry(),
                request.getEventType().getType(), request.getEventStatus().getType(),
                request.getDemography(), request.getAudience(),
                request.getSocialMediaPlatform(), request.getSocialMediaPostTitle(),
                request.getSocialMediaPostContent());
    }

    public static String getFormattedPrompt(PersonalizedPostRequest request, PostDetails post) {
        return PERSONALIZED_POST_PROMPT.formatted(request.getAttendeeProfile().getAttendeeType(),
                request.getTitle(), request.getDescription(), request.getEventDateAndTime(),
                request.getAttendeeProfile().getFirstName() + " " + request.getAttendeeProfile().getLastName(),
                request.getAttendeeProfile().getDesignation(), request.getAttendeeProfile().getCompanyName(),
                request.getSocialMediaChannel(), request.getThemingDetails());
    }
}
