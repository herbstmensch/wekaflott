package de.timherbst.wau.domain.wertungen;

import java.io.Serializable;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("CdPWertung")
public class CdPWertung extends Wertung implements Serializable {

	private static final long serialVersionUID = 3742504743775430850L;
	private Double difficulty = 0d;
	private Double execution = 0d;
	private Double missingElements = 0d;

	public Double getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(Double difficulty) {
		this.difficulty = difficulty;
	}

	public Double getExecution() {
		return execution;
	}

	public void setExecution(Double execution) {
		this.execution = execution;
	}

	public Double getMissingElements() {
		return missingElements;
	}

	public void setMissingElements(Double missingElements) {
		this.missingElements = missingElements;
	}

	@Override
	public Double getEndwert() {
		if (difficulty == null || execution == null || missingElements == null)
			return 0d;
		double r = (difficulty + execution) - missingElements;
		return r < 0 ? 0 : r;
	}

}
