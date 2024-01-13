package gr.conference.confsys;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "conference")
public class Conference {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name ="conf_id", nullable = false, unique = true)
	private float id;
	
	@Column(name="creator_id", nullable = false)
	private float id_creator;
	
	@Column(name="conf_name", nullable = false)
	private String name;
	
	@Temporal(TemporalType.DATE)
	@Column(name="conf_date", nullable = false)
	private Date date;
	
	@Column(name = "conf_desc")
	private String desc;
	

	public float getId() {
		return id;
	}

	public void setId(float id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public float getId_creator() {
		return id_creator;
	}

	public void setId_creator(float id_creator) {
		this.id_creator = id_creator;
	}

	@Override
	public String toString() {
		return "Conference: name=" + name + ", date=" + date + ", desc=" + desc + ".";
	}
	
	
	
}
