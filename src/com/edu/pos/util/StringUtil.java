package com.edu.pos.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class StringUtil {
	
	//넘겨받은 숫자가 1자리 수이면, 앞에 0 붙이기
	//누군가가 이 메서드를 호출하면, 처리결과를 반환받는다.
	//static
	public static String getNumString(int num) {
		String str=null;
		
		if(num<10) {		//한자리수
			str="0"+num;
		
		}else {		//두자리수
			str=Integer.toString(num);		//Wrapper 클래스
		}
		return str;
	}
	
	
	
	
	//=========================================//
	
	
	
	
	// 확장자 추출하여 반환받기
	public static String getExtend(String filename) {
	//	String filename="a.aa.png";
		int lastIndex=filename.lastIndexOf(".");
		System.out.println(lastIndex);
		return filename.substring(lastIndex+1, filename.length());		//뒤에 인수 --> 전체 문자열의 length
	}
	
	

	
	//=========================================//
	
	

	//파일명 반환받기 (밀리세컨즈로)
	
	public static String createFilename(String url) {
		//파일명 만들기
		long time=System.currentTimeMillis();
		//확장자 구하기
		String ext=StringUtil.getExtend(url);
		
		return time+"."+ext;
	}
	
	
	
	//=========================================//
	
	
	
	
	//비밀번호 암호화하기
	public static String getConvertedPassword(String pass) {
		//자바의 보안과 관련된 기능을 지원하는 api가 모여 있는 패키지 : java.security
		
		StringBuffer hexString=null;
		
		//암호화 객체 이용!
		MessageDigest digest;
		try {
			// 1)
			//MessageDigest는 추상메서드이기 때문에 new하지 않고 객체를 참조해 메서드를 사용한다
			//--> 그 메서드의 반환값을 변수로 받으면 됨
			digest = MessageDigest.getInstance("SHA-256");
			
			// 2) 문자를 바이트로 분해!  digest()메서드 이용
			//--> 한 바이트씩 나누어 배열에 담는다
			byte[] hash=digest.digest(pass.getBytes("UTF-8"));
			
			//String --> 버퍼로 변경
			hexString=new StringBuffer();		//누적 시킬 스트링
			
			//**주의점))String은 불변이다!!
			//--> 따라서 그 값이 변경될 수 없다! 한번 메모리에 올라간 객체는 수정이 안된다
			// 스트링은 반복문 횟수가 클 때는 절대 누적식을 사용해서는 안된다.!!
			// 해결책 --> 변경 가능한 문자열 객체를 지원하는 StringBuffer, StringBuilder 등을 활용하자!
			
			
			for(int i=0; i<hash.length;i++) {
				// 3) 배열에 담긴 바이트들을 문자열로 변환하기
				// 자리수 옯겨서 16진수로 만든다 (0xff&로 바이트를 해독-->16진수로 바뀐 문자열 반환)
				String hex=Integer.toHexString(0xff& hash[i]);

//				System.out.println(hash[i]);
//				System.out.println(hex);
				
				// 5)**한 자리수만 있을 경우 64자리수에서 제외되므로 0추가!
				if(hex.length()==1) {
					hexString.append("0");
//					hex="0"+hex;
//					hexString+="0";
				} 

				// 4) 반환된 문자열들을 StringBuffer 객체에 담는다()  문자열을 담을 수 있는 객체
				//문자들은 변하지 않는 수라는 것을 참고하면(수정 불가) +=로 누적시킬 수가 없다
				//--> 객체에 append() 메서드로 담는다!
				
				//스트링 누적 메서드!! append()메서드
				hexString.append(hex);
				
			}
			
//			System.out.println(hexString.toString());
//			System.out.println(hexString.length());
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return hexString.toString();
	}
	
	
	

	//테스트용 메인!
/*	public static void main(String[] args) {
		String result=getConvertedPassword("minzino");
		System.out.println(result);
		System.out.println(result.length());
	}
*/
	
	
}
