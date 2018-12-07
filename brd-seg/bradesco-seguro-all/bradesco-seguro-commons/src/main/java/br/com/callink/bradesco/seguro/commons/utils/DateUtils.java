package br.com.callink.bradesco.seguro.commons.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;


/**
 * Representa a classe de utils de datas
 * 
 * @author neppo.oldamar
 *
 */
public class DateUtils {

	public static String getIdadeByNascimento(Date dataNasc) {
		
		Calendar dateOfBirth = new GregorianCalendar();
		dateOfBirth.setTime(dataNasc);

		// Cria um objeto calendar com a data atual
		Calendar today = Calendar.getInstance(TimeZone.getTimeZone("GMT"));

		// Obtém a idade baseado no ano
		Integer age = today.get(Calendar.YEAR) - dateOfBirth.get(Calendar.YEAR);
		dateOfBirth.add(Calendar.YEAR, age);

		//se a data de hoje é antes da data de Nascimento, então diminui 1(um)
		if (today.before(dateOfBirth)) {
			age--;
		}
		
		return age.toString();
	}
	
	public static Date parse(String pattern, String value) throws ParseException{
		return value == null ? null : new SimpleDateFormat(pattern).parse(value);
	}
	
	public static Date parseENLocale(String value) throws ParseException{
		return value == null ? null : new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(value);
	}
	
	public static String formatDateOnly(Date date){
		return date == null ? null : new SimpleDateFormat("yyyy-MM-dd").format(date);
	}
	
	public static String formatDateandTime(Date date){
		return date == null ? null : new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
	}
	
	public static String format(Date date, String pattern){
		return date == null ? null : new SimpleDateFormat(pattern).format(date);
	}
	
	public static Date getUtimaDataMes(Date dataBase) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(dataBase);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		return cal.getTime();
	}

	public static Date getUtimaDataMes(Integer mes, Integer ano) {
		Calendar calFinal = Calendar.getInstance();
		calFinal.set(Calendar.MONTH, mes-1);
		calFinal.set(Calendar.YEAR, ano);
		calFinal.set(Calendar.DAY_OF_MONTH, calFinal.getActualMaximum(Calendar.DAY_OF_MONTH));
		
		calFinal.set(Calendar.HOUR_OF_DAY, 23);
		calFinal.set(Calendar.MINUTE, 59);
		calFinal.set(Calendar.SECOND, 59);
		calFinal.set(Calendar.MILLISECOND, 9);
		
		return calFinal.getTime();
	}
	
	public static Date adicionarSegundos(Date date, int segundos){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.SECOND, segundos);
		return cal.getTime();
	}
	
	public static List<Integer> getMeses() {
		List<Integer> meses = new ArrayList<>();
		for (int i = 1; i <= 12; i++) {
			meses.add(i);
		}
		return meses;
	}

	/**
	 * Obtém uma lista de anos retroativos com a quantidade limite de anos
	 * informada.
	 * 
	 * @param limite
	 * @return
	 */
	public static List<Integer> getAnosRetroativos(int limite) {
		List<Integer> anos = new ArrayList<>();
		Integer ano = Calendar.getInstance().get(Calendar.YEAR);

		for (int i = 0; i < limite; i++) {
			anos.add(ano--);
		}
		
		return anos;
	}

	
	public static int getPrimeiroDia(Integer mes, Integer ano) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MONTH, mes-1);
		cal.set(Calendar.YEAR, ano);
		return cal.getActualMinimum(Calendar.DAY_OF_MONTH);
	}
	
	public static int getUltimoDia(Integer mes, Integer ano) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MONTH, mes-1);
		cal.set(Calendar.YEAR, ano);
		return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	}
	
	public static Date getPrimeiraDataMes(Integer mes, Integer ano) {
		Calendar calInicio = Calendar.getInstance();
		calInicio.set(Calendar.MONTH, mes-1);
		calInicio.set(Calendar.YEAR, ano);
		calInicio.set(Calendar.DAY_OF_MONTH, calInicio.getActualMinimum(Calendar.DAY_OF_MONTH));
		
		calInicio.set(Calendar.HOUR_OF_DAY, 00);
		calInicio.set(Calendar.MINUTE, 00);
		calInicio.set(Calendar.SECOND, 00);
		calInicio.set(Calendar.MILLISECOND, 0);
		
		return calInicio.getTime();
	}
	
	
	public static int getMesData(Date data) {
		Calendar calMes = Calendar.getInstance();
		calMes.setTime(data);
		int mes = calMes.get(Calendar.MONTH) + 1;
		
		return mes;
	}
	
	public static Date aplicar0H0m0s(Date date){
		return aplicar(date, 0, 0, 0);
	}
	
	public static Date aplicar(Date date, int horas, int minutos, int segundos){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, horas);
		cal.set(Calendar.MINUTE, minutos);
		cal.set(Calendar.SECOND, segundos);
		return cal.getTime();
	}
	
	public static Date aplicar23H59m59s(Date date){
		return aplicar(date, 23, 59, 59);
	}
	
	public static int getAnoData(Date data) {
		Calendar calMes = Calendar.getInstance();
		calMes.setTime(data);
		int ano = calMes.get(Calendar.YEAR);
		
		return ano;
	}
	
	public static boolean isTimestampEqualsDate(Date d1, Date d2){
		return d1.getTime() == d2.getTime();
	}
	
	public static boolean isTimestampBeforeDate(Date d1, Date d2){
		return d1.getTime() < d2.getTime();
	}
}
