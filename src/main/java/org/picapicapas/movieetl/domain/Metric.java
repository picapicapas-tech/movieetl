package org.picapicapas.movieetl.domain;

import java.time.LocalDateTime;
import java.util.Objects;

public abstract class Metric {
    protected final String source;
    protected final LocalDateTime timestamp;

    public Metric(String source, LocalDateTime timestamp) {
        this.source = Objects.requireNonNull(source, "Source cannot be null");
        this.timestamp = Objects.requireNonNull(timestamp, "Timestamp cannot be null");
    }

    public String getSource() {
        return source;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
