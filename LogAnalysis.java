import java.io.IOException;
import java.util.StringTokenizer;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.fs.Path;
import java.util.Calendar;
import java.text.SimpleDateFormat;
 
public class LogAnalysis
{
public static class Map extends Mapper<LongWritable,Text,Text,IntWritable> {
public void map(LongWritable key, Text value,Context context) throws IOException,InterruptedException{

String[] parts= value.toString().split("\t");
String text=parts[1];
int openb=text.indexOf('[');
if(openb!=-1){
	String time=text.substring(openb+1,text.length());
	int index = 0;
    int nextIndex = time.indexOf( '/' );
    int day = Integer.parseInt(time.substring(index, nextIndex) );

    index = nextIndex;
    nextIndex = time.indexOf( '/', index+1 );
   	String month = time.substring( index+1, nextIndex );

    index = nextIndex;
    nextIndex = time.indexOf( ':', index );
    int year = Integer.parseInt(time.substring(index + 1, nextIndex));

    index = nextIndex;
    nextIndex = time.indexOf( ':', index+1 );
    int hour = Integer.parseInt(time.substring(index + 1, nextIndex));
     Calendar calendar = Calendar.getInstance();
                calendar.set( Calendar.DATE, day );
                calendar.set( Calendar.YEAR, year );
                calendar.set( Calendar.HOUR, hour );
                calendar.set( Calendar.MINUTE, 0 );
                calendar.set( Calendar.SECOND, 0 );
                calendar.set( Calendar.MILLISECOND, 0 );
     if( month.equalsIgnoreCase( "Dec" ) )
                {
                    calendar.set( Calendar.MONTH, Calendar.DECEMBER );
                }
                else if( month.equalsIgnoreCase( "Nov" ) )
                {
                    calendar.set( Calendar.MONTH, Calendar.NOVEMBER );
                }
                else if( month.equalsIgnoreCase( "Oct" ) )
                {
                    calendar.set( Calendar.MONTH, Calendar.OCTOBER );
                }
                else if( month.equalsIgnoreCase( "Sep" ) )
                {
                    calendar.set( Calendar.MONTH, Calendar.SEPTEMBER );
                }
                else if( month.equalsIgnoreCase( "Aug" ) )
                {
                    calendar.set( Calendar.MONTH, Calendar.AUGUST );
                }
                else if( month.equalsIgnoreCase( "Jul" ) )
                {
                    calendar.set( Calendar.MONTH, Calendar.JULY );
                }
                else if( month.equalsIgnoreCase( "Jun" ) )
                {
                    calendar.set( Calendar.MONTH, Calendar.JUNE );
                }
                else if( month.equalsIgnoreCase( "May" ) )
                {
                    calendar.set( Calendar.MONTH, Calendar.MAY );
                }
                else if( month.equalsIgnoreCase( "Apr" ) )
                {
                    calendar.set( Calendar.MONTH, Calendar.APRIL );
                }
                else if( month.equalsIgnoreCase( "Mar" ) )
                {
                    calendar.set( Calendar.MONTH, Calendar.MARCH );
                }
                else if( month.equalsIgnoreCase( "Feb" ) )
                {
                    calendar.set( Calendar.MONTH, Calendar.FEBRUARY );
                }
                else if( month.equalsIgnoreCase( "Jan" ) )
                {
                    calendar.set( Calendar.MONTH, Calendar.JANUARY );
                }
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SS");
    String strDate = sdf.format(calendar.getTime());

                value.set(strDate);
                context.write(value,new IntWritable(1));

}


}
}
 
public static class Reduce extends Reducer<Text,IntWritable,Text,IntWritable> {
public void reduce(Text key, Iterable<IntWritable> values,Context context) throws IOException,InterruptedException {
int sum=0;
for(IntWritable x: values)
{
sum+=x.get();
}
context.write(key, new IntWritable(sum));
}
}
 
public static void main(String[] args) throws Exception {
 
Configuration conf= new Configuration();
Job job = new Job(conf,"My Log Analysis Program");
job.setJarByClass(LogAnalysis.class);
job.setMapperClass(Map.class);
job.setReducerClass(Reduce.class);
job.setOutputKeyClass(Text.class);
job.setOutputValueClass(IntWritable.class);
job.setInputFormatClass(TextInputFormat.class);
job.setOutputFormatClass(TextOutputFormat.class);
Path outputPath = new Path(args[1]);
//Configuring the input/output path from the filesystem into the job
FileInputFormat.addInputPath(job, new Path(args[0]));
FileOutputFormat.setOutputPath(job, new Path(args[1]));
//deleting the output path automatically from hdfs so that we don't have to delete it explicitly
outputPath.getFileSystem(conf).delete(outputPath);
//exiting the job only if the flag value becomes false
System.exit(job.waitForCompletion(true) ? 0 : 1);
}
}