package be.iannel.iannelloecoleski.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import be.iannel.iannelloecoleski.models.Period;
import be.iannel.iannelloecoleski.models.ConnectionBdd;

public class PeriodDAO {

    private Connection connection;

    public PeriodDAO() {
        this.connection = ConnectionBdd.getInstance();
    }

    public boolean create(Period period) {
        String sql = "INSERT INTO period (ID, STARTDATE, ENDDATE, ISVACATION, NAME) " +
                     "VALUES (period_seq.NEXTVAL, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDate(1, period.getStartDate());
            stmt.setDate(2, period.getEndDate());
            stmt.setInt(3, period.getIsVacation() ? 1 : 0);
            stmt.setString(4, period.getName());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Period read(int id) {
        Period period = null;
        String sql = "SELECT * FROM period WHERE ID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                period = new Period(
                    rs.getInt("ID"),
                    rs.getDate("STARTDATE"),
                    rs.getDate("ENDDATE"),
                    rs.getInt("ISVACATION") == 1,
                    rs.getString("NAME")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return period;
    }

    public List<Period> readAll() {
        List<Period> periods = new ArrayList<>();
        String sql = "SELECT * FROM period";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Period period = new Period(
                    rs.getInt("ID"),
                    rs.getDate("STARTDATE"),
                    rs.getDate("ENDDATE"),
                    rs.getInt("ISVACATION") == 1,
                    rs.getString("NAME")
                );
                periods.add(period);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return periods;
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM period WHERE ID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
