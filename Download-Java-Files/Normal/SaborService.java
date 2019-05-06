package br.com.uds.udspizzaria.application.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.uds.udspizzaria.application.service.exception.InformacaoNaoEncontradaException;
import br.com.uds.udspizzaria.application.service.exception.SaborNaoEncontradoException;
import br.com.uds.udspizzaria.domain.model.sabor.Sabor;
import br.com.uds.udspizzaria.domain.service.SaborServiceInterface;
import br.com.uds.udspizzaria.infrastructure.persistence.hibernate.repository.SaborRepositoryInterface;
import br.com.uds.udspizzaria.presentation.dto.SaborDTO;

@Service
public class SaborService extends BaseService<Sabor> implements SaborServiceInterface {

	@Autowired
	private SaborRepositoryInterface saborRepository;
	
	/**
	 * Metodo responsável por listar todos os sabores com id e nome
	 * 
	 * @return SaborDTO
	 */
    public List<SaborDTO> listarTodosComIdENome() {
        return this.getRepository().findIdAndNomeByAll();
    }

    @Override
    public Sabor buscar(Long id) throws SaborNaoEncontradoException {
    	try {
        	return super.buscar(id);
		} catch (InformacaoNaoEncontradaException exception) {
			throw new SaborNaoEncontradoException();
		}
    }

	@Override
	protected SaborRepositoryInterface getRepository() {
		return this.saborRepository;
	}

}
