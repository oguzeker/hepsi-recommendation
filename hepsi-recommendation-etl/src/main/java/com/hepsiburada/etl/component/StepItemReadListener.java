package com.hepsiburada.etl.component;

import com.hepsiburada.etl.model.UserIdDto;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.beans.factory.annotation.Autowired;

public class StepItemReadListener implements ItemReadListener<UserIdDto> {

    @Autowired
    private JdbcPagingJobLauncher jdbcPagingJobLauncher;

    @Override
    public void beforeRead() {
        System.out.println("ItemReadListener - beforeRead");
    }

    @Override
    public void afterRead(UserIdDto item) {
        System.out.println("ItemReadListener - afterRead");
//        if (item != null) {
//            jdbcPagingJobLauncher.stop();
//            jdbcPagingJobLauncher.start();
//        }
    }

    @Override
    public void onReadError(Exception ex) {
        System.out.println("ItemReadListener - onReadError");
    }
}
