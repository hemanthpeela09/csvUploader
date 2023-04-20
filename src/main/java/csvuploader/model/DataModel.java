package csvuploader.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "data")
public class DataModel {

	public DataModel(String code, String source, String codeListCode, String displayValue, String longDescription,
			Date fromDate, Date toDate, Integer sortingPriority) {
		this.code = code;
		this.source = source;
		this.codeListCode = codeListCode;
		this.displayValue = displayValue;
		this.longDescription = longDescription;
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.sortingPriority = sortingPriority;
	}

	@Id
	@NonNull
	@Column(name = "code")
	private String code;

	@Column(name = "source")
	private String source;

	@Column(name = "codeListCode")
	private String codeListCode;

	@Column(name = "displayValue")
	private String displayValue;

	@Column(name = "longDescription")
	private String longDescription;

	@Column(name = "fromDate")
	private Date fromDate;

	@Column(name = "toDate")
	private Date toDate;

	@Column(name = "sortingPriority")
	private Integer sortingPriority;
}
