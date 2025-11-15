package org.picapicapas.movieetl.pipeline;

import org.picapicapas.movieetl.domain.DataSource;
import org.picapicapas.movieetl.providers.common.DataExtractor;
import org.picapicapas.movieetl.providers.common.DataNormalizer;

import java.io.File;

public interface Provider {
    
    DataSource getDataSource();
    
    File getDataFile();
    
    <T> DataExtractor<T> getExtractor();
    
    <T> DataNormalizer<T> getNormalizer();
}
