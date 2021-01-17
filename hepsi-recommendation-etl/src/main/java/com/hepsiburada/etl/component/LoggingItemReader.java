package com.hepsiburada.etl.component;

import com.hepsiburada.etl.model.UserIdDto;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class LoggingItemReader implements ItemReader<UserIdDto> {

    private JdbcPagingItemReader<UserIdDto> jdbcPagingItemReader;


    @Override
    public UserIdDto read() throws Exception {
        Thread.sleep(2000);


        UserIdDto read = jdbcPagingItemReader.read();

        System.out.println(" GELDİİİİİ");
        System.out.println(" GELDİİİİİ");
        System.out.println(" GELDİİİİİ");
        System.out.println(" read>>>> " + read);
        System.out.println(" GELDİİİİİ");
        System.out.println(" GELDİİİİİ");
        System.out.println(" GELDİİİİİ");


        return read;
    }

}
