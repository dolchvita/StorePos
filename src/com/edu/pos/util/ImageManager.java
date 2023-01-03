package com.edu.pos.util;

import java.awt.Image;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;

public class ImageManager {
	
	
	//이미지에 대한 경로를 전달하면 --> 이미지 객체를 반환하는 메서드 정의
	public Image[] createImages(String[] imgName) {
		
		Class myclass=this.getClass();
		Image[] images= new Image[imgName.length];

		
		for(int i=0; i<imgName.length; i++) {
			
			URL url=myclass.getClassLoader().getResource(imgName[i]);
			
			try {
				images[i]=ImageIO.read(url);		//생성된 이미지를 배열에 담기
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}//for()문
		
		return images;
	}
	

	
	//======================================//
	
	
	
	
	/* path : 클래스 패스 내의 이미지 경로!
	 * width, height : 크기를 조정하고 싶은 값 
	 */

	//버튼 메뉴에 사용할 아이콘 생성하기
	public ImageIcon getIcon(String path, int width, int height) {
		Class myClass=this.getClass();
		URL url=myClass.getClassLoader().getResource(path);
		
		Image scaledImg=null;
		try {
			Image image=ImageIO.read(url);
			scaledImg=image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
			
			}catch(IOException e) {
				e.printStackTrace();
			}
		ImageIcon icon=new ImageIcon(scaledImg);
		
		return icon;		//ImageIcon 반환!
	}


	
	//------------------------------------------------//
	
	
	//이미지 얻어오기!!
	
	public Image getImage(String path,int width, int height) {
		
		URL url=this.getClass().getClassLoader().getResource(path);
		Image image=null;
		
		try {
			image=ImageIO.read(url);
			
			//크기도 유연하게 조정하자!
			image=image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return image;
	}
	
}

