package sagan.model;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import sagan.web.json.DoubleJsonSerializer;

public class AuroraData {

	@JsonSerialize(using = DoubleJsonSerializer.class)
	private double[][] n;
	@JsonSerialize(using = DoubleJsonSerializer.class)
	private double[][] s;

	public double[][] getS() {
		return s;
	}

	public void setS(double[][] s) {
		this.s = s;
	}

	public double[][] getN() {
		return n;
	}

	public void setN(double[][] n) {
		this.n = n;
	}

	public AuroraData() {
	}

}
