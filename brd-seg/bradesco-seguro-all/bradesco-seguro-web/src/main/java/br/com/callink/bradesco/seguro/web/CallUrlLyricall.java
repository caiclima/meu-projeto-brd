package br.com.callink.bradesco.seguro.web;

import java.io.Serializable;

import br.com.callink.bradesco.seguro.entity.Evento;
import br.com.callink.bradesco.seguro.web.util.StringUtil;




public class CallUrlLyricall implements Serializable {
	
	private static final long serialVersionUID = 5983391166273688107L;
	
	private String url;
	private Evento currentEvento;
	
	public static final String CALL_URL = "callUrl";
	public static final String LYRICALL_SCRIPT = "lyricallScript";
	public static final String DEFAULT_TIPO_FINALIZACAO = "1";
	

	public CallUrlLyricall(Evento evento, String callUrl, String lyricallScript) throws Exception {
		this.currentEvento = evento;
		this.url = callUrl + "/UDAgentPL/opt/http/forms/scripts/" + lyricallScript + "/termcode.dsp?type=";
		
		validaParametros(currentEvento, callUrl, lyricallScript);

	}
	
	public String getUrl(String callManual, String usuario) throws Exception{
		if (getCurrentEvento() != null) {
			if(callManual != null && callManual.equals("1")) {//Se for cliente buscado, envia sempre tabulacao de sucesso
				return url + "1" + "&codigoAspect=" + getCurrentEvento().getCodigoAspect();
				//return url + this.currentEvento.getTipoFinalizacao() + "&codigoAspect=" + getCurrentEvento().getCodigoAspect();
			} else {
				return url + this.currentEvento.getTipoFinalizacao() + "&codigoAspect=" + getCurrentEvento().getCodigoAspect();
			}

		} else {
			throw new Exception("Selecione uma finalização!");
		}
	}

	public String getUrl(String callManual) throws Exception {
		if (getCurrentEvento() != null) {
			if(callManual != null && callManual.equals("1")) {//Se for cliente buscado, envia sempre tabulacao de sucesso
				return url + "1" + "&codigoAspect=" + getCurrentEvento().getCodigoAspect();
				//return url + this.currentEvento.getTipoFinalizacao() + "&codigoAspect=" + getCurrentEvento().getCodigoAspect();
			} else {
				return url + this.currentEvento.getTipoFinalizacao() + "&codigoAspect=" + getCurrentEvento().getCodigoAspect();
			}

		} else {
			throw new Exception("Selecione uma finalização!");
		}
	}

	public Evento getCurrentEvento() {
		return currentEvento;
	}

	private void validaParametros(Evento currentEvento, String callUrl, String lyricallScript) throws Exception {
		StringBuilder errosMessage = new StringBuilder();

		if (currentEvento == null) {
			errosMessage.append("Evento ");
		}
		if (StringUtil.isEmpty(callUrl)) {
			if (errosMessage.length() > 0) {
				errosMessage.append(" , ");
			}

			errosMessage.append(CALL_URL);
		}
		if (StringUtil.isEmpty(lyricallScript)) {
			if (errosMessage.length() > 0) {
				errosMessage.append(" , ");
			}

			errosMessage.append(LYRICALL_SCRIPT);
		}

		if (errosMessage.length() > 0) {
			errosMessage.append(" obrigatório(s)");
			throw new Exception("Parametro(s): " + errosMessage);
		}
	}

}
