package it.polito.tdp.poweroutages.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.poweroutages.model.Nerc;
import it.polito.tdp.poweroutages.model.PowerOutage;

public class PowerOutageDAO {

	public List<Nerc> getNercList() {

		String sql = "SELECT id, value FROM nerc";
		List<Nerc> nercList = new ArrayList<>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				Nerc n = new Nerc(res.getInt("id"), res.getString("value"));
				nercList.add(n);
			}

			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return nercList;
	}

	public List<PowerOutage> powerOutagesFromNerc(int nercId) {

		String sql = "SELECT nerc_id, date_event_began, date_event_finished, customers_affected FROM poweroutages WHERE nerc_id = ? ORDER BY date_event_began ASC";
		List<PowerOutage> poList = new ArrayList<>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, nercId);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				PowerOutage po = new PowerOutage(res.getInt("nerc_id"),
						res.getTimestamp("date_event_began").toLocalDateTime(),
						res.getTimestamp("date_event_finished").toLocalDateTime(), res.getInt("customers_affected"));
				poList.add(po);
			}

			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return poList;
	}

}
