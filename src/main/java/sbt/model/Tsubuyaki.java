package sbt.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

import lombok.Data;

/**
 * つぶやきクラス
 * @author 1290402
 *
 */
@Entity
@Data
public class Tsubuyaki {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	private String message;
	
	@Version
	private long version;
	
	@Column(insertable=false, updatable=false)
	private Timestamp createdTime;
}
