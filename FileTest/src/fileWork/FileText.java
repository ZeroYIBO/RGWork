package fileWork;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileText {
	public static void main(String[] args){
		File file = new File("yq_in.txt");
		FileWriter fw = null;
		BufferedReader br = null;
		BufferedWriter bw = null;
		String ary[] = {"浙江省","江西省","广东省","江苏省","湖南省","安徽省","陕西省","河南省","贵州省"};
		
		try {
			FileReader fr = new FileReader(file);
			fw = new FileWriter("out.txt");
			br = new BufferedReader(fr);
			bw = new BufferedWriter(fw);
			String str = null;
			int j=0;
			while((str=br.readLine())!=null) {
				String result[] = str.split("	");				
				for(int i=0;i<9;i++) {
					if(result[0].equals(ary[i])) {
						if(i==j) {							
							if(j>0) { 
								bw.newLine();
								System.out.println();
								}
							bw.write(result[0]);
							bw.newLine();
							System.out.println(result[0]);	
							j++;					
						}
						int temp[] = new int[2];
						temp[0] = Integer.parseInt(result[2]);
						if(temp[0]>0) {
							bw.write(result[1]+"	"+result[2]);
							bw.newLine();
							System.out.println(result[1]+"	"+result[2]);
							break;
						}
					}					
				}
			}
			br.close();
			bw.close();
		} catch (IOException e) {
			e.getMessage();
		}finally {
			if(br!=null) {
				try {
					br.close();
				} catch (Exception e2) {
				}
			}
		}	
	}
}