package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import db.DB;
import db.DBException;
import model.dao.ProjectDao;
import model.entities.Change;
import model.entities.Project;
import model.gui.MainWindow;

public class ProjectDaoJDBC implements ProjectDao {

	private Connection conn;

	public ProjectDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public void insert(Project obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO `projects` "
					+ "(`nameProject`,"
					+ "`city`,"
					+ "`costCenter`,"
					+ "`status`,"
					+ "`dateEntry`) "
					+ "VALUES "
					+ "(?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			
			st.setString(1, obj.getName());
			st.setString(2, obj.getCity());
			st.setString(3, obj.getCostCenter());
			st.setString(4, obj.getStatus());
			st.setDate(5, new java.sql.Date(obj.getDateEntry().getTime()));

			int rowsAffected = st.executeUpdate();
			
			if (rowsAffected > 0) {
//				ResultSet rs = st.getGeneratedKeys();
//				if (rs.next()) {
//					int id = rs.getInt(1);
//					obj.setId(id);
//				}
//				DB.closeResultSet(rs);
			}
			else {
				throw new DBException("Unexpected error! No rows affected!");
			}
		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void update(Project obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"UPDATE `projects` "
					+ "SET `nameProject` = ?, "
					+ "`city` = ? "
					+ "WHERE `costCenter` = ?");

			st.setString(1, obj.getName());
			st.setString(2, obj.getCity());
			st.setString(3, obj.getCostCenter());

			st.executeUpdate();
		} 
		catch (SQLException e) {
			throw new DBException(e.getMessage());
		} 
		finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void disable(Project obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"UPDATE `projects` "
					+ "SET `status` = ?, `reason` = ? "
					+ "WHERE `costCenter` = ?");

			st.setString(1, obj.getStatus());
			st.setString(2, obj.getReason());
			st.setString(3, obj.getCostCenter());

			st.executeUpdate();
		} 
		catch (SQLException e) {
			throw new DBException(e.getMessage());
		} 
		finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public Map<String, Project> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * FROM `projects`");

			rs = st.executeQuery();

			Map<String, Project> projects = new HashMap<String, Project>();

			while (rs.next()) {
				Project project = new Project();

				project.setCostCenter(rs.getString("costCenter"));
				project.setName(rs.getString("nameProject"));
				project.setCity(rs.getString("city"));
				project.setStatus(rs.getString("statusProject"));
				project.setDateEntry(rs.getDate("dateEntry"));
				project.setChanges(instatiateChanges(project.getCostCenter()));
				projects.put(project.getCostCenter(), project);
			}
			return projects;
		} 
		catch (SQLException e) {
			throw new DBException(e.getMessage());
		} 
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}
	
	private List<Change> instatiateChanges(String costCenter) {
		List<Change> changes = MainWindow.getChanges().stream().filter(c -> c.getObject().equals(costCenter)).collect(Collectors.toList());
		return changes;
	}
}
