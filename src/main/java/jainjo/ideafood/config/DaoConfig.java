package jainjo.ideafood.config;

import com.google.appengine.api.utils.SystemProperty;
import jainjo.ideafood.dao.TipoAlimentoDaoJdbc;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import jainjo.ideafood.dao.*;

@Configuration
public class DaoConfig {
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        if(SystemProperty.environment.value() == SystemProperty.Environment.Value.Production) {
            dataSource.setUrl("jdbc:google:mysql://digitalmenudev-180918:us-central1:jainjo-mysql/IdeaFood?socketFactory=com.google.cloud.sql.mysql.SocketFactory&useSSL=false");
            dataSource.setDriverClassName("com.mysql.jdbc.GoogleDriver");
        } else {
            dataSource.setDriverClassName("com.mysql.jdbc.Driver");
            dataSource.setUrl("jdbc:mysql:///IdeaFood?cloudSqlInstance=digitalmenudev-180918:us-central1:jainjo-mysql&socketFactory=com.google.cloud.sql.mysql.SocketFactory&useSSL=false");
        }
        dataSource.setUsername("root");
        dataSource.setPassword("0FbObz5gaFAdjxin");
        
        return dataSource;
    }
    
    @Bean
    public JdbcTemplate jdbcTemplate() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource());
        return jdbcTemplate;
    }
    
    @Bean
    public TipoAlimentoDao tipoAlimentoDao() {
        TipoAlimentoDaoJdbc tipoAlimentoDao = new TipoAlimentoDaoJdbc();
        tipoAlimentoDao.setJdbcTemplate(jdbcTemplate());
        return tipoAlimentoDao;
    }
    
    @Bean
    public IngredienteDao ingredienteDao() {
        IngredienteDaoJdbc ingredienteDao = new IngredienteDaoJdbc();
        ingredienteDao.setJdbcTemplate(jdbcTemplate());
        return ingredienteDao;
    }
    
    @Bean
    public AlimentoDao alimentoDao() {
        AlimentoDaoJdbc alimentoDao = new AlimentoDaoJdbc();
        alimentoDao.setJdbcTemplate(jdbcTemplate());
        return alimentoDao;
    }
    
    @Bean
    public IdeaDao ideaDao() {
        return new IdeaDaoJdbc(jdbcTemplate());
    }
    
    @Bean
    public PuntoVentaDao puntoVentaDao() {
        return new PuntoVentaDaoJdbc(jdbcTemplate());
    }
    
    @Bean
    public FotoDao fotoDao() {
        return new FotoDaoJdbc(jdbcTemplate());
    }
    
    @Bean
    public UsuarioDao usuarioDao() {
        return new UsuarioDaoJdbc(jdbcTemplate());
    }
}
