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

import application.model.Szallas;

@Repository
public class SzallasDAO extends JdbcDaoSupport {

    @Autowired
    DataSource dataSource;

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }

    public void insertSzallas(Szallas szallas, int userId) {
        String sqlSzallas = "INSERT INTO Szallasok (ar, nev, leiras, utca, isz, varos) VALUES (?, ?, ?, ?, ?, ?)";
        getJdbcTemplate().update(sqlSzallas, szallas.getAr(), szallas.getNev(), szallas.getLeiras(), szallas.getUtca(),
                szallas.getIsz(), szallas.getVaros());

        String sqlLastId = "SELECT LAST_INSERT_ID()";
        int szallasId = getJdbcTemplate().queryForObject(sqlLastId, Integer.class);

        String sqlKapcsolat = "INSERT INTO Felhasznalo_Szallas (felhasznalo_id, szallas_id) VALUES (?, ?)";
        getJdbcTemplate().update(sqlKapcsolat, userId, szallasId);
    }

    public List<Szallas> listSzallasok() {
        String sql = "SELECT * FROM Szallasok";
        return getJdbcTemplate().query(sql, new SzallasRowMapper());
    }

    public Szallas getSzallasById(int id) {
        String sql = "SELECT * FROM Szallasok WHERE id = ?";
        return getJdbcTemplate().queryForObject(sql, new SzallasRowMapper(), id);
    }

    public List<Szallas> getSzallasokByFelhasznaloId(int felhasznaloId) {
        String sql = "SELECT sz.* FROM Szallasok sz " +
                "JOIN Felhasznalo_Szallas fs ON sz.id = fs.szallas_id " +
                "WHERE fs.felhasznalo_id = ?";
        return getJdbcTemplate().query(sql, new SzallasRowMapper(), felhasznaloId);
    }

    public void deleteSzallas(int id) {
        String sqlKapcsolatTorles = "DELETE FROM Felhasznalo_Szallas WHERE szallas_id = ?";
        getJdbcTemplate().update(sqlKapcsolatTorles, id);

        String sqlSzallasTorles = "DELETE FROM Szallasok WHERE id = ?";
        getJdbcTemplate().update(sqlSzallasTorles, id);
    }

    public void updateSzallas(int id, double ar, String nev, String leiras, String utca, int isz, String varos) {
        String sql = "UPDATE Szallasok SET ar = ?, nev = ?, leiras = ?, utca = ?, isz = ?, varos = ? WHERE id = ?";
        getJdbcTemplate().update(sql, ar, nev, leiras, utca, isz, varos, id);
    }

    private static class SzallasRowMapper implements RowMapper<Szallas> {
        @Override
        public Szallas mapRow(ResultSet rs, int rowNum) throws SQLException {
            Szallas szallas = new Szallas();
            szallas.setId(rs.getInt("id"));
            szallas.setAr(rs.getDouble("ar"));
            szallas.setNev(rs.getString("nev"));
            szallas.setLeiras(rs.getString("leiras"));
            szallas.setUtca(rs.getString("utca"));
            szallas.setIsz(rs.getInt("isz"));
            szallas.setVaros(rs.getString("varos"));
            return szallas;
        }
    }
}
