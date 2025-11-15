# Movie Score Data Pipeline

A data pipeline application that ingests, cleans, standardizes, and combines movie data from multiple providers to create a unified, queryable dataset.

## Overview

This project processes movie scoring data from three different providers:
- **Provider 1 (CriticAgg)**: CSV file with critic scores
- **Provider 2 (AudiencePulse)**: JSON file with audience ratings and box office data
- **Provider 3 (BoxOfficeMetrics)**: Three CSV files with domestic/international box office and financial data

The pipeline produces a unified `UnifiedMovie` data structure that can be easily queried by the data science team.

## Project Structure

```
src/main/java/org/picapicapas/movieetl/
├── domain/                 # Core data models
│   ├── DataSource.java    # Enum tracking data provider sources
│   ├── MovieKey.java      # Immutable movie identifier (title + year)
│   └── UnifiedMovie.java  # Aggregated movie data from all sources
├── pipeline/
│   └── MovieDataPipeline.java  # Main orchestrator
├── providers/
│   └── common/            # Base interfaces and utilities
│       ├── DataExtractor.java  # Generic extraction interface
│       ├── DataNormalizer.java # Generic transformation interface
│       └── FileReader.java     # Base file reading class
└── utils/
    └── ValidationUtils.java    # Data validation utilities
```

## Building the Project

### Prerequisites
- Java 25+
- Maven 3.8+

### Build Command
```bash
mvn clean compile
```

### Run the Application
```bash
mvn exec:java
```

## Dependencies

- **Jackson 2.16.1** - JSON parsing
- **Apache Commons CSV 1.10.0** - CSV parsing
- **JUnit 4.13.2** - Testing framework
- **SLF4J 2.0.9** - Logging

## Architecture

The pipeline follows a layered, extensible architecture:

1. **Data Layer** - File I/O and reading
2. **Extraction Layer** - Provider-specific parsing to raw records
3. **Transformation Layer** - Normalization, cleaning, and standardization
4. **Integration Layer** - Deduplication and data merging
5. **Output Layer** - Unified, queryable in-memory dataset

## Future Enhancements

- Provider 4 and beyond (plug-and-play with new extractors and normalizers)
- Schema versioning for handling provider changes
- Persistent storage (database integration)
- REST API for data access
- Data quality reporting and monitoring

## Key Input and Output classes

- The output/result will be stored in a UnifiedMovie object. The data structure is meant to store the whole set of upcoming information. Since data for the same category and movie can come from different providers (we can't even exclude the same provider sending updated data over time), all the information is available for consumers and it's up them if they want the whole history, the latest, any average or any other logic they may want to apply.

- For the input, MovieDataPipelineOrchestrator has a main() method that can be run to ilustrate the results with the sample provided files.
