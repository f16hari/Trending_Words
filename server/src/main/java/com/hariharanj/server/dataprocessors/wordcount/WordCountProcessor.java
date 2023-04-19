package com.hariharanj.server.dataprocessors.wordcount;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.springframework.context.support.GenericApplicationContext;

import com.hariharanj.server.ApplicationContext;
import com.hariharanj.server.dfs.IDistFileSystem;

public class WordCountProcessor {
    private static GenericApplicationContext context = ApplicationContext.getInstance();

    public static void performWordCount(String topic) throws Exception{

        IDistFileSystem dfs = context.getBean("distFileSystem", IDistFileSystem.class);
        String inputFileName = topic + ".txt";
        String outputDirectory = topic + "_wordCount";
        dfs.DeleteFile(outputDirectory);
        
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "word count");
        job.setJarByClass(WordCountProcessor.class);
        job.setMapperClass(TokenizedMapper.class);
        job.setCombinerClass(WordCountReducer.class);
        job.setReducerClass(WordCountReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        job.setNumReduceTasks(1);
        FileInputFormat.addInputPath(job, new Path(dfs.getPath(inputFileName)));
        FileOutputFormat.setOutputPath(job, new Path(dfs.getPath(outputDirectory)));
        job.waitForCompletion(true);

        dfs.MergeFiles(outputDirectory, "part-*", topic + "wordCount.txt");
        dfs.DeleteFile(outputDirectory);
    }

    public static String getProcessedResult(String topic) throws Exception{
        IDistFileSystem dfs = context.getBean("distFileSystem", IDistFileSystem.class);

        return dfs.ReadFile(topic + "wordCount.txt");
    }
}