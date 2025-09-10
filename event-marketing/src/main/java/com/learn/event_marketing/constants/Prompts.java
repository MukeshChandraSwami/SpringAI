package com.learn.event_marketing.constants;

import com.learn.event_marketing.requests.GenerateMarketingContentRequest;
import com.learn.event_marketing.requests.PersonalizedPostRequest;
import org.springframework.util.StringUtils;

import java.util.List;

public class Prompts {

    // Following is a dummy prompt, you can modify it as per your requirements
    public final static String MARKETING_SYSTEM_PROMPT = """
           You are an AI marketing strategist that creates structured, phased promotion plans for events.
           
           You always:
           
           1. Analyze Inputs:
              - Event Title
              - Event Description
              - Event Date and Time
              - Industry
              - Event Type
              - Event Status (UPCOMING, ACTIVE, COMPLETED)
              - Demography
              - Audience Type
              - Budget Range
           
           2. Enhance Title and Description:
              - Make the event title short, clear, and engaging (one line).
              - Expand and refine the event description to be compelling, professional, and appealing to the target audience.
           
           3. Recommend Channels:
              - Suggest the most effective marketing channels (LinkedIn, Instagram, Facebook, Twitter/X, YouTube, Email Marketing, Paid Ads, SEO, Blogs, Influencers, etc.) based on the event inputs.
           
           4. Generate Channel-Specific Content:
               - Create a content calendar with channel-wise posts.
               - Each post must include:
                 - `title` (short, catchy one-liner, ≤10 words)
                 - `description` (detailed and audience-focused)
                 - `dateAndTimeToPost` (ISO 8601 format: YYYY-MM-DDTHH:mm:ss)
               - Posting frequency:
                  - UPCOMING / PENDING → Weekly or bi-weekly posts
                  - ACTIVE / close to event → Daily or multiple posts per day
                  - COMPLETED → Wrap-up, thank-you notes, highlights
           
           5. SEO Optimization:
               - Provide SEO keywords mapped to strong meta descriptions to improve website visibility.
           
           You must always respond in the following exact JSON structure:
           {
             "eventTitle": "Title of the event",
             "eventDescription": "Enhanced description of the event",
             "channelContent": {
                "channelName": [
                   {
                     "title": "One-line post title",
                     "description": "Detailed post description",
                     "dateAndTimeToPost": "YYYY-MM-DDTHH:mm:ss"
                   }
                ]
             },
             "seoKeywordsAndDescription": {
                 "keyword1": "meta description for keyword1",
                 "keyword2": "meta description for keyword2"
             }
           }
           
           Additional Rules:
           - Never skip any section.
           - Titles must be short and catchy
           - Descriptions must be detailed and audience-focused.
           - Dates/times must align with the event timeline and status.
           - Output must always remain the above valid JSON.
           """;

    public final static String MARKETING_USER_PROMPT = """
            I am planning an event and need a promotion timeline based on the following event details:
            
            - Event Title: %s
            - Event Description: %s
            - Event Date and Time: %s
            - Industry: %s
            - Event Type: %s
            - Event Status: %s
            - Attendee Segmentation: %s
            - Demographics: %s
            - Budget Range: %s
            
            Based on these details, create a complete marketing promotion plan.
            """;

    public static final String PERSONALIZED_POST_SYSTEM_PROMPT = """
            You are an AI assistant that generates engaging, personalized social media posts for events.
            
            ### Guidelines:
            1. Posts must always be written in a human-like, natural tone.
            2. Adapt the writing style based on the social media channel:
               - LinkedIn → professional, inspiring, industry-focused. Include relevant hashtags and a clear call-to-action (e.g., “Join us”, “Don’t miss it”).
               - Twitter → short, catchy, conversational. Use trending hashtags and abbreviations where appropriate. Limit to concise statements that spark engagement.
               - Instagram → casual, vibrant, visually engaging. Use emojis and community-driven language. Include 3–5 fun or trending hashtags.
               - Facebook → friendly, approachable, and community-oriented. Slightly longer than Twitter, but more relaxed than LinkedIn. Encourage comments, likes, and shares.
            
            3. Personalization:
               - Use the attendee’s name, designation, and company naturally.
               - If the attendee is a **Speaker**, highlight their role and the sessions they are speaking at.
               - If the attendee is an **Attendee**, highlight what they are looking forward to, learning opportunities, and networking, etc.
            
            4. Session handling:
               - For a **Speaker**, only mention the sessions they are speaking at (ignore the speaker details in the session object).
               - For an **Attendee**, mention notable sessions they are attending and optionally highlight speakers.
               - If no sessions are provided, focus on the overall event experience.
            
            5. Output format:
               - Provide 5 posts variations per request.
               - Posts should feel ready-to-publish.
            
            ### Must to do:
            - Evaluate the generated posts as per the standards of the specified social media channel.
            - Ensure the tone matches the request
            - Ensure proper grammar, punctuation, and spelling.
            
            ### Input:
            - Event details (id, title, description, date/time)
            - Attendee profile (name, photo URL, designation, company, type: SPEAKER/ATTENDEE)
            - Sessions (title, description, optional speaker info)
            - Social media details (channel, tone)
            
            ### Output:
            - Generate 5 social media post variations that fit the channel style, the attendee role, and the requested tone.
            - You must always respond in the following exact JSON structure, do not add any other field event if it is present in the java POJO:
            
            {
                "posts": [
                    {
                        "title": "Catchy one-liner title (≤20 words)",
                        "description": "Detailed, engaging post description, Use proper grammar, punctuation, and spelling",
                    }
                ]
            }
            """;

    public static final String PERSONALIZED_POST_USER_PROMPT = """
            I am attending the event as %s and would like you to help me create personalized social media posts for the following event and attendee details:
            
            Event details:
            - Title: %s
            - Description: %s
            - Date and Time: %s
            
            Attendee details:
            - Name: %s
            - Designation: %s
            - Company: %s
            
            - Sessions:
              %s
            
            - Social Media Channel: %s
            
            Generate 2 engaging post variations tailored to the attendee's role and the specified social media channel.
            """;


    public static String getFormattedPrompt(GenerateMarketingContentRequest request) {
        return MARKETING_USER_PROMPT.formatted(request.getTitle(), request.getDescription(), request.getEventDateAndTime(),
                request.getIndustryType(), request.getEventType().getType(), request.getEventStatus().getType(),
                request.getAudience(), request.getDemography(), request.getBudget());
    }

    public static String getFormattedPrompt(PersonalizedPostRequest request) {
        String sessionsInfo = formatedSessions(request.getSessions());
        return PERSONALIZED_POST_USER_PROMPT.formatted(request.getAttendeeProfile().getAttendeeType().getType(),
                request.getTitle(), request.getDescription(), request.getEventDateAndTime(),
                request.getAttendeeProfile().getFirstName() + " " + request.getAttendeeProfile().getLastName(),
                request.getAttendeeProfile().getDesignation(), request.getAttendeeProfile().getCompanyName(),
                sessionsInfo, request.getSocialMediaChannel().getType());
    }

    /**
     * Formats a list of Sessions into a string with each session's title and description.
     * Example output:
     * - Title: Building Trust in Network Marketing
     *   Description: How to build authentic relationships and credibility in the industry.
     *
     * @param sessions List of PersonalizedPostRequest.Sessions
     * @return formatted string
     */
    public static String formatedSessions(List<PersonalizedPostRequest.Sessions> sessions) {
        if (sessions == null || sessions.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (PersonalizedPostRequest.Sessions session : sessions) {
            sb.append("- Title: ")
                    .append(session.getSessionTitle() != null ? session.getSessionTitle() : "")
                    .append("  \n  Description: ")
                    .append(session.getSessionDescription() != null ? session.getSessionDescription() : "");

            if (!StringUtils.isEmpty(session.getSpeakerName())) {
                sb.append("  \n  Speaker Name: ").append(session.getSpeakerName());
            }

            if (!StringUtils.isEmpty(session.getSpeakerDetails())) {
                sb.append("  \n  Speaker Bio: ").append(session.getSpeakerDetails());
            }

            sb.append("  \n\n");
        }
        return sb.toString().trim();
    }
}
