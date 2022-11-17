package com.example.demo2;
import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Builder
@NonNull
@RequiredArgsConstructor
public class DemoModel {
    @NonNull
    private String id;
}
