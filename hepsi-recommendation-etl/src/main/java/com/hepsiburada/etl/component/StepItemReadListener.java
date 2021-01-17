package com.hepsiburada.etl.component;

import org.springframework.batch.core.ItemReadListener;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//@Component
public class StepItemReadListener implements ItemReadListener<String> {

//    @Autowired
//    private Tasklet deleteFromTableTasklet;

    @Override
    public void beforeRead() {
        //deleteFromTableTasklet.execute()
        System.out.println(">>>>>>>>>>ItemReadListener - beforeRead");
        System.out.println(">>>>>>>>>>ItemReadListener - beforeRead");
        System.out.println(">>>>>>>>>>ItemReadListener - beforeRead");
    }

    @Override
    public void afterRead(String item) {
        System.out.println(">>>>>>>>>>ItemReadListener - afterRead");
        System.out.println(">>>>>>>>>>ItemReadListener - afterRead");
        System.out.println(">>>>>>>>>>ItemReadListener - afterRead");
    }

    @Override
    public void onReadError(Exception ex) {
        System.out.println(">>>>>>>>>>ItemReadListener - onReadError");
        System.out.println(">>>>>>>>>>ItemReadListener - onReadError");
        System.out.println(">>>>>>>>>>ItemReadListener - onReadError");
    }
}
