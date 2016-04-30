package sbt.repository;

import org.springframework.data.repository.CrudRepository;

import sbt.model.Tsubuyaki;

/**
 * DBにCRUD操作をするインタフェースを作成する
 * @author 1290402
 *
 */
public interface TsubuyakiRepository extends CrudRepository<Tsubuyaki, Long> {

	Iterable<Tsubuyaki> findAllByOrderByCreatedTimeDesc();
}
