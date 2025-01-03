package com.study.cmd_chat_assistant.services;

import java.util.Map;
import java.util.function.Function;

public class HotelBookingServiceFunctionCalling implements Function<HotelBookingServiceFunctionCalling.BookingRequest, HotelBookingServiceFunctionCalling.BookingResponse> {

    public record BookingRequest(String bookingId) {}

    public record BookingResponse(String bookingId, String bookingStatus) {}

    public enum Status {
        PENDING, SUCCESS, FAILURE, UNKNOWN
    }

    Map<String, Status> bookingStatusMap = Map.of(
        "H1", Status.PENDING,
        "H2", Status.SUCCESS,
        "H3", Status.FAILURE
    );

    @Override
    public BookingResponse apply(BookingRequest bookingRequest) {
        Status status = bookingStatusMap.getOrDefault(bookingRequest.bookingId(), Status.UNKNOWN);
        return new BookingResponse(bookingRequest.bookingId(), status.name());
    }
}
