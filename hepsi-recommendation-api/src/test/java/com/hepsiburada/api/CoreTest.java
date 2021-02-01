package com.hepsiburada.api;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Tag("core")
@DisplayName("Core Tests")
@ExtendWith(MockitoExtension.class)
@Retention(RetentionPolicy.RUNTIME)
@MockitoSettings(strictness = Strictness.LENIENT)
public @interface CoreTest {
}
