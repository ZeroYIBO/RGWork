package fileWork;

import java.io.*;
import java.text.Collator;
import java.util.Scanner;

public class TestFour {
public static void main(String[] args){
		
		Scanner sc = new Scanner(System.in);
		int sum=0,n=0;
		int datasum[] = new int[20];
		int num[] = new int[20];
		String pro[] = new String[200];
		String provance[] = new String[200];
		String town[] = new String[200];
		int data[] = new int[600];
		System.out.println("�����������ļ���������ļ�����ָ�����ʡ�ݣ��ɲ�д����");
		System.out.println("(�㽭ʡ������ʡ���㶫ʡ������ʡ������ʡ������ʡ������ʡ������ʡ������ʡ)");
		String s = sc.nextLine();
		String split[] = s.split("\\s");
		int len = split.length;
		File file = new File(split[0]);
		FileWriter fw = null;
		BufferedReader br = null;
		BufferedWriter bw = null;
		String ary[] = {"�㽭ʡ","����ʡ","�㶫ʡ","����ʡ","����ʡ","����ʡ","����ʡ","����ʡ","����ʡ"};	

		try {
			FileReader fr = new FileReader(file);
			fw = new FileWriter(split[1]);
			br = new BufferedReader(fr);
			bw = new BufferedWriter(fw);
			String str = null;
			int i=0,j=0,k=0,index=0;
			Collator col = Collator.getInstance(java.util.Locale.CHINA);
			
			while((str=br.readLine())!=null) {
				String result[] = str.split("	");	
				pro[i] = result[0];
				town[i] = result[1];
				data[i] = Integer.parseInt(result[2]);
				i++;
				n=i;
			}			//���ж��룬�ָ������Ӧʡ���С��������鲢��¼����			
		
			for(j=0;j<9;j++) {			
				for(i=0;i<n;i++) {
					if(pro[i].equals(ary[j])&&data[i]>0) {
						if(j==k) {
							provance[j] = pro[i];		//ȥ���ظ���ʡ��
							k++;
						}
						sum = sum+data[i];
						index = i;
					}				//sum = sum+data[i]���ڼ���ÿ��ʡ������
				}					//index = i���ڼ�¼ÿ��ʡ���е��±�
				num[j] = index;			//��ÿ��ʡ���±��������
				datasum[j] = sum;		//��ÿ��ʡ��������������
				sum=0;
				index=0;
			}			
			k=0;
			for(j=0;j<9;j++) {
				for(int m=j+1;m<10;m++) {
					if(datasum[m]>datasum[j]) {
						int temp = datasum[m];
/*����ʡ����*/				datasum[m] = datasum[j];
						datasum[j] = temp;
/*������Ӧʡ��*/			String swap = provance[m];
						provance[m] = provance[j];
						provance[j] = swap;
/*������Ӧʡ�±�*/			int tp = num[m];
						num[m] = num[j];
						num[j] = tp;
					}
				}				//��ʡ��������������ͬʱ������Ӧ���±�
			}
			
			if(len<3) {			//if������������ʡ����Ϣ
				for(j=0;j<9;j++) {
					for(i=0;i<n;i++) {
						if(pro[i].equals(provance[j])&&data[i]>0) {			
							if(j==k) {
/*���ÿ��ʡ�����ֺͶ�Ӧ����*/			if(k>0) {
								bw.newLine();
								System.out.println();
								}
								bw.write(pro[i]+"\t������"+datasum[j]);
								bw.newLine();
								System.out.println(pro[i]+"\t������"+datasum[j]);
								k++;
							}
/*forѭ�����н�������*/			for(int m=i+1;m<num[j]+1;m++) {
								if(data[m]>data[i]) {
/*��������*/							int temp = data[m];
									data[m] = data[i];
									data[i] = temp;
/*������*/							String swap = town[m];
									town[m] = town[i];
									town[i] = swap;
								}
								else if(data[m]==data[i]) {
									if(col.getCollationKey(town[m]).compareTo(col.getCollationKey(town[i]))<0) {
										int temp = data[m];
										data[m] = data[i];
										data[i] = temp;
										String swap = town[m];
										town[m] = town[i];
										town[i] = swap;
									}
								}
							}
							bw.write(town[i]+"\t"+data[i]);
							bw.newLine();
							System.out.println(town[i]+"\t"+data[i]);							
					}	
				}
			}
		}
		else {					//else������ָ��ʡ����Ϣ
			int temp=0,number=0;
			String t = null;
			for(i=0;i<n;i++) {
				if(pro[i].equals(split[2])&&data[i]>0) {
					number=i;
				}				//number��¼ָ��ʡ���±꣬����ѭ������
			}
			for(i=0;i<n;i++) {
				if(pro[i].equals(split[2])&&data[i]>0) {
					for(j=i+1;j<number+1;j++) {
						if(data[j]>data[i]) {
/*�ڲ�ѭ���Ƕ��е�����*/			temp = data[j];
							data[j] = data[i];
							data[i] = temp;
							t = town[j];
							town[j] = town[i];
							town[i] = t;
						}
						else if(data[j]==data[i]) {
							if(col.getCollationKey(town[j]).compareTo(col.getCollationKey(town[i]))<0) {
/*��������*/						temp = data[j];
								data[j] = data[i];
								data[i] = temp;
/*������Ӧ��*/						t = town[j];
								town[j] = town[i];
								town[i] = t;
							}	
						}
					}
					if(k==0) {			//��ѭ�����ָ��ʡ�����ֺ�����
						for(int dex=0;dex<9;dex++) {
							if(split[2].equals(provance[dex])) {
								bw.write(pro[i]+"\t������"+datasum[dex]);
								bw.newLine();
								System.out.println(pro[i]+"\t������"+datasum[dex]);
							}
						}							
					}
					k++;
					bw.write(town[i]+"\t"+data[i]);
					bw.newLine();
					System.out.println(town[i]+"\t"+data[i]);
				}
			}	
		}
			bw.close();
		}catch (IOException e) {
			e.getMessage();
		}finally {
			if(br!=null) {
				try {
					br.close();
				} catch (Exception e2) {
			}
		}	
	}
		sc.close();
	}
}