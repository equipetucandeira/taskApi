package com.ifsp.task.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ifsp.task.model.Task;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Esta interface extende JpaRepository, que nos fornece métodos prontos
 * para acessar e manipular dados no banco de dados. Basta especificar
 * a classe de entidade (Contact) e o tipo da chave primária (Long).
 */
public interface TaskRepository extends JpaRepository<Task, Long> {
  // Podemos adicionar métodos personalizados se necessário.
}
