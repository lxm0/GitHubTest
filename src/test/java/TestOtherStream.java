import org.junit.Test;

import java.io.*;

public class
TestOtherStream {
	/*
	 * ��׼�������������
	 * ��׼���������System.out
	 * ��׼����������System.in
	 * 
	 * ��Ŀ��
	 * �Ӽ��������ַ�����Ҫ�󽫶�ȡ���������ַ���ת�ɴ�д�����Ȼ������������������
	 * ֱ�������롰e�����ߡ�exit��ʱ���˳�����

	 */
	@Test
	public void test2(){
		BufferedReader br = null;
		try {
			InputStream is = System.in;
			InputStreamReader isr = new InputStreamReader(is);
			br = new BufferedReader(isr);
			String str;
			while(true){
				System.out.println("�������ַ�����");
				str = br.readLine();
				if(str.equalsIgnoreCase("e") || str.equalsIgnoreCase("exit")){
					break;
				}
				String str1 = str.toUpperCase();
				System.out.println(str1);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
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
	
	/*
	 * ���ʵ���ֽ������ַ���֮���ת����
	 * ת������InputStreamReader  OutputStreamWriter
	 * ���룺�ַ���  --->�ֽ�����
	 * ���룺�ֽ�����--->�ַ���
	 */
	@Test
	public void test1(){
		BufferedReader br = null;
		BufferedWriter bw = null;
		try {
			//����
			File file = new File("dbcp.txt");
			FileInputStream fis = new FileInputStream(file);
			InputStreamReader isr = new InputStreamReader(fis, "GBK");
			br = new BufferedReader(isr);
			//����
			File file1 = new File("dbcp4.txt");
			FileOutputStream fos = new FileOutputStream(file1);
			OutputStreamWriter osw = new OutputStreamWriter(fos, "GBK");
			bw = new BufferedWriter(osw);
			String str;
			while((str = br.readLine()) != null){
				bw.write(str);
				bw.newLine();
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
}
