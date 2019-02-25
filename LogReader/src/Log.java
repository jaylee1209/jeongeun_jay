import java.awt.image.BufferedImageFilter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Externalizable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.LongToDoubleFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class Log {

	public static void main(String[] args) throws FileNotFoundException, ParseException {
		long startMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory(), endMemory = 0;
		long start = System.currentTimeMillis();
		System.gc();
		
		Log lr = new Log();
		String[] splitstring = lr.read();
		
		StringBuffer sb = lr.process(splitstring);
		
		lr.write(sb);
		
		String[] split2string = lr.read2();

		Map<String, List<LogDto2>> map = lr.process2(split2string);
		
		lr.calculate(map);
		
		endMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		long end = System.currentTimeMillis();
		System.out.print("끝난시간!:");
		System.out.println((end - start)/1000.0 + "초");
		System.out.print(", 메모리 사용량 : " + String.format("%,d", (endMemory - startMemory) / 1000) + " kbyte");		
	}
	
	/**
	 * 시간과 Content-Size를 계산하여 값을 넣어준다.
	 * @param map
	 */
	private void calculate(Map<String, List<LogDto2>> map) {
		String trimkeyset = map.keySet().toString().substring(1, map.keySet().toString().indexOf("]"));
		String[] keyset = trimkeyset.split(", ");
		String result = "";
		
		List<String> resultlist = new ArrayList<>();
		
		for (int i = 0; i < keyset.length; i++) {

			int num = map.get(keyset[i]).size();
			
			Map<String, Long> timeresult = calculateTime(map.get(keyset[i]), num);
			Map<String, Integer> sizeresult = calculateSize(map.get(keyset[i]), num);
			
			
			result = map.get(keyset[i]).get(0).getStart() + ", " + num + ", " + 
							timeresult.get("average") + ", " + timeresult.get("min") + ", " + timeresult.get("max") + ", " +
							sizeresult.get("average") + ", " + sizeresult.get("min") + ", " + sizeresult.get("max");
			
			resultlist.add(result);
		}
		write2(resultlist);
	}
	
	
	/**
	 * 과제 2에서 데이터를 정제하기 위한 메소드
	 * 
	 * 정규식을 포함하고 있는 줄을 찾고 map 의 key값이 존재하지 않는다면은 
	 * 시작시간, 끝나는 시간, contentlength 를 가져와서 DTO 에 담아준다.
	 * 
	 * 그렇지 않으면은 DTO에 값을 넣어준다.
	 * 
	 * @param split2string
	 * @return
	 */
	private Map<String, List<LogDto2>> process2(String[] split2string) {
		
		String extstarttime = "([1-9][0-9].[0-9][0-9].[0-9][0-9] [0-2][0-3]:[0-5][0-9]):[0-5][0-9], ([1-9][0-9].[0-9][0-9].[0-9][0-9] [0-2][0-3]:[0-5][0-9]):[0-5][0-9], IF_[0-9a-z_-]{40,44}, ([0-9]{1,6})";
		Pattern timepattern = Pattern.compile(extstarttime);
		
		SimpleDateFormat sdf = new SimpleDateFormat("YY.MM.DD HH:mm", Locale.KOREA);
		Date startdate;
		Date enddate;
		long diff = 0;
		long sec = 0;
		
		Map<String, List<LogDto2>> map = new HashMap<String, List<LogDto2>>();
		
		for(String rec2string : split2string) {
			Matcher timematcher = timepattern.matcher(rec2string);

			LogDto2 alogdto2 = new LogDto2();
			
			if(timematcher.find() && !map.containsKey(timematcher.group(1))) {
				List<LogDto2> listlogdto = new ArrayList<LogDto2>();
				
				listlogdto.add(alogdto2);
				
				alogdto2.setStart(timematcher.group(1));
				alogdto2.setEnd(timematcher.group(2));
				alogdto2.setContent(timematcher.group(3));
				
				map.put(timematcher.group(1), listlogdto);
				
			} else {
				
				alogdto2.setStart(timematcher.group(1));
				alogdto2.setEnd(timematcher.group(2));
				alogdto2.setContent(timematcher.group(3));
				
				map.get(timematcher.group(1)).add(alogdto2);
			}
		}
		return map;
	}
	
	/**
	 * 과제 2번을 출력하는 메소드
	 * File에대한 경로에다가 txt 파일의 형식으로 파일을 저장한다.
	 * 해당 형태의 파일이 존재하고 쓰기가 가능할 때 bufferedwriter를 사용하여 쓴다.
	 * 
	 * @param result List형태의 결과물을 받는다. 
	 */
	private void write2(List<String> result) {
		try {
		File file = new File("C:\\Users\\meta\\Desktop\\log2.txt");
		BufferedWriter bufferedwriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "utf-8"));
		
			if(file.isFile() && file.canWrite()) {
				
				for (int i = 0; i < result.size(); i++) {
					bufferedwriter.write(result.get(i) + "\r\n");
					
				}
				bufferedwriter.close();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * ContentSize 를 계산하는 메소드
	 * 
	 * @param map		Content-Size를 계산할 요소들이 담겨있는 map
	 * @param number	Content-Size를 가지고 있는 숫자
	 * @return
	 */
	private Map<String, Integer> calculateSize(List<LogDto2> map, int number) {
		int min = 0;
		int max = 0;
		int sum =0;
		
		Map<String, Integer> result = new HashMap<String, Integer>();
		
		for (int i = 0; i < map.size(); i++) {
			
			if(i == 0) {
				min = Integer.parseInt(map.get(i).getContent());
			}
			
			sum += Integer.parseInt(map.get(i).getContent());
			
			if( Integer.parseInt(map.get(i).getContent()) < min ) {
				min = Integer.parseInt(map.get(i).getContent());
			} else if( Integer.parseInt(map.get(i).getContent()) > max ) {
				max = Integer.parseInt(map.get(i).getContent());
			}
		}
		
		int average = sum / number;
		
		result.put("average", average);
		result.put("min", min);
		result.put("max", max);
		
		
		return result;
	}
	
	/**
	 * Time을 계산하는 메소드
	 * 
	 * @param map		시간을 계산할 요소들이 담겨있는 map
	 * @param number	시간의 총 갯수를 가지고 있는 숫자
	 * @return
	 */
	private Map<String, Long> calculateTime(List<LogDto2> map, int number) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("YY.MM.DD HH:mm", Locale.KOREA);
		
		long min = 0;
		long max = 0;
		long sum = 0;
		
		Map<String, Long> result = new HashMap<String, Long>();
		
		for (int i = 0; i < map.size(); i++) {
			
				try {
					long difference = sdf.parse(map.get(i).getEnd()).getTime() - sdf.parse(map.get(i).getStart()).getTime();
					
					sum += difference;
					
					if(i==0) {
						min = difference;
					}
					
					if(difference < min) {
						min = difference;
					} else if (difference > max) {
						max = difference;
					}
					
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			}
		long average = sum / number;
		
		result.put("min", min);
		result.put("max", max);
		result.put("average", average);

		return result;
	}	
	
	
	/**
	 * 과제 1번을 완료 하고 만들어진 txt 파일을 불러온다.
	 * 
	 * 정규식 표현을 사용하여 엔터의 값이 들어오면 다음 배열로 넘겨준다.
	 * @return
	 * @throws FileNotFoundException
	 */
	private String[] read2() throws FileNotFoundException {
		StringBuffer sb = new StringBuffer();
		String line = ""; 
		Pattern timepattern = null;
		File file = null;
		FileReader filereader =null;
		BufferedReader bf = null;
		
		try {
			file = new File("C:\\Users\\meta\\Desktop\\log.txt");
			filereader = new FileReader(file);
			bf = new BufferedReader(filereader);

			while ((line = bf.readLine()) != null) {
				sb.append(line + "\r\n");
			}
			
			timepattern = Pattern.compile("\r\n");
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				bf.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}
		return timepattern.split(sb);
	}

	
	//==============================================================
	//과제 1
	//==============================================================
	
	/**
	 * 결과를 읽어온 파일을 txt파일로 만드는 작업
	 * 
	 * @param sb
	 */
	private void write(StringBuffer sb) {
		try {
		File file = new File("C:\\Users\\meta\\Desktop\\log.txt");
		BufferedWriter bufferedwriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "utf-8"));
		
			if(file.isFile() && file.canWrite()) {
				
				bufferedwriter.write(sb.toString() + "\n");
				
				bufferedwriter.close();
				
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	/**
	 * 정규식 표현을 사용한 데이터 정제 메소드
	 * 
	 * 줄을 읽으며 bean start라는 단어가 존재하는 단어를 찾으면  map을 생성해준다.
	 * 그렇지 않으면 찾으려하는 키워드 값을 찾을 때 존재하는 key값에 value로 데이터를 넣어준다.
	 * 
	 * @param splitstring
	 * @return
	 */
	private StringBuffer process(String[] splitstring) {
		StringBuffer sb = new StringBuffer();
		
		String start = "##galileo_bean start";
        Pattern beanstartpattern = Pattern.compile(start);	
		
        String starttime = "([1-9]{1}[0-9]{1}.[0-9]{1}[0-9]{1}.[0-9]{1}[0-9]{1} [0-2]{1}[0-3]{1}:[0-5]{1}[0-9]{1}:[0-5]{1}[0-9]{1})";
        Pattern starttimepattern = Pattern.compile(starttime);
		
        String threadid = "eclipse.galileo-bean-thread-([0-9]{8})";	//logid 뽑는 regex
        Pattern threadpattern = Pattern.compile(threadid);
        
        String esbid= "ESB_TRAN_ID : (IF_[0-9]{4}_[0-9]{2}_[0-9a-z]{8}-[0-9a-z]{4}-[0-9a-z]{4}-[0-9a-z]{4}-[0-9a-z]{12})";
        Pattern esbpattern = Pattern.compile(esbid);
        
        String contentlength = "Content-Length:([0-9]{1,8})";
        Pattern contentpattern = Pattern.compile(contentlength);
        
        String calltime = "#galileo call time:([0-9]{3,5})";
        Pattern calltimepattern = Pattern.compile(calltime);
        
        String task1 = "1. Before Marshalling";
        Pattern task1pattern = Pattern.compile(task1);
        
        String task2 = "2. Marshalling";
        Pattern task2pattern = Pattern.compile(task2);
        
        String task3 = "3. Invoking galileo";
        Pattern task3pattern = Pattern.compile(task3);
        
        String task4 = "4. Unmarshalling and Send to CmmMod Server";
        Pattern task4pattern = Pattern.compile(task4);
        
        String end = "##galileo_bean end";
        Pattern beanendpattern = Pattern.compile(end);
        
        Map<String, LogDto> map = new HashMap<>();
        
		for (String recstring : splitstring) {
			Matcher beanstartmatcher = beanstartpattern.matcher(recstring);
			Matcher timematcher = starttimepattern.matcher(recstring);
			Matcher threadmatcher = threadpattern.matcher(recstring);
			Matcher esbmatcher = esbpattern.matcher(recstring);
			Matcher contentmatcher = contentpattern.matcher(recstring);
			Matcher calltimematcher = calltimepattern.matcher(recstring);
	        Matcher task1matcher = task1pattern.matcher(recstring);
	        Matcher task2matcher = task2pattern.matcher(recstring);
	        Matcher task3matcher = task3pattern.matcher(recstring);
	        Matcher task4matcher = task4pattern.matcher(recstring);
	        Matcher endmatcher = beanendpattern.matcher(recstring);
	        
			LogDto logdto = new LogDto();
			
			if(threadmatcher.find() && !map.containsKey(threadmatcher.group(1))) {
					map.put(threadmatcher.group(1), logdto);
			}
			
			/*
			 * bean start 일때와 아닐때를 판단해주는 if문
			 */
			if(beanstartmatcher.find()) {
				timematcher.find();
				map.get(threadmatcher.group(1)).setStart(timematcher.group(0));
			} else { 
				
				//thread와 같은 키를 존재 && esb_tran_id 찾고 && start가 null이 아닌것
				if(map.containsKey(threadmatcher.group(1)) && esbmatcher.find() && map.get(threadmatcher.group(1)).getStart() != null) {
		            map.get(threadmatcher.group(1)).setEsb_tran_id(esbmatcher.group(1));
		            
		            //thread와 같은 키존재 && content라는 키워드를 찾으면 실행되는 else if
				} else if(map.containsKey(threadmatcher.group(1)) && contentmatcher.find()) {
						map.get(threadmatcher.group(1)).setContent_length(contentmatcher.group(1));
				
					//thread를 찾거나 task 1,2,3,4 (Marshalling 관련 키워드) 를 찾으면 실행되는 else if
				} else if(map.containsKey(threadmatcher.group(1)) && task1matcher.find() && task2matcher.find() && task3matcher.find() && task4matcher.find()) {
					String task1string = recstring.substring(recstring.indexOf("1. Before Marshalling")-13, recstring.indexOf("1. Before Marshalling")-8);
					String task2string = recstring.substring(recstring.indexOf("2. Marshalling")-13, recstring.indexOf("2. Marshalling")-8);
					String task3string = recstring.substring(recstring.indexOf("3. Invoking galileo")-13, recstring.indexOf("3. Invoking galileo")-8);
					String task4string = recstring.substring(recstring.indexOf("4. Unmarshalling and Send to CmmMod Server")-13, recstring.indexOf("4. Unmarshalling and Send to CmmMod Server")-8);
			
				    map.get(threadmatcher.group(1)).setBmarshalling(task1string);
			        map.get(threadmatcher.group(1)).setMarshalling(task2string);
			        map.get(threadmatcher.group(1)).setInvoke_galileo(task3string);
		            map.get(threadmatcher.group(1)).setUnmarshalling(task4string);
		            
		            //map에 키가 존재하거나 calltime 이라는 키워드가 존재할 때 실행되는 else if
				} else if(map.containsKey(threadmatcher.group(1)) && calltimematcher.find()) {
					map.get(threadmatcher.group(1)).setCall_time(calltimematcher.group(1));
					
					//key가 존재하는 
				} else if(map.keySet().contains(threadmatcher.group(1)) && endmatcher.find()) {
					timematcher.find();
					map.get(threadmatcher.group(1)).setEnd(timematcher.group(1));
					
					if(map.get(threadmatcher.group(1)).getStart() != null && (map.get(threadmatcher.group(1)).getBmarshalling() != null || 
							map.get(threadmatcher.group(1)).getMarshalling() != null || map.get(threadmatcher.group(1)).getInvoke_galileo() != null || 
							map.get(threadmatcher.group(1)).getUnmarshalling() != null)) {
//						sb.append(threadmatcher.group(1) + " : " + map.get(threadmatcher.group(1)) + "\r\n");
						sb.append(map.get(threadmatcher.group(1)).getStart() + ", " + map.get(threadmatcher.group(1)).getEnd() + ", " +
								map.get(threadmatcher.group(1)).getEsb_tran_id() + ", " + map.get(threadmatcher.group(1)).getContent_length() + ", " +
								map.get(threadmatcher.group(1)).getCall_time() + ", " + map.get(threadmatcher.group(1)).getBmarshalling() + ", " +
								map.get(threadmatcher.group(1)).getMarshalling() + ", " + map.get(threadmatcher.group(1)).getInvoke_galileo() + ", " +
								map.get(threadmatcher.group(1)).getUnmarshalling() + "\r\n");
					}
//					System.out.println(map);
					map.remove(threadmatcher.group(1));
				}
			
			}
			
		}
//		System.out.println(map.size());
		
		return sb;
	}
	
	
	/**
	 * 과제1을 시작하기 위한 log파일 읽어오기
	 * 파일을 읽어오면서 line에 일치하는 String이 있으면은 그 줄을 가져온다.
	 * @return
	 */
	private String[] read() {
		 	String path = "C:\\Users\\meta\\Desktop\\logfile";
	        String fileName = "galileo.log";
	        BufferedReader reader = null;
	        StringBuffer sb = new StringBuffer();
	        String line = "";
	        Pattern timepattern = null;
	        
	        
	        try {
	            reader = new BufferedReader(new FileReader(new File(path, fileName)));
	            
	            while ((line = reader.readLine()) != null){
	            	if(line.contains("##galileo_bean start") || line.contains("ESB_TRAN_ID :") || line.contains("Content-Length") || line.contains("#galileo call time") 
	            			|| line.contains("1. Before Marshalling") || line.contains("2. Marshalling") || line.contains("3. Invoking galileo") 
	            			|| line.contains("4. Unmarshalling and Send to CmmMod Server") || line.contains("##galileo_bean end") || line.contains("StopWatch")) {
	            		sb.append(line);
	            		sb.append(" ");
	            		
	            		
	            	}
	            }

	            timepattern = Pattern.compile("(?=\\[[1-9]{1}[0-9]{1}.[0-9]{1}[0-9]{1}.[0-9]{1}[0-9]{1} [0-2]{1}[0-3]{1}:[0-5]{1}[0-9]{1}:[0-5]{1}[0-9]{1}])");
	            
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }finally {
	            try{ 
	            	if(reader != null) 
	            		reader.close(); 
	            	}
	            catch(IOException e){}
	        }
	        return timepattern.split(sb);
	}
}
