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
import br.com.callink.bradesco.seguro.entity.Cargo;
import br.com.callink.bradesco.seguro.entity.Pessoa;
import br.com.callink.bradesco.seguro.entity.PessoaColaborador;
import br.com.callink.bradesco.seguro.service.ICargoService;
import br.com.callink.bradesco.seguro.service.IParametroSistemaService;
import br.com.callink.bradesco.seguro.service.IPessoaColaboradorService;
import br.com.callink.bradesco.seguro.service.IPessoaService;
import br.com.callink.bradesco.seguro.service.utils.ServiceResponse;
import br.com.callink.bradesco.task.sincronizadores.ISincronizadorPessoas;
import br.com.callink.bradesco.task.sincronizadores.utils.ParametrosUtils;

@Stateless
@Local(ISincronizadorPessoas.class)
public class SincronizadorPessoa implements ISincronizadorPessoas<Pessoa> {
	
	private final Logger logger = Logger.getLogger(SincronizadorPessoa.class);
	private ICorporativoDAO corporativoDAO;
	
	@SuppressWarnings("rawtypes")
	@EJB
	private IParametroSistemaService parametroSistemaService;
	
	@EJB
	IPessoaService<Pessoa> pessoaService;
	
	@EJB
	IPessoaColaboradorService<PessoaColaborador> pessoaColaboradorService;
	
	@EJB
	ICargoService<Cargo> cargoService;

	@SuppressWarnings("unchecked")
	@Override
	public void sincronizar() {
		
		logger.info("Sincronizando pessoas...");
		
		String idOperacao = ParametrosUtils.getIdOperacao(parametroSistemaService);
		String idCargos = ParametrosUtils.getIdCargos(parametroSistemaService);
		Set<PessoaColaborador> pessoas;
		
		try {
			corporativoDAO = CorporativoConnection.getConnection();
			pessoas = corporativoDAO.buscarPessoas(idOperacao, idCargos);
		} catch (Exception e) {
			throw new RuntimeException("Erro ao buscar pessoas na base do Corporativo. Erro: " + e.getMessage(), e);
		}

		if (!CollectionUtils.isEmpty(pessoas)) {

			ServiceResponse serviceResponse = null;
			
			try {
				serviceResponse = pessoaColaboradorService.findAll();
			} catch (Exception e) {
				throw new RuntimeException("Erro ao buscar dominios na base do Bradesco Seguros. Erro: " + e.getMessage(), e);
			}
			
			List<PessoaColaborador> pessoasExistentes = (List<PessoaColaborador>) serviceResponse.getData();
			
			PessoaColaborador pessoaColaboradorUpdate = null;
			Pessoa pessoaUpdate = null;
			BigInteger idCargo = null;

			BigInteger idPessoa = null;

			for (PessoaColaborador pessoaColaborador : pessoas) {
				
				pessoaColaborador.setDataAtualizacao(new Date());
				pessoaColaborador.getPessoa().setDataAtualizacao(new Date());
				pessoaColaborador.setId(null);

				try {
					
					idCargo = cargoService.buscarIdCargo(pessoaColaborador.getIdCargo());
					pessoaColaborador.setIdCargo(idCargo);
					
					for (PessoaColaborador pessoaExistente : pessoasExistentes) {
						
						if (pessoaExistente.getIdPessoaColaboradorCorporativo().equals(pessoaColaborador.getIdPessoaColaboradorCorporativo())) {
							pessoaColaboradorUpdate = pessoaExistente;
							pessoaUpdate = pessoaExistente.getPessoa();
							break;
						}
					}

					if (pessoaColaboradorUpdate != null) {
						pessoaUpdate.setCpfCnpj(pessoaColaboradorUpdate.getPessoa().getCpfCnpj());;
						pessoaUpdate.setDataAtualizacao(pessoaColaborador.getPessoa().getDataAtualizacao());
						pessoaUpdate.setEmail(pessoaColaborador.getPessoa().getEmail());
						pessoaUpdate.setNomePessoa(pessoaColaborador.getPessoa().getNomePessoa());
						pessoaUpdate.setTelefone(pessoaColaborador.getPessoa().getTelefone());
						
						pessoaService.update(pessoaUpdate);
						
						pessoasExistentes.remove(pessoaUpdate);
						pessoaColaboradorUpdate.setAtivo(pessoaColaborador.getAtivo());
						pessoaColaboradorUpdate.setIdCargo(pessoaColaborador.getIdCargo());
						pessoaColaboradorUpdate.setDataAdmissao(pessoaColaborador.getDataAdmissao());
						pessoaColaboradorUpdate.setDataAfastamento(pessoaColaborador.getDataAfastamento());
						pessoaColaboradorUpdate.setExperiencia(pessoaColaborador.getExperiencia());
						pessoaColaboradorUpdate.setMatricula(pessoaColaborador.getMatricula());
						
						idPessoa = pessoaService.buscarIdPessoa(pessoaColaborador.getPessoa().getIdPessoaCorporativo());
						pessoaColaboradorUpdate.setIdPessoa(idPessoa);
						pessoaColaboradorUpdate.setPessoa(null);
						pessoaColaboradorUpdate.setCargo(null);
						
						pessoaColaboradorService.update(pessoaColaboradorUpdate);
						
						pessoaColaboradorUpdate = null;
						pessoaUpdate = null;
					} else {
						ServiceResponse response = pessoaService.save(pessoaColaborador.getPessoa());
						Pessoa pessoaSaved = (Pessoa) response.getData();
						pessoaColaborador.setIdPessoa(pessoaSaved.getId());
						pessoaColaborador.setPessoa(null);
						pessoaColaborador.setCargo(null);
						
						pessoaColaboradorService.salvar(pessoaColaborador);
					}
					
				} catch (Exception e) {
					logger.error("Erro ao processar pessoa atual: " + pessoaColaborador + ". Erro: " + e.getMessage(), e);
				}
			}
		} else {
			logger.info("Nenhuma pessoa encontrado!");
		}
	}
}
