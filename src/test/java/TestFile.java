import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Date;


/*
 * java.io.File��
 * 1.���������롢�����ص��ࡢ�ӿڵȶ�������java.io����
 * 2.File��һ���࣬�����й�������������󡣴˶����Ӧ��һ���ļ���.txt .avi .doc .ppt .mp3 .jpg�����ļ�Ŀ¼
 * 3.File���������ƽ̨�޹صġ�
 * 4.File�еķ��������漰����δ�����ɾ�����������ȵȡ�ֻҪ�漰�ļ����ݵģ�File������Ϊ���ģ�������io������ɡ�
 * 5.File��Ķ�����Ϊio���ľ�����Ĺ��������βΡ�
 */
public class TestFile {
	/*
	 *  createNewFile()
		delete()
		mkDir():����һ���ļ�Ŀ¼��ֻ�����ϲ��ļ�Ŀ¼���ڵ�����£����ܷ���true
		mkDirs():����һ���ļ�Ŀ¼�����ϲ��ļ�Ŀ¼�����ڣ�һ������
		list()
		listFiles()
	 */
	@Test
	public void test3() throws IOException{
		File file1 = new File("d:/io/helloworld.txt");
		System.out.println(file1.delete());
		
		if(!file1.exists()){
			boolean b = file1.createNewFile();
			System.out.println(b);
		}
		
		File file2 = new File("d:\\io1\\io2");
		if(!file2.exists()){
			boolean b = file2.mkdirs();
			System.out.println(b);
		}
		
		File file3 = new File("d:\\teach");
		String[] strs = file3.list();
		for(int i = 0;i < strs.length;i++){
			System.out.println(strs[i]);
		}
		
		System.out.println();
		
		File[] files = file3.listFiles();
		for(int i = 0;i < files.length;i++){
			System.out.println(files[i].getName());
		}
	}
	
	/*
	 *  exists()
		canWrite()
		canRead()
		isFile()
		isDirectory()
		lastModified()
		length()

	 */
	@Test
	public void test2(){
		File file1 = new File("d:/io/helloworld.txt");
		File file2 = new File("d:\\io\\io1");
		
		System.out.println(file1.exists());
		System.out.println(file1.canWrite());
		System.out.println(file1.canRead());
		System.out.println(file1.isFile());
		System.out.println(file1.isDirectory());
		System.out.println(new Date(file1.lastModified()));
		System.out.println(file1.length());
		
		System.out.println();
		
		System.out.println(file2.exists());
		System.out.println(file2.canWrite());
		System.out.println(file2.canRead());
		System.out.println(file2.isFile());
		System.out.println(file2.isDirectory());
		System.out.println(new Date(file2.lastModified()));
		System.out.println(file2.length());
	
	}
	
	/*
	 * ·����
	 * ����·���������̷����ڵ��������ļ�·��
	 * ���·�����ڵ�ǰ�ļ�Ŀ¼�µ��ļ���·��
	 * 
	 *  getName()
		getPath()
		getAbsoluteFile()
		getAbsolutePath()
		getParent()
		renameTo(File newName)

	 */
	@Test
	public void test1(){
		File file1 = new File("d:/io/helloworld.txt");
		File file2 = new File("hello1.txt");
		
		File file3 = new File("d:\\io\\io1");
		File file4 = new File("d:\\io2");
		
		System.out.println(file1.getName());
		System.out.println(file1.getPath());
		System.out.println(file1.getAbsoluteFile());
		System.out.println(file1.getParent());
		System.out.println(file1.getAbsolutePath());
		
		System.out.println();
		
		System.out.println(file3.getName());
		System.out.println(file3.getPath());
		System.out.println(file3.getAbsoluteFile());
		System.out.println(file3.getParent());
		System.out.println(file3.getAbsolutePath());
		
		//renameTo(File newName):������
		//file1.renameTo(file2):file1������Ϊfile2.Ҫ��file1�ļ�һ�����ڣ�file2һ��������
		boolean b = file1.renameTo(file2);
		System.out.println(b);
		
		boolean b1 = file4.renameTo(file3);
		System.out.println(b1);
	}
}
