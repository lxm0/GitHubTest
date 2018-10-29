import org.junit.Test;

import java.io.*;

/*
 * �������			�ڵ������ļ�����                                ����������������һ��,���������ļ�������Ч�ʣ�
 * InputStream		FileInputStream			BufferedInputStream
 * OutputStream		FileOutputStream		BufferedOutputStream  (flush())
 * Reader			FileReader				BufferedReader  (readLine())
 * Writer			FileWriter				BufferedWriter  (flush())
 */
public class TestBuffered {
	@Test
	public void testBufferedReader(){
		BufferedReader br = null;
		BufferedWriter bw = null;
		try {
			File file = new File("dbcp.txt");
			File file1 = new File("dbcp3.txt");
			FileReader fr = new FileReader(file);
			
			FileWriter fw = new FileWriter(file1);
			br = new BufferedReader(fr);
			bw = new BufferedWriter(fw);
//			char[] c = new char[1024];
//			int len;
//			while((len = br.read(c))!= -1){
//				String str = new String(c, 0, len);
//				System.out.print(str);
//			}
			
			String str;
			while((str = br.readLine()) != null){
//				System.out.println(str);
				bw.write(str + "\n");
//				bw.newLine();
				bw.flush();
			}
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(bw != null){
				try {
					bw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			if(br != null){
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
		}
	}
	
	@Test
	public void testCopyFile(){
		long start = System.currentTimeMillis();
//		String src = "C:\\Users\\shkstart\\Desktop\\1.avi";
//		String dest = "C:\\Users\\shkstart\\Desktop\\3.avi";
		String src = "C:\\Users\\shkstart\\Desktop\\ʵ��.doc";
		String dest = "C:\\Users\\shkstart\\Desktop\\ʵ��1.doc";
		copyFile(src,dest);
		long end = System.currentTimeMillis();
		System.out.println("���ѵ�ʱ��Ϊ��" + (end - start));//746
	}
	
	//ʹ�û�����ʵ���ļ��ĸ��Ƶķ���
	public void copyFile(String src,String dest){
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			//1.�ṩ���롢д�����ļ�
			File file1 = new File(src);
			File file2 = new File(dest);
			//2.�봴����Ӧ�Ľڵ�����FileInputStream��FileOutputStream
			FileInputStream fis = new FileInputStream(file1);
			FileOutputStream fos = new FileOutputStream(file2);
			//3.�������Ľڵ����Ķ�����Ϊ�βδ��ݸ��������Ĺ�������
			bis = new BufferedInputStream(fis);
			bos = new BufferedOutputStream(fos);
			//4.�����ʵ���ļ����ƵĲ���
			byte[] b = new byte[1024];
			int len;
			while((len = bis.read(b)) != -1){
				bos.write(b, 0, len);
				bos.flush();
			}
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			//5.�ر���Ӧ����
			if(bos != null){
				try {
					bos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			if(bis != null){
				try {
					bis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
		}
	}
	
	
	//ʹ��BufferedInputStream��BufferedOutputStreamʵ�ַ��ı��ļ��ĸ���
	@Test
	public void testBufferedInputOutputStream(){
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			//1.�ṩ���롢д�����ļ�
			File file1 = new File("1.jpg");
			File file2 = new File("2.jpg");
			//2.�봴����Ӧ�Ľڵ�����FileInputStream��FileOutputStream
			FileInputStream fis = new FileInputStream(file1);
			FileOutputStream fos = new FileOutputStream(file2);
			//3.�������Ľڵ����Ķ�����Ϊ�βδ��ݸ��������Ĺ�������
			bis = new BufferedInputStream(fis);
			bos = new BufferedOutputStream(fos);
			//4.�����ʵ���ļ����ƵĲ���
			byte[] b = new byte[1024];
			int len;
			while((len = bis.read(b)) != -1){
				bos.write(b, 0, len);
				bos.flush();
			}
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			//5.�ر���Ӧ����
			if(bos != null){
				try {
					bos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			if(bis != null){
				try {
					bis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
		}
	}
	
}
