package br.com.callink.bradesco.seguro.web.faces.utils;

import java.io.PrintWriter;

public class TriggersGenerator {

	public static void main(String[] args) {
		try{
			final String CREATE_TRIGGER = "CREATE TRIGGER ";
			final String prefixIns = "trg_ins_";
			final String prefixUpd = "trg_upd_";
			final String prefixDel = "trg_del_";
			final String sufix = "_gera_log";
			final String SELECT_INSERTED = "SELECT * FROM INSERTED";
			final String SELECT_DELETED = "SELECT * FROM DELETED";
			
			String[] tables = new String[] {"TB_CAMPANHA", "TB_EVENTO", "TB_EVENTO_CAMPANHA",
					"TB_PRODUTO", "TB_PRODUTO_EVENTO", "TB_ESPORTE", "TB_PLANO", 
					"TB_PROFISSAO", "TB_TIPO_EVENTO", "TB_TIPO_PLANO", "TB_PARAMETRO_SISTEMA",
					"TB_BENEFICIARIO", "TB_BENEFICIARIO_PLANO", "TB_CLIENTE_CAMPANHA", 
					"TB_COBERTURA", "TB_ESTADO_CIVIL", "TB_GRAU_PARENTESCO", "TB_VENDA"};
			
			PrintWriter writer = new PrintWriter("createTriggers.sql", "UTF-8");
			PrintWriter writer2 = new PrintWriter("createTables.sql", "UTF-8");
			PrintWriter writer3 = new PrintWriter("dropTables.sql", "UTF-8");
			PrintWriter writer4 = new PrintWriter("dropTriggers.sql", "UTF-8");
			
			for (String tableName : tables) {
				String name = prefixIns + tableName.toLowerCase() + sufix;
				writer4.println("DROP TRIGGER " + name + ";");
				writer.println(CREATE_TRIGGER + name + " on " + tableName);
				writer.println("after insert as");
				writer.println("INSERT INTO " + tableName + "_LOG");
				writer.println(SELECT_INSERTED);
				writer.println("GO");
				writer.println("");
				
				name = prefixUpd + tableName.toLowerCase() + sufix;
				writer4.println("DROP TRIGGER " + name + ";");
				writer.println(CREATE_TRIGGER + name + " on " + tableName);
				writer.println("after update as");
				writer.println("INSERT INTO " + tableName + "_LOG");
				writer.println(SELECT_DELETED);
				writer.println("GO");
				writer.println("");
				
				name = prefixDel + tableName.toLowerCase() + sufix;
				writer4.println("DROP TRIGGER " + name + ";");
				writer.println(CREATE_TRIGGER + name + " on " + tableName);
				writer.println("after delete as");
				writer.println("INSERT INTO " + tableName + "_LOG");
				writer.println(SELECT_DELETED);
				writer.println("GO");
				writer.println("");
				
				writer2.println("SELECT * INTO " + tableName + "_LOG" + 
						" from " + tableName + " where 1=2 UNION ALL SELECT TOP (1) * FROM " +
						tableName + " where 1=2;");
				
				writer3.println("DROP TABLE " + tableName + "_LOG;");
				
			}
			
			writer.close();
			writer2.close();
			writer3.close();
			writer4.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

}
