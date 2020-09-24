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
		System.out.println("请输入输入文件名和输出文件名和指定输出省份（可不写）：");
		System.out.println("(浙江省、江西省、广东省、江苏省、湖南省、安徽省、陕西省、河南省、贵州省)");
		String s = sc.nextLine();
		String split[] = s.split("\\s");
		int len = split.length;
		File file = new File(split[0]);
		FileWriter fw = null;
		BufferedReader br = null;
		BufferedWriter bw = null;
		String ary[] = {"浙江省","江西省","广东省","江苏省","湖南省","安徽省","陕西省","河南省","贵州省"};	

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
			}			//按行读入，分割后存入对应省、市、数据数组并记录长度			
		
			for(j=0;j<9;j++) {			
				for(i=0;i<n;i++) {
					if(pro[i].equals(ary[j])&&data[i]>0) {
						if(j==k) {
							provance[j] = pro[i];		//去掉重复的省份
							k++;
						}
						sum = sum+data[i];
						index = i;
					}				//sum = sum+data[i]用于计算每个省的总数
				}					//index = i用于记录每个省内市的下标
				num[j] = index;			//将每个省的下标存入数组
				datasum[j] = sum;		//将每个省的总数存入数组
				sum=0;
				index=0;
			}			
			k=0;
			for(j=0;j<9;j++) {
				for(int m=j+1;m<10;m++) {
					if(datasum[m]>datasum[j]) {
						int temp = datasum[m];
/*交换省总数*/				datasum[m] = datasum[j];
						datasum[j] = temp;
/*交换对应省份*/			String swap = provance[m];
						provance[m] = provance[j];
						provance[j] = swap;
/*交换对应省下标*/			int tp = num[m];
						num[m] = num[j];
						num[j] = tp;
					}
				}				//对省的总数进行排序，同时交换对应的下标
			}
			
			if(len<3) {			//if语句是输出所有省的信息
				for(j=0;j<9;j++) {
					for(i=0;i<n;i++) {
						if(pro[i].equals(provance[j])&&data[i]>0) {			
							if(j==k) {
/*输出每个省的名字和对应总数*/			if(k>0) {
								bw.newLine();
								System.out.println();
								}
								bw.write(pro[i]+"\t总数："+datasum[j]);
								bw.newLine();
								System.out.println(pro[i]+"\t总数："+datasum[j]);
								k++;
							}
/*for循环对市进行排序*/			for(int m=i+1;m<num[j]+1;m++) {
								if(data[m]>data[i]) {
/*交换数字*/							int temp = data[m];
									data[m] = data[i];
									data[i] = temp;
/*交换市*/							String swap = town[m];
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
		else {					//else语句输出指定省的信息
			int temp=0,number=0;
			String t = null;
			for(i=0;i<n;i++) {
				if(pro[i].equals(split[2])&&data[i]>0) {
					number=i;
				}				//number记录指定省的下标，用于循环排序
			}
			for(i=0;i<n;i++) {
				if(pro[i].equals(split[2])&&data[i]>0) {
					for(j=i+1;j<number+1;j++) {
						if(data[j]>data[i]) {
/*内层循环是对市的排序*/			temp = data[j];
							data[j] = data[i];
							data[i] = temp;
							t = town[j];
							town[j] = town[i];
							town[i] = t;
						}
						else if(data[j]==data[i]) {
							if(col.getCollationKey(town[j]).compareTo(col.getCollationKey(town[i]))<0) {
/*交换数字*/						temp = data[j];
								data[j] = data[i];
								data[i] = temp;
/*交换对应市*/						t = town[j];
								town[j] = town[i];
								town[i] = t;
							}	
						}
					}
					if(k==0) {			//用循环输出指定省的名字和总数
						for(int dex=0;dex<9;dex++) {
							if(split[2].equals(provance[dex])) {
								bw.write(pro[i]+"\t总数："+datasum[dex]);
								bw.newLine();
								System.out.println(pro[i]+"\t总数："+datasum[dex]);
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