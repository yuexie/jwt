package com.xieyue.jwt.Tool;

/**
 * @program: jwt
 * @description:
 * @author: xieyue
 * @create: 2020-08-14 14:43
 **/

import java.io.*;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel.MapMode;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

public class BigFileReader {
    private int threadSize;
    private String charset;
    private int bufferSize;
    private IHandle handle;
    private ExecutorService  executorService;
    private long fileLength;
    private RandomAccessFile rAccessFile;
    private Set<StartEndPair> startEndPairs;
    private CyclicBarrier cyclicBarrier;
    private AtomicLong counter = new AtomicLong(0);

    private BigFileReader(File file,IHandle handle,String charset,int bufferSize,int threadSize){
        this.fileLength = file.length();
        this.handle = handle;
        this.charset = charset;
        this.bufferSize = bufferSize;
        this.threadSize = threadSize;
        try {
            this.rAccessFile = new RandomAccessFile(file,"r");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        this.executorService = Executors.newFixedThreadPool(threadSize);
        startEndPairs = new HashSet<BigFileReader.StartEndPair>();
    }

    public void start(){
        System.out.println(this.fileLength);
        long everySize = this.fileLength/this.threadSize;
        try {
            calculateStartEnd(0, everySize);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        final long startTime = System.currentTimeMillis();
        cyclicBarrier = new CyclicBarrier(startEndPairs.size(),new Runnable() {

            @Override
            public void run() {
                System.out.println("use time: "+(System.currentTimeMillis()-startTime));
                System.out.println("all line: "+counter.get());
            }
        });
        for(StartEndPair pair:startEndPairs){
            System.out.println("分配分片："+pair);
            this.executorService.execute(new SliceReaderTask(pair));
        }
    }

    private void calculateStartEnd(long start,long size) throws IOException{
        if(start>fileLength-1){
            return;
        }
        StartEndPair pair = new StartEndPair();
        pair.index = startEndPairs.size() + 1;
        pair.start = start;
        long endPosition = start+size-1;
        if(endPosition>=fileLength-1){
            pair.end=fileLength-1;
            startEndPairs.add(pair);
            return;
        }

        rAccessFile.seek(endPosition);
        byte tmp =(byte) rAccessFile.read();
        while(tmp!='\n' && tmp!='\r'){
        //while(tmp!='-'){
            endPosition++;
            if(endPosition>=fileLength-1){
                endPosition=fileLength-1;
                break;
            }
            rAccessFile.seek(endPosition);
            tmp =(byte) rAccessFile.read();
        }
        pair.end=endPosition;
        startEndPairs.add(pair);

        calculateStartEnd(endPosition+1, size);

    }



    public void shutdown(){
        try {
            this.rAccessFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.executorService.shutdown();
    }
    private void handle(byte[] bytes) throws IOException {
        String line = null;
        if(this.charset==null){
            line = new String(bytes);
        }else{
            line = new String(bytes,charset);
        }
        if(line!=null && !"".equals(line)){
            this.handle.handle(line);
            counter.incrementAndGet();
        }
    }
    private static class StartEndPair{
        public long  index = 0;
        public long  start;
        public long  end;
        public long  size;

        @Override
        public String toString() {
            return "index="+index+"; star="+start+"; end="+end + "; size=" + (end-start);
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + (int) (end ^ (end >>> 32));
            result = prime * result + (int) (start ^ (start >>> 32));
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            StartEndPair other = (StartEndPair) obj;
            if (end != other.end)
                return false;
            if (start != other.start)
                return false;
            return true;
        }

    }
    private class SliceReaderTask implements Runnable{
        private long start;
        private long sliceSize;
        private byte[] readBuff;
        /**
         * @param start 	read position (include)
         * @param end 	the position read to(include)
         */
        public SliceReaderTask(StartEndPair pair) {
            this.start = pair.start;
            this.sliceSize = pair.end-pair.start+1;
            this.readBuff = new byte[bufferSize];
        }

        @Override
        public void run() {

            // 输出缓冲流
            BufferedWriter writer   = null;
            String tableName        = "" + Thread.currentThread().getId();

            try {

                MappedByteBuffer mapBuffer = rAccessFile.getChannel().map(MapMode.READ_ONLY,start, this.sliceSize);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                for(int offset=0;offset<sliceSize;offset+=bufferSize){
                    int readLength;
                    if(offset+bufferSize<=sliceSize){
                        readLength = bufferSize;
                    }else{
                        readLength = (int) (sliceSize-offset);
                    }
                    mapBuffer.get(readBuff, 0, readLength);
                    for(int i=0;i<readLength;i++){
                        byte tmp = readBuff[i];
                        if(tmp=='\n'||tmp=='\r'){//按行输出
                            handle(bos.toByteArray());



                            //在各自线程中写入分片的文件
                            String line = new String(bos.toByteArray());
                            if(line.indexOf("log") == -1){
                                if(line.indexOf("Table structure for") != -1){
                                    tableName = "" + Thread.currentThread().getId() + "_" +
                                            line.replace("Table structure for", "")
                                                    .replaceAll("\r|\n", "")
                                                    .replace("-","")
                                                    .replace(" ","") ;
                                    //System.out.println(tableName);
                                }

                                String fileName = "D:\\Code\\data\\sql\\" + tableName + ".sql";

                                // 使用 BufferedWriter 如果 不进行 flush 或者 close 写入不了内容。
                                writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName, true)));
                                writer.write(line + System.lineSeparator());
                                writer.flush();
                            }



                            bos.reset();
                        }else{
                            bos.write(tmp);
                        }
                    }

                    /*

                    String sql = new String(readBuff);
                    String fileName = "D:\\logs\\elec\\" + Thread.currentThread().getId() + ".sql";
                    // 使用 BufferedWriter 如果 不进行 flush 或者 close 写入不了内容。
                    writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName, true)));
                    writer.write(sql);
                    writer.flush();


                     */

                }
                if(bos.size()>0){
                    handle(bos.toByteArray());
                }
                cyclicBarrier.await();//测试性能用
            }catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public static class Builder{
        private int threadSize=1;
        private String charset=null;
        private int bufferSize=1024*1024;
        private IHandle handle;
        private File file;
        public Builder(String file,IHandle handle){
            this.file = new File(file);
            if(!this.file.exists())
                throw new IllegalArgumentException("文件不存在！");
            this.handle = handle;
        }

        public Builder withTreahdSize(int size){
            this.threadSize = size;
            return this;
        }

        public Builder withCharset(String charset){
            this.charset= charset;
            return this;
        }

        public Builder withBufferSize(int bufferSize){
            this.bufferSize = bufferSize;
            return this;
        }

        public BigFileReader build(){
            return new BigFileReader(this.file,this.handle,this.charset,this.bufferSize,this.threadSize);
        }
    }


}
