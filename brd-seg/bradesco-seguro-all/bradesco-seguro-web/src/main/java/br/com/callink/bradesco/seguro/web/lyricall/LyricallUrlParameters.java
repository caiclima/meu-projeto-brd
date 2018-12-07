/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.callink.bradesco.seguro.web.lyricall;

import static br.com.callink.bradesco.seguro.commons.utils.StringUtils.isEmpty;
import br.com.callink.bradesco.seguro.entity.Evento;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author rogerio_moreira
 */
public class LyricallUrlParameters {

    private final String idClienteCampanha;
    private final String loginUsuario;
    private final String callType;
    private final String telefone;
    private final String callUrl;
    private final String lyricallScript;
    private final String chaveAtendimento;
    private final String url = "%s/UDAgentPL/opt/http/forms/scripts/%s/termcode.dsp?type=%s&codigoAspect=%s";

    public LyricallUrlParameters(Map<String, String> requestParameter) throws Exception {
        idClienteCampanha = requestParameter.get("IdClienteCampanha");
        loginUsuario = requestParameter.get("UserID");
        callType = requestParameter.get("CallType");
        telefone = requestParameter.get("phoneNumber");
        callUrl = requestParameter.get("callURL");
        lyricallScript = requestParameter.get("LyricallScript");
        chaveAtendimento = requestParameter.get("chaveAtendimento");
        validaUrlParams();
    }

    private void validaUrlParams() throws Exception {
        List<String> erro = new ArrayList();
        if (isEmpty(idClienteCampanha)) {
            erro.add("IdClienteCampanha");
        }
        if (isEmpty(loginUsuario)) {
            erro.add("UserID");
        }
        if (isEmpty(callType)) {
            erro.add("CallType");
        }
        if (isEmpty(telefone)) {
            erro.add("phoneNumber");
        }
        if (isEmpty(callUrl)) {
            erro.add("callURL");
        }
        if (isEmpty(lyricallScript)) {
            erro.add("LyricallScript");
        }
        if (isEmpty(chaveAtendimento)) {
            erro.add("chaveAtendimento");
        }
        if (!erro.isEmpty()) {
            throw new Exception(erro.toString().replace("[", "").replace("]", ""));
        }
    }

    public String getUrl(Evento evento) throws Exception {
        if (evento != null) {
            return String.format(url, callUrl, lyricallScript, evento.getTipoFinalizacao(), evento.getCodigoAspect());
        } else {
            throw new Exception("Selecione uma finalização!");
        }
    }

    public String getIdClienteCampanha() {
        return idClienteCampanha;
    }

    public String getLoginUsuario() {
        return loginUsuario;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getChaveAtendimento() {
        return chaveAtendimento;
    }

}
