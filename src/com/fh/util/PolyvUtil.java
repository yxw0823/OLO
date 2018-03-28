package com.fh.util;

import java.util.List;

import com.polyv.sdk.PolyvSDKClient;
import com.polyv.sdk.Video;

import net.polyv.Progress;
import net.polyv.UploadListener;

public class PolyvUtil {
    private static Logger logger = Logger.getLogger(PolyvUtil.class);
    private static class PolyvUtilHolder{  
        public static PolyvUtil instance = new PolyvUtil();  
    }  
    private PolyvUtil(){
        PolyvSDKClient client = PolyvSDKClient.getInstance();
        client.setReadtoken("58abfd29-077c-47e4-9dea-f6cf08ee409e");
        client.setWritetoken("bd4ea0b2-55e6-481c-8d7b-37c35bff0909");
        client.setSecretkey("bH6gMpUM9t");
        client.setUserid("32b7e66cf3");
    }  
    public static PolyvUtil getPolyvUtil(){  
        return PolyvUtilHolder.instance;  
    }  
    
    /**
     * 断点续传上传实例
     */
    public String  resumableUpload(String filename,String title,String tag,String desc,long cataid){
        PolyvSDKClient client = PolyvSDKClient.getInstance();
        String vid = "";
        try {
            vid = client.resumableUpload(filename, title, tag, desc, cataid,new Progress(){
                public void run(long offset, long max) {
                    // TODO Auto-generated method stub
                    int percent = (int)(offset*100/max);
                 //   System.out.println(percent);
                }
                
                
            },new UploadListener() {
                
                @Override
                public void success(String body) {
                 //   System.out.println("上传成功："+body);
                    logger.info("上传成功："+body);
                }
                
                @Override
                public void fail(Exception ex) {
                  //  System.out.println("上传失败："+ex.getLocalizedMessage());
                    logger.error("上传失败："+ex.getLocalizedMessage());
                }
            });
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        
        return vid;
      //  System.out.println(vid);
    }
    public  void testGet() {
        try {
            Video v = PolyvSDKClient.getInstance().getVideo("01b768ec8cd3226845437dd10b93fc06_0");
            System.out.println(v.getStatus());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public  void testUpload() {
        Thread t = new Thread(new Runnable() {
            public void run() {
                Video v;
                try {
                    v = PolyvSDKClient.getInstance().upload("/Users/hhl/Downloads/test.avi",
                            "我的标题", "tag", "desc", 0);
                    System.out.println(v.getFirstImage());
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        });
        t.start();
        
        while(true){
            int percent = PolyvSDKClient.getInstance().getPercent();
            if(percent==100){
                break;
            }
            System.out.println("upload percent: " + percent + "%");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        

    }

    public  void testDeleteVideo() {
        try {

            boolean result = PolyvSDKClient.getInstance().deleteVideo(
                    "sl8da4jjbxa1077082a56e35adef93c4_s");
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public  void testListVideo() {
        try {
            List<Video> list = PolyvSDKClient.getInstance().getVideoList(1, 20);
            for (int i = 0; i < list.size(); i++) {
                Video v = list.get(i);
                System.out.println(v.getVid() + "/" + v.getTitle());
            }
            System.out.println("----查看结束----");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
