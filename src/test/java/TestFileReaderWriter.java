import org.junit.Test;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/*
 * ʹ��FileReader��FileWriter ����ʵ���ı��ļ��ĸ��ơ�
 * ���ڷ��ı��ļ�����Ƶ�ļ�����Ƶ�ļ���ͼƬ����ֻ��ʹ���ֽ�����
 */
public class TestFileReaderWriter {
	@Test
	public void testFileReaderWriter(){
		//1.��������Ӧ���ļ�srcһ��Ҫ���ڣ��������쳣���������Ӧ���ļ�dest���Բ����ڣ�ִ�й����л��Զ�����
		FileReader fr = null;
		FileWriter fw = null;
		try{
			//����ʵ�ַ��ı��ļ��ĸ���
//			File src = new File("C:\\Users\\shkstart\\Desktop\\1.jpg");
//			File dest = new File("C:\\Users\\shkstart\\Desktop\\3.jpg");
			File src = new File("dbcp.txt");
			File dest = new File("dbcp1.txt");
			//2.
			fr = new FileReader(src);
			fw = new FileWriter(dest);
			//3.
			char[] c = new char[24];
			int len;
			while((len = fr.read(c)) != -1){
				fw.write(c, 0, len);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(fw != null){
				try {
					fw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(fr != null){
				try {
					fr.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	@Test
	public void testFileReader(){
		FileReader fr = null;
		try {
			File file = new File("dbcp.txt");
			fr = new FileReader(file);
			char[] c = new char[24];
			int len;
			while((len = fr.read(c)) != -1){
				String str = new String(c, 0, len);
				System.out.print(str);
			}
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(fr != null){
				try {
					fr.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}
}
