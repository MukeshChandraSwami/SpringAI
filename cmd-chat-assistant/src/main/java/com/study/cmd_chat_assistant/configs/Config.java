package com.study.cmd_chat_assistant.configs;

import com.study.cmd_chat_assistant.services.HotelBookingServiceFunctionCalling;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;

import java.util.function.Function;

@Configuration
public class Config {

    @Bean("hotelBookingService")
    @Description("This is a bean for fetching the status of booking by booking id")
    public Function<HotelBookingServiceFunctionCalling.BookingRequest,
            HotelBookingServiceFunctionCalling.BookingResponse> hotelBookingServiceFunctionCalling() {
        return new HotelBookingServiceFunctionCalling();
    }
}
