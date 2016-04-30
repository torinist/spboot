package sbt.controller;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sbt.model.Tsubuyaki;
import sbt.repository.TsubuyakiRepository;

/**
 * クライアントからのリクエストを受け付けるクラスを作成する
 * アノテーション＠RestControllerをつけることで、メソッドの戻り値をJSONで返却する
 * @author 1290402
 *
 */
@RestController
@RequestMapping("/tsubuyaki")
public class TsubuyakiController {

	@Autowired
	TsubuyakiRepository repository;
	
	/**
	 * つぶやきを新規追加するメソッド
	 * @param tsubuyaki 追加するつぶやき
	 * @param br ？
	 * @return つぶやき
	 */
	@RequestMapping(method=RequestMethod.POST)
	public Map<String, Tsubuyaki> create(@Valid Tsubuyaki tsubuyaki, BindingResult br) {
		System.out.println("createメソッドに突入");
		Tsubuyaki result = repository.save(tsubuyaki);
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		result.setCreatedTime(timestamp);
		System.out.println("ここまできてます");
		return Collections.singletonMap("content", result);		
	}
	
	/**
	 * つぶやき一覧を取得するメソッド
	 * @return つぶやき一覧
	 */
	@RequestMapping(method=RequestMethod.GET)
	public Map<String, Iterable<Tsubuyaki>> read() {
		System.out.println("readが呼ばれました");
		Iterable<Tsubuyaki> result = repository.findAllByOrderByCreatedTimeDesc();
		return Collections.singletonMap("content", result);
	}
	
	/**
	 * 選択されたつぶやきを更新するメソッド
	 * @param id 更新するつぶやきのID
	 * @param message 更新するつぶやきの値
	 * @return 更新したつぶやきのID
	 */
	@RequestMapping(value="/{id}", method=RequestMethod.POST)
	public Map<String, Long> update(@PathVariable Long id, @RequestParam String message) {
		Tsubuyaki tsubuyaki = repository.findOne(id);
		tsubuyaki.setMessage(message);
		repository.save(tsubuyaki);
		return Collections.singletonMap("id", id);
	}
	
	/**
	 * 選択されたつぶやきを削除するメソッド
	 * @param id 削除するつぶやきのID
	 * @return 削除したつぶやきのID
	 */
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public Map<String, Long> delete(@PathVariable Long id) {
		repository.delete(id);
		return Collections.singletonMap("id", id);
	}
}
