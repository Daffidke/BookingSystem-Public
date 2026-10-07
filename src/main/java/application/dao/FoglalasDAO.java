package application.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import application.model.Foglalas;

@Repository
public class FoglalasDAO extends JdbcDaoSupport {

    @Autowired
    DataSource dataSource;

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }

    public void insertFoglalas(Foglalas foglalas) {
        String sql = "INSERT INTO Foglalasok (kezdet, veg, allapot, felhasznalo_id, szallas_id) VALUES (?, ?, ?, ?, ?)";
        getJdbcTemplate().update(sql, foglalas.getKezdet(), foglalas.getVeg(), foglalas.getAllapot(),
                foglalas.getFelhasznaloId(), foglalas.getSzallasId());
    }

    public List<Foglalas> listFoglalasok() {
        String sql = "SELECT * FROM Foglalasok";
        return getJdbcTemplate().query(sql, new FoglalasRowMapper());
    }

    public Foglalas getFoglalasById(int id) {
        String sql = "SELECT * FROM Foglalasok WHERE id = ?";
        return getJdbcTemplate().queryForObject(sql, new FoglalasRowMapper(), id);
    }

    public List<Foglalas> getFoglalasokByFelhasznaloId(int felhasznaloId) {
        String sql = "SELECT * FROM Foglalasok WHERE felhasznalo_id = ?";
        return getJdbcTemplate().query(sql, new FoglalasRowMapper(), felhasznaloId);
    }

    public List<Foglalas> getFoglalasokBySzallasId(int szallasId) {
        String sql = "SELECT * FROM Foglalasok WHERE szallas_id = ?";
        return getJdbcTemplate().query(sql, new FoglalasRowMapper(), szallasId);
    }

    public void deleteFoglalas(int id) {
        String sql = "DELETE FROM Foglalasok WHERE id = ?";
        getJdbcTemplate().update(sql, id);
    }

    public void updateFoglalas(int id, String kezdet, String veg, String allapot, int felhasznaloId, int szallasId) {
        String sql = "UPDATE Foglalasok SET kezdet = ?, veg = ?, allapot = ?, felhasznalo_id = ?, szallas_id = ? WHERE id = ?";
        getJdbcTemplate().update(sql, kezdet, veg, allapot, felhasznaloId, szallasId, id);
    }

    private static class FoglalasRowMapper implements RowMapper<Foglalas> {
        @Override
        public Foglalas mapRow(ResultSet rs, int rowNum) throws SQLException {
            Foglalas foglalas = new Foglalas();
            foglalas.setId(rs.getInt("id"));
            foglalas.setKezdet(rs.getString("kezdet"));
            foglalas.setVeg(rs.getString("veg"));
            foglalas.setAllapot(rs.getString("allapot"));
            foglalas.setFelhasznaloId(rs.getInt("felhasznalo_id"));
            foglalas.setSzallasId(rs.getInt("szallas_id"));
            return foglalas;
        }
    }
}
