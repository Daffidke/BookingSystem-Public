package application.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import application.model.Review;

@Repository
public class VelemenyekDAO extends JdbcDaoSupport {

    @Autowired
    DataSource dataSource;

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }

    public void insertVelemeny(Review velemeny, Timestamp time) {
        String sql = "INSERT INTO velemenyek (szallas_id, felhasznalo_id, mikor, ertekeles, uzenet) VALUES (?, ?, ?, ?, ?)";
        getJdbcTemplate().update(sql,
                velemeny.getSzallasId(),
                velemeny.getFelhasznaloId(),
                time,
                velemeny.getErtekeles(),
                velemeny.getUzenet());
    }

    public List<Review> listVelemenyek() {
        String sql = "SELECT * FROM velemenyek";
        return getJdbcTemplate().query(sql, new VelemenyRowMapper());
    }

    public Review getVelemenyById(int id) {
        String sql = "SELECT * FROM velemenyek WHERE id = ?";
        return getJdbcTemplate().queryForObject(sql, new VelemenyRowMapper(), id);
    }

    public List<Review> getVelemenyekBySzallasId(int szallasId) {
        String sql = "SELECT * FROM velemenyek WHERE szallas_id = ?";
        return getJdbcTemplate().query(sql, new VelemenyRowMapper(), szallasId);
    }

    public List<Review> getVelemenyekByFelhasznaloId(int felhasznaloId) {
        String sql = "SELECT * FROM velemenyek WHERE felhasznalo_id = ?";
        return getJdbcTemplate().query(sql, new VelemenyRowMapper(), felhasznaloId);
    }

    public void deleteVelemeny(int id) {
        String sql = "DELETE FROM velemenyek WHERE id = ?";
        getJdbcTemplate().update(sql, id);
    }

    public void updateVelemeny(int id, int szallasId, int felhasznaloId, Timestamp mikor, int ertekeles,
            String uzenet) {
        String sql = "UPDATE velemenyek SET szallas_id = ?, felhasznalo_id = ?, mikor = ?, ertekeles = ?, uzenet = ? WHERE id = ?";
        getJdbcTemplate().update(sql, szallasId, felhasznaloId, mikor, ertekeles, uzenet, id);
    }

    private static class VelemenyRowMapper implements RowMapper<Review> {
        @Override
        public Review mapRow(ResultSet rs, int rowNum) throws SQLException {
            Review velemeny = new Review();
            velemeny.setId(rs.getInt("id"));
            velemeny.setSzallasId(rs.getInt("szallas_id"));
            velemeny.setFelhasznaloId(rs.getInt("felhasznalo_id"));
            velemeny.setMikor(rs.getString("mikor"));
            velemeny.setErtekeles(rs.getInt("ertekeles"));
            velemeny.setUzenet(rs.getString("uzenet"));
            return velemeny;
        }
    }
}
