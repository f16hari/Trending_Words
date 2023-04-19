package com.hariharanj.server.dfs;

public interface IDistFileSystem {
    boolean FileExists(String fileName) throws Exception;
    void CreateFile(String fileName) throws Exception;
    String ReadFile(String fileName) throws Exception;
    void AppendInFile(String fileName, String data) throws Exception;
    void TruncateFile(String fileName) throws Exception;
    void DeleteFile(String fileName) throws Exception;
    String getPath(String fileName) throws Exception;
    void MergeFiles(String directory, String filter, String outputFile) throws Exception;
}
