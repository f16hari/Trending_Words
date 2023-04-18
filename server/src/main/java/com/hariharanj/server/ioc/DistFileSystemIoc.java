package com.hariharanj.server.ioc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hariharanj.server.dfs.IDistFileSystem;
import com.hariharanj.server.dfs.hdfs.HadoopDistFileSystem;

@Configuration
public class DistFileSystemIoc {
    @Bean
    public IDistFileSystem distFileSystem(){
        return new HadoopDistFileSystem();
    }
}