package com.hariharanj.server.dfs.hdfs;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import com.hariharanj.server.dfs.IDistFileSystem;

public class HadoopDistFileSystem implements IDistFileSystem{
    public final static String HadoopWorkingDirectory = "Trending_Words";
    public final static String HadoopURI = "hdfs://localhost:9000";
    public final static boolean CleanDirectory = true;

    private FileSystem Hdfs;
    
    public HadoopDistFileSystem(){
        try{
            Configuration config = new Configuration();
            config.setBoolean("dfs.support.append", true);

            Hdfs = FileSystem.get(URI.create(HadoopURI), config);

            if(Hdfs.exists(new Path(HadoopWorkingDirectory))){
                if(CleanDirectory){
                    Hdfs.delete(new Path(HadoopWorkingDirectory), true);
                }
            }
            else{
                Hdfs.mkdirs(new Path(HadoopWorkingDirectory));
            }
        }
        catch(IOException e){
            System.out.println(HadoopURI + " can't be initiated!!!");
        }
    }

    public boolean FileExists(String fileName) throws Exception{
        return Hdfs.exists(new Path(HadoopWorkingDirectory, fileName));
    }

    public void CreateFile(String fileName) throws Exception{
        if(FileExists(fileName)){
            DeleteFile(fileName);
        }

        if(Hdfs.createNewFile(new Path(HadoopWorkingDirectory, fileName))){
            Hdfs.setReplication(new Path(HadoopWorkingDirectory, fileName), (short) 1);
        }
    }

    public String ReadFile(String fileName) throws Exception{
        if(!FileExists(fileName)){
            throw new Exception(fileName + " Does not Exists!!");
        }

        FSDataInputStream inputStream = Hdfs.open(new Path(HadoopWorkingDirectory, fileName));
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

        String content;
        StringBuilder builder = new StringBuilder();
        while((content = br.readLine()) != null){
            builder.append(content);
        }

        inputStream.close();
        br.close();
        return builder.toString();
    }

    public void AppendInFile(String fileName, String data) throws Exception{
        if(!FileExists(fileName)){
            CreateFile(fileName);
        }

        FSDataOutputStream outputStream = Hdfs.append(new Path(HadoopWorkingDirectory, fileName));
        outputStream.writeBytes(" " + data);

        outputStream.close();
    }

    public void TruncateFile(String fileName) throws Exception{
        DeleteFile(fileName);
        CreateFile(fileName);
    }

    public void DeleteFile(String fileName) throws Exception{
        if(!FileExists(fileName)){
            return;
        }

        Hdfs.delete(new Path(HadoopWorkingDirectory, fileName), true);
    }
}