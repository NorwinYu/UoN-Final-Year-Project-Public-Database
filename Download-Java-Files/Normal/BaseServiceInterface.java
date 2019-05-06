package br.com.uds.udspizzaria.domain.service;

import java.util.List;

public interface BaseServiceInterface<T> {
    public T buscar(Long id);

    public List<T> listarTodos();

    public T salvar(T entidade);

    public T atualizar(Long id, T entidade);

    public void remover(Long id);
}
