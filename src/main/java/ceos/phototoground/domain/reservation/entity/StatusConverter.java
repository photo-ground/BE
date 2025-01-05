package ceos.phototoground.domain.reservation.entity;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.Arrays;

@Converter(autoApply = true)
public class StatusConverter implements AttributeConverter<Status, String> {
    @Override
    public String convertToDatabaseColumn(Status status) {
        return status.getName(); // 한글 이름 저장
    }

    @Override
    public Status convertToEntityAttribute(String dbData) {
        return Arrays.stream(Status.values())
                     .filter(status -> status.getName().equals(dbData)) // 한글로 매핑
                     .findFirst()
                     .orElseThrow(() -> new IllegalArgumentException("Unknown status: " + dbData));
    }
}

