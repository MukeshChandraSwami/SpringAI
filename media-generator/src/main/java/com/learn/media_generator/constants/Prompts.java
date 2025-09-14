package com.learn.media_generator.constants;

import com.learn.media_generator.requests.MediaRequest.MediaDetails;
import com.learn.media_generator.requests.PersonalizedPostRequest;
import com.learn.media_generator.requests.PersonalizedPostRequest.PostDetails;

import static com.learn.media_generator.requests.PersonalizedPostRequest.ThemeElement.BACKGROUND_COLOR;
import static com.learn.media_generator.requests.PersonalizedPostRequest.ThemeElement.CARD_COLOR;
import static com.learn.media_generator.requests.PersonalizedPostRequest.ThemeElement.PRIMARY_COLOR;
import static com.learn.media_generator.requests.PersonalizedPostRequest.ThemeElement.SECONDARY_COLOR;

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

    public static final String PERSONALIZED_POST_PROMPT = "You are an AI assistant that generates social media-ready event images.";

    public static final String PERSONALIZED_POST_PROMPT_1 = """
            You are an AI assistant that generates social media-ready event images.
            
            ### Image Generation Guidelines:
            1. Event branding:
               - Display the event title and date clearly.
               - Optionally include a short tagline or phrase from the event description.
            
            2. Attendee personalisation:
               - Always include attendee's full name, designation, and company.
               - Ensure personalisation integrates naturally with the overall layout.
            
            3. Styling & Colors:
               - I will provide 4 colors in hex formate:- Primary and secondary colors are the main brand colors used to style key elements across an interface.
                    - Primary Color → The primary color typically appears on buttons and links, representing the main action or brand identity.
                    - Secondary Color → The secondary color adds visual contrast or flair, such as highlighting timelines or accent elements.
                    - Background Color -> Background color is the base color for large areas on a image, setting the overall tone.
                    - Card color -> Card color is used for any containers or cards within a image, helping that block stand out from the main background.
               - This image will be posted on %s. Its tone should be %s.
            
            4. Output:
               - Generate a polished and social-media ready image.
               - Do not include any explanatory text outside of the designed content.
            
            I am attending the event as %s and would like you to create a high-quality, visually engaging image personalised for the given attendee and event.
            
            Event details:
            - Title: %s
            - Description: %s
            - Date and Time: %s
            
            Attendee details:
            - Name: %s
            - Designation: %s
            - Company: %s
            
            Colors Details:
            - Primary Color: %s
            - Secondary Color: %s
            - Background Color: %s
            - Card Color: %s
            """;

    public static final String PERSONALIZED_POST_PROMPT_LINKEDIN = """
            You are an AI assistant that generates social media-ready event images.
            
            Create a professional LinkedIn-style event announcement image.
            Tone: Enthusiastic, professional, community-driven, and celebratory.
            
            Details to include:
            - Event Title: %s
            - Event Date: %s
            - Personalization Text: "%s"
            - Name: %s
            - Designation: %s
            - Company: %s
            
            Design style:
            - Clean, modern, and minimal with a professional color palette (%s, %s, %s, %s).
            - Abstract geometric shapes, gradients, and subtle patterns for a festive but professional look.
            - Event details neatly at the top.
            - Space for event logo in the top-right.
            - Optional: Highlight role with a small badge or ribbon to emphasize contribution.
            - Should feel celebratory, shareable, and LinkedIn-appropriate.
            - Use event description to narrow down the design context (Do not include it in the image): %s
            """;

    public static final String PERSONALIZED_POST_PROMPT_FACEBOOK = """
            You are an AI assistant that generates social media-ready event images.
            
            Create a Facebook-style event announcement image.
            Tone: Friendly, enthusiastic, social, and community-driven.
            
            Details to include:
            - Event Title: %s
            - Event Date: %s
            - Personalization Text: "%s"
            - Name: %s
            - Designation: %s
            - Company: %s
            
            Design style:
            - Bright, colorful, and engaging with a social feel with following color combination:- (%s, %s, %s, %s).
            - Use lively shapes, gradients, and soft backgrounds.
            - Event details shown casually at the top or bottom.
            - Event logo included in the design.
            - Should feel welcoming, festive, and Facebook-friendly.
            - Use event description to narrow down the design context (Do not include it in the image): %s
            """;
            // - Add fun elements for a celebratory effect.

    public static final String PERSONALIZED_POST_PROMPT_TWITTER = """
            You are an AI assistant that generates social media-ready event images.
            
            Create a Twitter/X-style event announcement image.
            Tone: Bold, energetic, concise, and shareable.
            
            Details to include:
            - Event Title: %s
            - Event Date: %s
            - Personalization Text: "%s"
            - Name: %s
            - Designation: %s
            - Company: %s
            
            Design style:
            - Eye-catching, high-contrast visuals using a combination of the following colors (%s, %s, %s, %s)
            - Event details kept concise, shown at the top or bottom.
            - Event logo in a corner.
            - Use dynamic elements to add energy.
            - Should feel fast, engaging, and Twitter-friendly.
            - Use event description to narrow down the design context (Do not include it in the image): %s
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
        return PERSONALIZED_POST_PROMPT.formatted(request.getSocialMediaChannel().getType(),
                request.getAttendeeProfile().getAttendeeType() == PersonalizedPostRequest.AttendeeType.ATTENDEE
                        ? request.getSocialMediaChannel().getAttendeeTone()
                        : request.getSocialMediaChannel().getSpeakerTone(),
                request.getAttendeeProfile().getAttendeeType().getType(), request.getTitle(), request.getDescription(), request.getEventDateAndTime(),
                request.getAttendeeProfile().getFirstName() + " " + request.getAttendeeProfile().getLastName(), request.getAttendeeProfile().getDesignation(),
                request.getAttendeeProfile().getCompanyName(), request.getThemingDetails().get(PRIMARY_COLOR), request.getThemingDetails().get(SECONDARY_COLOR),
                request.getThemingDetails().get(BACKGROUND_COLOR), request.getThemingDetails().get(CARD_COLOR));
    }

    public static String gerFormattedPromptForLinkedIN(PersonalizedPostRequest request) {
        return PERSONALIZED_POST_PROMPT_LINKEDIN.formatted(request.getTitle(), request.getEventDateAndTime(),
                request.getAttendeeProfile().getAttendeeType() == PersonalizedPostRequest.AttendeeType.ATTENDEE
                        ? "I'm Joining"
                        : "I'm Speaking",
                request.getAttendeeProfile().getFirstName() + " " + request.getAttendeeProfile().getLastName(),
                request.getAttendeeProfile().getDesignation(), request.getAttendeeProfile().getCompanyName(),
                request.getThemingDetails().get(PRIMARY_COLOR), request.getThemingDetails().get(SECONDARY_COLOR),
                request.getThemingDetails().get(BACKGROUND_COLOR), request.getThemingDetails().get(CARD_COLOR),
                request.getDescription()
                );
    }

    public static String gerFormattedPromptForFacebook(PersonalizedPostRequest request) {
        return PERSONALIZED_POST_PROMPT_FACEBOOK.formatted(request.getTitle(), request.getEventDateAndTime(),
                request.getAttendeeProfile().getAttendeeType() == PersonalizedPostRequest.AttendeeType.ATTENDEE
                        ? "I'm Joining"
                        : "I'm Speaking",
                request.getAttendeeProfile().getFirstName() + " " + request.getAttendeeProfile().getLastName(),
                request.getAttendeeProfile().getDesignation(), request.getAttendeeProfile().getCompanyName(),
                request.getThemingDetails().get(PRIMARY_COLOR), request.getThemingDetails().get(SECONDARY_COLOR),
                request.getThemingDetails().get(BACKGROUND_COLOR), request.getThemingDetails().get(CARD_COLOR),
                request.getDescription()
        );
    }

    public static String gerFormattedPromptForTwitter(PersonalizedPostRequest request) {
        return PERSONALIZED_POST_PROMPT_TWITTER.formatted(request.getTitle(), request.getEventDateAndTime(),
                request.getAttendeeProfile().getAttendeeType() == PersonalizedPostRequest.AttendeeType.ATTENDEE
                        ? "I'm Joining"
                        : "I'm Speaking",
                request.getAttendeeProfile().getFirstName() + " " + request.getAttendeeProfile().getLastName(),
                request.getAttendeeProfile().getDesignation(), request.getAttendeeProfile().getCompanyName(),
                request.getThemingDetails().get(PRIMARY_COLOR), request.getThemingDetails().get(SECONDARY_COLOR),
                request.getThemingDetails().get(BACKGROUND_COLOR), request.getThemingDetails().get(CARD_COLOR),
                request.getDescription()
        );
    }
}
