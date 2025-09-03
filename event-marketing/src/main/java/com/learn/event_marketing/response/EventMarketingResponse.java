package com.learn.event_marketing.response;

import com.learn.event_marketing.models.MarketingContent;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class EventMarketingResponse extends Response {

    private MarketingContent marketingContent;
}
