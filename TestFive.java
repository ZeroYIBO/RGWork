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
			rd.read(split[0]);			//���ö��ļ�����
			rd.wipe();					//����ȥ���ظ�Ԫ�ط���
			sort.ProSort();				//����ʡ���򷽷�
			sort.TownSort();			//���������򷽷�
			f.two(split[0], split[1]);	//�������������ĺ���
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
			//whileѭ�������ļ�
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
	//wipe����ȥ��ʡ�е��ظ�Ԫ�أ�b��¼���в��ظ�Ԫ�ظ���
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
			}//���Դ�ļ����в��Ķ�������
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
				} // sum = sum+data[i]���ڼ���ÿ��ʡ������
			} // index = i���ڼ�¼ÿ��ʡ���е��±�
			Index[j] = index; // ��ÿ��ʡ���±��������
			datasum[j] = sum; // ��ÿ��ʡ��������������
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
			} // ��ʡ��������������ͬʱ������Ӧ���±�
		}
	}//ʡ����
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
	}//������
}
class Function extends Data {
	//�������������ķ�����forѭ��������
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
							bw.write(pro[i] + "\t������" + datasum[j]);
							bw.newLine();
							System.out.println(pro[i] + "\t������" + datasum[j]);
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
	//���������������ָ��ʡ
	void three(String s1,String s2,String s3) {
		FileWriter fw = null;
		BufferedWriter bw = null;
		try {
			fw = new FileWriter(s2);
			bw = new BufferedWriter(fw);
				
				for (i = 0; i < n; i++) {
					if (pro[i].equals(s3) && data[i] > 0) {
						if (k == 0) { // ��ѭ�����ָ��ʡ�����ֺ�����
							for (int dex = 0; dex < b; dex++) {
								if (s3.equals(provance[dex])) {
									bw.write(pro[i] + "\t������" + datasum[dex]);
									bw.newLine();
									System.out.println(pro[i] + "\t������" + datasum[dex]);
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
