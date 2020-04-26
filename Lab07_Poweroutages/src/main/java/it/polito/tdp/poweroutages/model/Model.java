package it.polito.tdp.poweroutages.model;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import it.polito.tdp.poweroutages.DAO.PowerOutageDAO;

public class Model {

	private PowerOutageDAO podao;
	private int bestAffected;
	private List<PowerOutage> powerOutages;
	private List<PowerOutage> result;
	private int maxY;

	public Model() {
		podao = new PowerOutageDAO();
	}

	public List<Nerc> getNercList() {
		return podao.getNercList();
	}

	public List<PowerOutage> ricerca(Nerc nerc, int maxY, int maxH) {
		bestAffected = 0;
		powerOutages = new ArrayList<PowerOutage>();
		result = new ArrayList<PowerOutage>();
		this.maxY = maxY;

		powerOutages = podao.powerOutagesFromNerc(nerc.getId()).stream().filter(po -> po.getDuration() <= maxH)
				.collect(Collectors.toList());

		ricorsiva(0);

		return result;
	}

	private void ricorsiva(int livello) {
		if (livello == powerOutages.size()) {
			return;
		}

		List<PowerOutage> parziale = new ArrayList<PowerOutage>();
		PowerOutage base = powerOutages.get(livello);
		parziale.add(base);

		for (int i = livello + 1; i < powerOutages.size(); i++) {
			PowerOutage next = powerOutages.get(i);
			if (ChronoUnit.YEARS.between(base.getStart(), next.getStop()) < maxY) {
				parziale.add(next);
			}
		}

		int totAffected = sumAffected(parziale);
		if (totAffected > bestAffected) {
			result = parziale;
			bestAffected = totAffected;
		}
		ricorsiva(livello + 1);
	}

	private int sumAffected(List<PowerOutage> parziale) {
		int somma = 0;
		for (PowerOutage p : parziale) {
			somma += p.getAffected();
		}

		return somma;
	}

}
