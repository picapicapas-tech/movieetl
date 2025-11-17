package org.picapicapas.movieetl.pipeline;

import org.picapicapas.movieetl.domain.DataSource;
import org.picapicapas.movieetl.providers.common.DataExtractor;
import org.picapicapas.movieetl.providers.common.DataNormalizer;

import java.io.File;
import java.util.List;

public interface Provider {
    
    String DATA_DIR_ENV_VAR = "MOVIE_DATA_DIR";
    
    DataSource getDataSource();
    
    List<File> getDataFiles();
    
    <T> List<DataExtractor<T>> getExtractors();
    
    <T> List<DataNormalizer<T>> getNormalizers();
}
