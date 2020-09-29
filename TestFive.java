package fileWork;

import java.io.*;
import java.text.Collator;
import java.util.Scanner;

public class TestFive {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String s = sc.nextLine();
		Operate op = new Operate();
		op.operate(s);
		sc.close();
	}
}
class Operate{
	String s = null;
	int len;
	Read rd = new Read();
	Sort sort = new Sort();
	Function f = new Function();
	Data operate(String s) {
		this.s = s;
		String split[] = s.split("\\s");
		len = split.length;
		if(len==1) {
			rd.read("yq_in.txt");
			rd.wipe();
			rd.show();
		}
		else if(len==2) {
			rd.read(split[0]);			//调用读文件方法
			rd.wipe();					//调用去除重复元素方法
			sort.ProSort();				//调用省排序方法
			sort.TownSort();			//调用市排序方法
			f.two(split[0], split[1]);	//调用两个参数的函数
		}
		else if(len==3) {
			rd.read(split[0]);
			rd.wipe();
			sort.ProSort();
			sort.TownSort();
			f.three(split[0], split[1],split[2]);
		}
		return null;
	}
}
class Data{
	public static String str=null;
	public static String pro[] = new String[200];
	public static String provance[] = new String[200];
	public static String town[] = new String[200];
	public static int sum = 0, n = 0, i = 0, j = 0, k = 0, index = 0, a = 0, b = 0;
	public static int datasum[] = new int[20];
	public static int Index[] = new int[20];
	public static int data[] = new int[600];
	public static Collator col = Collator.getInstance(java.util.Locale.CHINA);
}

class Read extends Data {

	void read(String s) {
		
		File file = new File(s);
		BufferedReader br = null;

		try {
			FileReader fr = new FileReader(file);
			br = new BufferedReader(fr);
			//while循环读入文件
			while ((str = br.readLine()) != null) {
				String ch[] = str.split("	");
				pro[i] = ch[0];
				town[i] = ch[1];
				data[i] = Integer.parseInt(ch[2]);
				i++;
				n = i;
			}
		} catch (IOException e) {
			e.getMessage();
		}
	}
	//wipe方法去掉省中的重复元素，b记录所有不重复元素个数
	void wipe() {
		for (i = 0; i < n;) {
			provance[0] = pro[0];
			for (j = i + 1; j < n; j++) {
				if (!(pro[i].equals(pro[j]))) {
					a++;
					provance[a] = pro[j];
					b = a;
					break;
				}
			}
			i = j;
		}
		b = b + 1;
	}
	void show() {
		FileWriter fw = null;
		BufferedWriter bw = null;
		try {
			fw = new FileWriter("yq_out_06.txt");
			bw = new BufferedWriter(fw);
			for (j = 0; j < b; j++) {
				for (i = 0; i < n; i++) {
					if (pro[i].equals(provance[j]) && data[i] > 0) {
						if (j == k) {
							if (k > 0) {
								bw.newLine();
								System.out.println();
							}
							bw.write(pro[i]);
							bw.newLine();
							System.out.println(pro[i]);
							k++;
						}
						bw.write(town[i] + "\t" + data[i]);
						bw.newLine();
						System.out.println(town[i] + "\t" + data[i]);
					}
				}
			}//输出源文件所有不改动的数据
			k=0;
			bw.close();
		} catch (Exception e) {
			e.getMessage();
		}		
	}
}
class Sort extends Data{

	void ProSort() {
		
		for (j = 0; j < b; j++) {
			for (i = 0; i < n; i++) {
				if (pro[i].equals(provance[j]) && data[i] > 0) {
					sum = sum + data[i];
					index = i;
				} // sum = sum+data[i]用于计算每个省的总数
			} // index = i用于记录每个省内市的下标
			Index[j] = index; // 将每个省的下标存入数组
			datasum[j] = sum; // 将每个省的总数存入数组
			sum = 0;
			index = 0;
		}
		for (j = 0; j < b; j++) {
			for (int m = j + 1; m < 10; m++) {
				if (datasum[m] > datasum[j]) {
					int temp = datasum[m];
					datasum[m] = datasum[j];
					datasum[j] = temp;
					String swap = provance[m];
					provance[m] = provance[j];
					provance[j] = swap;
					int tp = Index[m];
					Index[m] = Index[j];
					Index[j] = tp;
				}
			} // 对省的总数进行排序，同时交换对应的下标
		}
	}//省排序
	void TownSort() {
		for (j = 0; j < b; j++) {
			for (i = 0; i < n; i++) {
				if (pro[i].equals(provance[j]) && data[i] > 0) {
					for (int m = i + 1; m < Index[j] + 1; m++) {
						if (data[m] > data[i]) {
							int temp = data[m];
							data[m] = data[i];
							data[i] = temp;
							String swap = town[m];
							town[m] = town[i];
							town[i] = swap;
						} else if (data[m] == data[i]) {
							if (col.getCollationKey(town[m]).compareTo(col.getCollationKey(town[i])) < 0) {
								int temp = data[m];
								data[m] = data[i];
								data[i] = temp;
								String swap = town[m];
								town[m] = town[i];
								town[i] = swap;
							}
						}
					}
				}
			}
		}
	}//市排序
}
class Function extends Data {
	//传入两个参数的方法，for循环输出结果
	void two(String s1,String s2) {
		
		FileWriter fw = null;
		BufferedWriter bw = null;
		try {
			fw = new FileWriter(s2);
			bw = new BufferedWriter(fw);
			for (j = 0; j < b; j++) {
				for (i = 0; i < n; i++) {
					if (pro[i].equals(provance[j]) && data[i] > 0) {
						if (j == k) {
							if (k > 0) {
								bw.newLine();
								System.out.println();
							}
							bw.write(pro[i] + "\t总数：" + datasum[j]);
							bw.newLine();
							System.out.println(pro[i] + "\t总数：" + datasum[j]);
							k++;
						}
						bw.write(town[i] + "\t" + data[i]);
						bw.newLine();
						System.out.println(town[i] + "\t" + data[i]);
					}
				}
			}
			bw.close();
		} catch (Exception e) {
			e.getMessage();
		}		
	}
	//传入三个参数输出指定省
	void three(String s1,String s2,String s3) {
		FileWriter fw = null;
		BufferedWriter bw = null;
		try {
			fw = new FileWriter(s2);
			bw = new BufferedWriter(fw);
				
				for (i = 0; i < n; i++) {
					if (pro[i].equals(s3) && data[i] > 0) {
						if (k == 0) { // 用循环输出指定省的名字和总数
							for (int dex = 0; dex < b; dex++) {
								if (s3.equals(provance[dex])) {
									bw.write(pro[i] + "\t总数：" + datasum[dex]);
									bw.newLine();
									System.out.println(pro[i] + "\t总数：" + datasum[dex]);
								}
							}
						}
						k++;
						bw.write(town[i] + "\t" + data[i]);
						bw.newLine();
						System.out.println(town[i] + "\t" + data[i]);
				}
			}
			bw.close();
		} catch (Exception e) {
			e.getMessage();
		}	
	}
}
