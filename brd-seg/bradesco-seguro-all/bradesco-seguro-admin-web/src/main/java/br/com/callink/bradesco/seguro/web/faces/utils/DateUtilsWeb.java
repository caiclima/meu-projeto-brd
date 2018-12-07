package br.com.callink.bradesco.seguro.web.faces.utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DateUtilsWeb {

	public static List<Integer> getListAnos() {
		List<Integer> listAnoMesDiaDTOs = new ArrayList<Integer>();
		
		Integer ano = Calendar.getInstance().get(Calendar.YEAR);
		
		listAnoMesDiaDTOs.add(ano--);
		listAnoMesDiaDTOs.add(ano--);
		listAnoMesDiaDTOs.add(ano--);
		listAnoMesDiaDTOs.add(ano--);
		
		return listAnoMesDiaDTOs;
	}
	
	public static List<Integer> getListMeses() {
		List<Integer> listAnoMesDiaDTOs = new ArrayList<Integer>();
		
		listAnoMesDiaDTOs.add(1);
		listAnoMesDiaDTOs.add(2);
		listAnoMesDiaDTOs.add(3);
		listAnoMesDiaDTOs.add(4);
		listAnoMesDiaDTOs.add(5);
		listAnoMesDiaDTOs.add(6);
		listAnoMesDiaDTOs.add(7);
		listAnoMesDiaDTOs.add(8);
		listAnoMesDiaDTOs.add(9);
		listAnoMesDiaDTOs.add(10);
		listAnoMesDiaDTOs.add(11);
		listAnoMesDiaDTOs.add(12);
		
		return listAnoMesDiaDTOs;
	}

}
