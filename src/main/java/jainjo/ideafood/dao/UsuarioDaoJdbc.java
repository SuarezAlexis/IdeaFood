package jainjo.ideafood.dao;

import jainjo.ideafood.model.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;

public class UsuarioDaoJdbc implements UsuarioDao {
    private JdbcTemplate jdbcTemplate;
    
    private final String INSERT_USUARIO_SQL = "INSERT INTO Usuario(UserName, Nombre, Password, Email) VALUES (?,?,?,?)";
    private final String INSERT_PERMISOS_SQL = "INSERT INTO Usuario_Permiso(UserName, Nombre) VALUES (?,?)";
    private final String SELECT_USUARIO_SQL = "SELECT * FROM Usuario WHERE UserName = ? OR Email = ?";
    private final String SELECT_USUARIO_PERMISO_SQL = "SELECT Nombre FROM Usuario_Permiso WHERE UserName = ?";
    private final String SELECT_HIGH_SCORES_SQL = "SELECT * FROM Usuario ORDER BY BricksScore DESC LIMIT ?";
    
    private final static RowMapper rowMapper = new RowMapper<Usuario>() {
        @Override
        public Usuario mapRow(ResultSet rs, int rowNum) throws SQLException {
            Usuario u = new Usuario();
            u.setUserName(rs.getString("UserName"));
            u.setNombre(rs.getString("Nombre"));
            u.setEmail(rs.getString("Email"));
            u.setPassword(rs.getString("Password"));
            u.setPermisos(new ArrayList<String>());
            u.setScore(rs.getInt("BricksScore"));
            return u;
        }
    };
    
    public UsuarioDaoJdbc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    @Override
    public void insert(final Usuario usuario) {
        PreparedStatementCreator psc = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
                PreparedStatement ps = conn.prepareStatement(INSERT_USUARIO_SQL);
                ps.setString(1,usuario.getUserName());
                ps.setString(2,usuario.getNombre());
                ps.setString(3,usuario.getPassword());
                ps.setString(4,usuario.getEmail());
                return ps;
            }
        };
        jdbcTemplate.update(psc);
        
        
        for(final String p : usuario.getPermisos()) {
            psc = new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
                    PreparedStatement ps = conn.prepareStatement(INSERT_PERMISOS_SQL);
                    ps.setString(1, usuario.getUserName());
                    ps.setString(2, p);
                    return ps;
                }
            };
            jdbcTemplate.update(psc);
        }
    }

    @Override
    public void delete(String userName) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(final Usuario usuario) {
        PreparedStatementCreator psc = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
                int paramCount = 1;
                String updateUsuarioSql = "UPDATE Usuario SET ";
                if(usuario.getNombre() != null) 
                    updateUsuarioSql += "Nombre = ? ,";
                if(usuario.getEmail() != null)
                    updateUsuarioSql += "Email = ? ,";
                if(usuario.getScore() > 0)
                    updateUsuarioSql += "BricksScore = ? ,";
                updateUsuarioSql = updateUsuarioSql.substring(0,updateUsuarioSql.length()-1);
                updateUsuarioSql += "WHERE UserName = ?";
                
                PreparedStatement ps = conn.prepareStatement(updateUsuarioSql);
                if(usuario.getNombre() != null) {
                    ps.setString(paramCount, usuario.getNombre());
                    paramCount++;
                }
                if(usuario.getEmail() != null) {
                    ps.setString(paramCount,usuario.getEmail());
                    paramCount++;
                }
                if(usuario.getScore() > 0) {
                    ps.setInt(paramCount, usuario.getScore());
                    paramCount++;
                }
                ps.setString(paramCount, usuario.getUserName());
                return ps;
            }
        };
        jdbcTemplate.update(psc);
    }

    @Override
    public Usuario find(String userNameOrEmail) {
        Usuario usuario = (Usuario) jdbcTemplate.query(SELECT_USUARIO_SQL, new Object[] {userNameOrEmail, userNameOrEmail}, UsuarioDaoJdbc.rowMapper).get(0);
        for(String p : jdbcTemplate.queryForList(SELECT_USUARIO_PERMISO_SQL, new Object[] {usuario.getUserName()}, String.class)) {
            usuario.getPermisos().add(p);
        }
        return usuario;
    }
    
    @Override
    public List<Usuario> findHighScores(int top) {
        return jdbcTemplate.query(SELECT_HIGH_SCORES_SQL, rowMapper, new Object[] { top });
    }
    
}
