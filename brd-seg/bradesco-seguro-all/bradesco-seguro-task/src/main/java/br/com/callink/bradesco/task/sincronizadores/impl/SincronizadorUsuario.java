package br.com.callink.bradesco.task.sincronizadores.impl;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import br.com.callink.bradesco.dao.ICorporativoDAO;
import br.com.callink.bradesco.dao.utils.CorporativoConnection;
import br.com.callink.bradesco.seguro.commons.utils.CollectionUtils;
import br.com.callink.bradesco.seguro.entity.Dominio;
import br.com.callink.bradesco.seguro.entity.Pessoa;
import br.com.callink.bradesco.seguro.entity.Usuario;
import br.com.callink.bradesco.seguro.service.IDominioService;
import br.com.callink.bradesco.seguro.service.IParametroSistemaService;
import br.com.callink.bradesco.seguro.service.IPessoaService;
import br.com.callink.bradesco.seguro.service.IUsuarioService;
import br.com.callink.bradesco.seguro.service.exception.ServiceException;
import br.com.callink.bradesco.seguro.service.utils.ServiceResponse;
import br.com.callink.bradesco.task.sincronizadores.ISincronizadorUsuarios;
import br.com.callink.bradesco.task.sincronizadores.utils.ParametrosUtils;

@Stateless
@Local(ISincronizadorUsuarios.class)
public class SincronizadorUsuario implements ISincronizadorUsuarios<Usuario> {
	
	private final Logger logger = Logger.getLogger(SincronizadorUsuario.class);
	private ICorporativoDAO corporativoDAO;
	
	@SuppressWarnings("rawtypes")
	@EJB
	private IParametroSistemaService parametroSistemaService;
	
	@EJB
	private IUsuarioService<Usuario> usuarioService;
	
	@EJB
	private IPessoaService<Pessoa> pessoaService;
	
	@EJB
	private IDominioService<Dominio> dominioService;

	@SuppressWarnings("unchecked")
	public void sincronizar()  {
		logger.info("Sincronizando usuarios...");
		
		String idOperacao = ParametrosUtils.getIdOperacao(parametroSistemaService);
		String idCargos = ParametrosUtils.getIdCargos(parametroSistemaService);
		Set<Usuario> usuarios;
		
		try {
			corporativoDAO = CorporativoConnection.getConnection();
			usuarios = corporativoDAO.buscarUsuarios(idOperacao, idCargos);
		} catch (Exception e) {
			throw new RuntimeException("Erro ao buscar usuarios na base do Corporativo. Erro: " + e.getMessage(), e);
		}

		if (!CollectionUtils.isEmpty(usuarios)) {

			ServiceResponse serviceResponse = null;
			
			try {
				serviceResponse = usuarioService.findAll();
			} catch (ServiceException e) {
				throw new RuntimeException("Erro ao buscar usuarios na base do Bradesco Seguros. Erro: " + e.getMessage(), e);
			}
			
			List<Usuario> usuariosExistentes = ((List<Usuario>) serviceResponse.getData());
			
			Usuario usuarioUpdate = null;
			BigInteger idDominio = null;
			BigInteger idPessoa = null;

			for (Usuario usuario : usuarios) {
				
				usuario.setDatAtualizacao(new Date());
				usuario.setId(null);

				try {
					
					idDominio = dominioService.buscarIdDominio(usuario.getIdDominio());
					usuario.setIdDominio(idDominio);
					
					idPessoa = pessoaService.buscarIdPessoa(usuario.getIdPessoa());
					usuario.setIdPessoa(idPessoa);
					
					for (Usuario usuarioExistente : usuariosExistentes) {
						if (usuarioExistente.getIdUsuarioCorporativo().equals(usuario.getIdUsuarioCorporativo())) {
							usuarioUpdate = usuarioExistente;
							break;
						}
					}

					if (usuarioUpdate != null) {
						usuarioUpdate.setDatAtualizacao(usuario.getDatAtualizacao());
						usuarioUpdate.setDatCadastro(usuario.getDatCadastro());
						usuarioUpdate.setDatFim(usuario.getDatFim());
						usuarioUpdate.setDatInicio(usuario.getDatInicio());
						usuarioUpdate.setIdDominio(usuario.getIdDominio());
						usuarioUpdate.setIndAtivo(usuario.getIndAtivo());
						usuarioUpdate.setIdPessoa(usuario.getIdPessoa());
						usuarioUpdate.setUsuario(usuario.getUsuario());
						usuarioUpdate.setDominio(null);
						usuarioUpdate.setPessoa(null);
						
						usuarioService.update(usuarioUpdate);
						
						usuarioUpdate = null;
					} else {
						usuarioService.salvar(usuario);
					}
					
				} catch (Exception e) {
					logger.error("Erro ao processar usuario atual: " + usuario+ ". Erro: " + e.getMessage(), e);
				}
			}
		} else {
			logger.info("Nenhum usuario encontrado!");
		}
	}
}
