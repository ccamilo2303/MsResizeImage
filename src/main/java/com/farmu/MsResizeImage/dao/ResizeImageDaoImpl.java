package com.farmu.MsResizeImage.dao;

import com.farmu.MsResizeImage.dto.RequestResizeImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

/**
 * @author Cristian Camilo Beltrán
 * @since  19/12/2022
 */
@Repository
public class ResizeImageDaoImpl implements ResizeImageDao{
    
    @Autowired
    @Qualifier("JdbcTemplateResizeImage")
    private NamedParameterJdbcTemplate namedParameterJdbcTemplateOne;
    
    @Override
    public long registryProcess(RequestResizeImage requestResizeImage) {
        
        KeyHolder keyHolder = new GeneratedKeyHolder();
        
        String sql = "insert into process_state (file_name, finished, start_date) values(:fileName, false, now())";
        
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("fileName", requestResizeImage.getFileName());
        
        System.out.println("Antes de insert");
        namedParameterJdbcTemplateOne.update(sql, param, keyHolder,  new String[]{"id"});
        System.out.println("despues de insert");
        
        return keyHolder.getKey().longValue();
    }
    
    @Override
    public void finishProcess(long idProcess, String outputPath) {
        String sql = "update process_state set finished = true, output_path = :outputPath, finish_date = now() where id = :id";
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("id", idProcess)
                .addValue("outputPath", outputPath);
        namedParameterJdbcTemplateOne.update(sql, param);
    }
    
    @Override
    public boolean queryProcess(long idProcess) {
        String sql = "select finished from process_state where id = :id";
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("id", idProcess);
        return namedParameterJdbcTemplateOne.queryForObject(sql, param, Boolean.class);
    }

    @Override
    public String queryPathDownload(long idProcess) {
        String sql = "select output_path from process_state where id = :id";
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("id", idProcess);
        return namedParameterJdbcTemplateOne.queryForObject(sql, param, String.class);
    }
    
}
