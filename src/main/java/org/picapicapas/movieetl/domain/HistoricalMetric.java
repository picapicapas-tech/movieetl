package org.picapicapas.movieetl.domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class HistoricalMetric<T> extends Metric {
    private final T data;
    private final Class<T> dataType;

    public HistoricalMetric(T data, Class<T> dataType, String source, LocalDateTime timestamp) {
        super(source, timestamp);
        this.data = Objects.requireNonNull(data, "Metric data cannot be null");
        this.dataType = Objects.requireNonNull(dataType, "Data type cannot be null");
    }

    public T getData() {
        return data;
    }

    public Class<T> getDataType() {
        return dataType;
    }
}
