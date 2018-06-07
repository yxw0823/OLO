package com.fh.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ImgUploudUtil
{
	public  static List<String> uploud(MultipartFile[] files,String upPath,String basePath){
		List<String> imgUrlList= new ArrayList<String>();
		String ffile = DateUtil.getDays(), fileName = "";
        String filePath = "";// 文件上传路径
        String fileNameUUid =  UuidUtil.get32UUID();
        filePath = PathUtil.getClasspath() + Const.FILEPATHIMG + ffile; // 文件上传路径
        List<String> list = new ArrayList<String>();
        for(MultipartFile file:files ){
            // 校验权限
            if (null != file && !file.isEmpty()) {
                fileName = FileUpload.fileUp(file, filePath, fileNameUUid); // 执行上传
                list.add(filePath+ "/" + fileName);
            } 
        }
        //上传到远程服务器
        String imgpath ="";
        if(!StringUtils.isEmpty(upPath) && list.size() >0 ){
            String str = HttpConnectionUtil.uploadFile(upPath,list);
            JSONObject jsonObject = JSONObject.fromObject(str);
            JSONArray jArray= jsonObject.optJSONArray("filePaths");
            for(int i=0;i<jArray.size();i++){
                  String imgPath = basePath+"/"+(String)jArray.get(i);
                  imgUrlList.add(imgPath);
                  //路径
            }
        }
        return imgUrlList;
	}
}
