package it.enaip.corso.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import it.enaip.corso.factory.DataSourceFacotry;

public class DaoStuff implements StuffDao {

	private DaoStuff() {
	}

	private static class SingletonHelper {
		private static final DaoStuff INSTANCE = new DaoStuff();
	}

	public static DaoStuff getInstance() {
		return SingletonHelper.INSTANCE;
	}

	public Optional<Stuff> find(String id) throws SQLException {
		String sql = "SELECT stuff_id,name,description,quantity,location FROM stuff WHERE stuff_id=?";
		int id_stuff = 0, quantity = 0;
		String name = "", description = "", location = "";
		Connection conn = DataSourceFacotry.getConnection();

		PreparedStatement statement = conn.prepareStatement(sql);
		statement.setString(1, id);
		ResultSet resultSet = statement.executeQuery();

		if (resultSet.next()) {
			id_stuff = resultSet.getInt("stuff_id");
			name = resultSet.getString("name");
			description = resultSet.getString("desctiption");
			quantity = resultSet.getInt("quantity");
			location = resultSet.getString("location");
		}
		return Optional.of(new Stuff(id_stuff, name, description, quantity, location));
	}

	public List<Stuff> findAll() throws SQLException {
		List<Stuff> listStuff = new ArrayList<Stuff>();
		String sql = "SELECT stuff_id,name,description,quantity,location FROM stuff";

		Connection conn = DataSourceFacotry.getConnection();
		PreparedStatement statement = conn.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery(sql);

		while (resultSet.next()) {
			int id = resultSet.getInt("stuff_id");
			String name = resultSet.getString("name");
			String description = resultSet.getString("desctiption");
			int quantity = resultSet.getInt("quantity");
			String location = resultSet.getString("location");

			Stuff stuff = new Stuff(id, name, description, quantity, location);
			listStuff.add(stuff);
		}

		return listStuff;
	}

	@Override
	public boolean save(Stuff stuff) throws SQLException {
		String sql = "INSERT INTO stuff (name, description, quantity, location) VALUES(?,?,?,?)";
		boolean rowInserted = false;
		Connection conn = DataSourceFacotry.getConnection();
		PreparedStatement statement = conn.prepareStatement(sql);
		statement.setString(1, stuff.getName());
		statement.setString(2, stuff.getDescription());
		statement.setInt(3, stuff.getQuantity());
		statement.setString(4, stuff.getLocation());
		rowInserted = statement.executeUpdate() > 0;

		return rowInserted;
	}

	@Override
	public boolean update(Stuff stuff) throws SQLException {
		String sql = "UPDATE stuff SET name=?, description=?, quantity=?,location=?";
		sql += "WHERE stuff_id=?";

		boolean rowUpdated = false;
		Connection conn = DataSourceFacotry.getConnection();
		PreparedStatement statement = conn.prepareStatement(sql);
		statement.setString(1, stuff.getName());
		statement.setString(2, stuff.getDescription());
		statement.setInt(3, stuff.getQuantity());
		statement.setString(4, stuff.getLocation());
		statement.setInt(5, stuff.getId());
		rowUpdated = statement.executeUpdate() > 0;

		return rowUpdated;
	}

	@Override
	public boolean delete(Stuff stuff) throws SQLException {
		String sql = "DELETE FROM stuff WHERE stuff_id = ?";
		boolean rowDeleted = false;
		
		Connection conn = DataSourceFacotry.getConnection();
		PreparedStatement statement = conn.prepareStatement(sql);
		statement.setInt(1, stuff.getId());
		rowDeleted = statement.executeUpdate() > 0;
		
		return rowDeleted;
	}

	@Override
	public List<Stuff> findALL() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}