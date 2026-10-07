package application.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import application.model.Foglalas;
import application.model.Szallas;
import application.model.User;

@Repository
public class UserDAO extends JdbcDaoSupport {

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    DataSource dataSource;

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }

    public void insertUser(User user) {
        String sql = "INSERT INTO Felhasznalok(nev, email, jelszo, szerep) VALUES (?, ?, ?, ?)";
        getJdbcTemplate().update(sql,
                user.getNev(),
                user.getEmail(),
                passwordEncoder.encode(user.getPassword()),
                user.getRole());
    }

    public void updateUser(User user){
        String sql = "UPDATE Felhasznalok SET nev = ?, email = ? WHERE id = ?";
        getJdbcTemplate().update(sql,
            user.getNev(),
            user.getEmail(),
            user.getId());
    }

    public User getUserById(int id) {
        String sql = "SELECT * FROM Felhasznalok WHERE id = ?";
        return getJdbcTemplate().queryForObject(sql, new UserRowMapper(), id);
    }

    public User getUserByEmail(String email) {
        String sql = "SELECT * FROM Felhasznalok WHERE email = ?";
        return getJdbcTemplate().queryForObject(sql, new UserRowMapper(), email);
    }

    public List<Szallas> listSzallasok() {
        String sql = "SELECT * FROM Szallasok";
        return getJdbcTemplate().query(sql, new SzallasRowMapper());
    }

    public List<Foglalas> getFoglalasByUserId(int userId) {
        String sql = "SELECT * FROM Foglalasok WHERE felhasznalo_id = ?";
        return getJdbcTemplate().query(sql, new FoglalasRowMapper(), userId);
    }

    public List<User> listUsers() {
        String sql = "SELECT * FROM Felhasznalok";
        return getJdbcTemplate().query(sql, new UserRowMapper());
    }

    private static class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setNev(rs.getString("nev"));
            user.setEmail(rs.getString("email"));
            user.setPassword(rs.getString("jelszo"));
            user.setRole(rs.getString("szerep"));
            return user;
        }
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

    private static class FoglalasRowMapper implements RowMapper<Foglalas> {
        @Override
        public Foglalas mapRow(ResultSet rs, int rowNum) throws SQLException {
            Foglalas foglalas = new Foglalas();
            foglalas.setId(rs.getInt("id"));
            foglalas.setSzallasId(rs.getInt("szallas_id"));
            foglalas.setFelhasznaloId(rs.getInt("felhasznalo_id"));
            foglalas.setTelefonszam(rs.getString("telefonszam"));
            foglalas.setAllapot(rs.getString("allapot"));
            foglalas.setKezdet(rs.getString("kezdet"));
            foglalas.setVeg(rs.getString("veg"));
            return foglalas;
        }
    }
}
