package com.mm.imgmgt.web;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mm.core.weave.web.BaseController;
import com.mm.imgmgt.model.Image;
import com.mm.imgmgt.service.ImgService;

/**
 * 파일 업로드 모듈
 * @author chambab
 *
 */
@Controller
public class ImgController extends BaseController {

	@Inject
	private ImgService imgService;
	@Value("#{contextProperties['file_upload_dir']}")
	private String fileUploadDir;
	
	
	@RequestMapping("/1/img/upload.json")	
	public @ResponseBody Image uploadImages(HttpServletRequest request) {			
		
		
		Calendar cal = Calendar.getInstance();
		String ymd = String.format("%04d%02d%02d", cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1,cal.get(Calendar.DAY_OF_MONTH));
		
		
		Image image = new Image();
		String imgId = null;
		
		String fileName = request.getHeader("X-File-Name");
		String ext = fileName.substring(fileName.lastIndexOf("."));
		String uploadFileName = fileName.substring(0, fileName.lastIndexOf(".")) + "_" + System.currentTimeMillis() + ext;
	
		
		//String fileUrl = request.getContextPath() + "/data/" + uploadFileName;
		//File uploadFile = new File(fileUploadDir + "/" +  uploadFileName);
		String fileUrl = request.getContextPath() + "/data/" + ymd + "/" + uploadFileName;
		
		File tmpFile = new File(fileUploadDir + "/" +  ymd + "/");
		if(tmpFile.isDirectory() == false) {
			tmpFile.mkdirs();
		}		
		
		File uploadFile = new File(fileUploadDir + "/" +  ymd + "/" + uploadFileName);
		
		
		InputStream in;
		try {
			in = request.getInputStream();
			OutputStream outFile = new FileOutputStream(uploadFile);
			byte[] buf = new byte[1024*2];
			int size = 0;
			while((size=in.read(buf)) != -1){
				outFile.write(buf, 0, size);
			}
			outFile.close();
			in.close();
			
			imgId = imgService.selectImgId();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			getLogger().debug(e.toString());
		}

		//String  rRul = request.getServerName();
		String url = request.getServerName() + fileUrl;		
		image.setImgUrl(url);
		image.setImgId(imgId);
		image.setImgNm(fileName);
		
		getLogger().info("imgUrl : " + url);
		return image;
		//return fileUrl;
		//return "<a href='"+fileUrl+"'><img src='" + fileUrl + "' width='300'></a>";
	}	
	
	/**
	 * 프로파일의 이미지 등록후 URL Return
	 * @param userId
	 * @param request
	 * @return
	 */	
	@RequestMapping(value="/1/img/{userId}/uploadprofile.json", method=RequestMethod.POST)
	public @ResponseBody Image uploadProfileImages(@PathVariable String userId, HttpServletRequest request) {			
		
		Image image = this.uploadImage(request);
		
		HashMap<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("userId", userId);
		paramMap.put("userImg", image.getImgUrl());
		try {
			imgService.insertProfileImg(paramMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			getLogger().info("Error : " + e.toString());
		}
		return image;
	}
	/** 
	 * 이미지 등록 후 정보 Return
	 * @param request
	 * @return
	 */
	private Image uploadImage(HttpServletRequest request) {
		
		Image image = new Image();
		String imgId = null;
		
		String fileName = request.getHeader("X-File-Name");
		String ext = fileName.substring(fileName.lastIndexOf("."));
		String uploadFileName = fileName.substring(0, fileName.lastIndexOf(".")) + "_" + System.currentTimeMillis() + ext;
		String fileUrl = request.getContextPath() + "/data/" + uploadFileName;
		File uploadFile = new File(fileUploadDir + "/" +  uploadFileName);
		
		InputStream in;
		try {
			in = request.getInputStream();
			OutputStream outFile = new FileOutputStream(uploadFile);
			byte[] buf = new byte[1024*2];
			int size = 0;
			while((size=in.read(buf)) != -1){
				outFile.write(buf, 0, size);
			}
			outFile.close();
			in.close();
			
			//imgId = imgService.selectImgId();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			getLogger().debug(e.toString());
		}

		//String  rRul = request.getServerName();
		String url = request.getServerName() + fileUrl;		
		image.setImgUrl(url);
		//image.setImgId(imgId);
		image.setImgNm(fileName);
		
		getLogger().info("imgUrl : " + url);
		return image;		
	}
}
