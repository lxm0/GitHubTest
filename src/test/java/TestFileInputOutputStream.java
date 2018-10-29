import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/*
 * 1.���ķ��ࣺ
 * ������������Ĳ�ͬ��������  �����
 * ���մ������ݵĵ�λ�Ĳ�ͬ���ֽ���  �ַ�����������ı��ļ���
 * ���ս�ɫ�Ĳ�ͬ���ڵ�����ֱ���������ļ��ģ�  ������
 * 
 * 2.IO����ϵ
 * �������			�ڵ������ļ�����                                ����������������һ�֣�
 * InputStream		FileInputStream			BufferedInputStream
 * OutputStream		FileOutputStream		BufferedOutputStream
 * Reader			FileReader				BufferedReader
 * Writer			FileWriter				BufferedWriter
 */
public class TestFileInputOutputStream {
	@Test
	public void testCopyFile(){
		long start = System.currentTimeMillis();
//		String src = "C:\\Users\\shkstart\\Desktop\\1.avi";
//		String dest = "C:\\Users\\shkstart\\Desktop\\2.avi";
		String src = "dbcp.txt";
		String dest = "dbcp2.txt";
		copyFile(src,dest);
		long end = System.currentTimeMillis();
		System.out.println("���ѵ�ʱ��Ϊ��" + (end - start));//3198
	}
	
	// ʵ���ļ����Ƶķ���
	public void copyFile(String src, String dest) {
		// 1.�ṩ���롢д�����ļ�
		File file1 = new File(src);
		File file2 = new File(dest);
		// 2.�ṩ��Ӧ����
		FileInputStream fis = null;
		FileOutputStream fos = null;
		try {
			fis = new FileInputStream(file1);
			fos = new FileOutputStream(file2);
			// 3.ʵ���ļ��ĸ���
			byte[] b = new byte[1024];
			int len;
			while ((len = fis.read(b)) != -1) {
				// fos.write(b);//�����д�����֣� fos.write(b,0,b.length);
				fos.write(b, 0, len);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
	}

	// ��Ӳ�̶�ȡһ���ļ�����д�뵽��һ��λ�á����൱���ļ��ĸ��ƣ�
	@Test
	public void testFileInputOutputStream() {
		// 1.�ṩ���롢д�����ļ�
		File file1 = new File("C:\\Users\\shkstart\\Desktop\\1.jpg");
		File file2 = new File("C:\\Users\\shkstart\\Desktop\\2.jpg");
		// 2.�ṩ��Ӧ����
		FileInputStream fis = null;
		FileOutputStream fos = null;
		try {
			fis = new FileInputStream(file1);
			fos = new FileOutputStream(file2);
			// 3.ʵ���ļ��ĸ���
			byte[] b = new byte[20];
			int len;
			while ((len = fis.read(b)) != -1) {
				// fos.write(b);//�����д�����֣� fos.write(b,0,b.length);
				fos.write(b, 0, len);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
	}

	// FileOutputStream
	@Test
	public void testFileOutputStream() {
		// 1.����һ��File���󣬱���Ҫд����ļ�λ�á�
		// ����������ļ����Բ����ڣ���ִ�й����У��������ڣ����Զ��Ĵ����������ڣ��Ὣԭ�е��ļ�����
		File file = new File("hello2.txt");
		// 2.����һ��FileOutputStream�Ķ��󣬽�file�Ķ�����Ϊ�βδ��ݸ�FileOutputStream�Ĺ�������
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
			// 3.д��Ĳ���
			fos.write(new String("I love China��").getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 4.�ر������
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	@Test
	public void testFileInputStream3() { // abcdefgcde
		FileInputStream fis = null;
		try {
			File file = new File("hello.txt");
			fis = new FileInputStream(file);
			byte[] b = new byte[5];// ��ȡ��������Ҫд������顣
			int len;// ÿ�ζ��뵽byte�е��ֽڵĳ���
			while ((len = fis.read(b)) != -1) {
				// for (int i = 0; i < len; i++) {
				// System.out.print((char) b[i]);
				// }
				String str = new String(b, 0, len);
				System.out.print(str);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		}
	}

	// ʹ��try-catch�ķ�ʽ�������µ��쳣������:��֤���Ĺرղ���һ������ִ��
	@Test
	public void testFileInputStream2() {
		// 2.����һ��FileInputStream��Ķ���
		FileInputStream fis = null;
		try {
			// 1.����һ��File��Ķ���
			File file = new File("hello.txt");
			fis = new FileInputStream(file);
			// 3.����FileInputStream�ķ�����ʵ��file�ļ��Ķ�ȡ��
			int b;
			while ((b = fis.read()) != -1) {
				System.out.print((char) b);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 4.�ر���Ӧ����
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	// ��Ӳ�̴��ڵ�һ���ļ��У���ȡ�����ݵ������С�ʹ��FileInputStream
	// Ҫ��ȡ���ļ�һ��Ҫ���ڡ�������FileNotFoundException
	@Test
	public void testFileInputStream1() throws Exception {
		// 1.����һ��File��Ķ���
		File file = new File("hello.txt");
		// 2.����һ��FileInputStream��Ķ���
		FileInputStream fis = new FileInputStream(file);
		// 3.����FileInputStream�ķ�����ʵ��file�ļ��Ķ�ȡ��
		/*
		 * read():��ȡ�ļ���һ���ֽڡ���ִ�е��ļ���βʱ������-1
		 */
		// int b = fis.read();
		// while(b != -1){
		// System.out.print((char)b);
		// b = fis.read();
		// }
		int b;
		while ((b = fis.read()) != -1) {
			System.out.print((char) b);
		}
		// 4.�ر���Ӧ����
		fis.close();
	}
}
